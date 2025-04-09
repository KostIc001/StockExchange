package com.teletrader.stockexchange.service;

import com.teletrader.stockexchange.enums.OrderType;
import com.teletrader.stockexchange.model.Order;
import com.teletrader.stockexchange.model.Trade;
import jakarta.annotation.PostConstruct;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

@Service
public class OrderBookService {

    private final PriorityQueue<Order> buyOrders = new PriorityQueue<>(
            Comparator.comparingDouble(Order::getPrice).reversed().thenComparingLong(Order::getTimestamp));

    private final PriorityQueue<Order> sellOrders = new PriorityQueue<>(
            Comparator.comparingDouble(Order::getPrice).thenComparingLong(Order::getTimestamp));

    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate; //za WebSocket slanje

    public OrderBookService(UserService userService, SimpMessagingTemplate messagingTemplate){
        this.userService = userService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostConstruct
    public void initializeTestOrders() {
        // Adding some sample orders to buy and sell queues for testing
        Order buyOrder = new Order(1L, "1", 100, 10, OrderType.BUY, "1L");
        Order buyOrder1 = new Order(2L, "2", 90, 10, OrderType.BUY, "2L");
        Order sellOrder = new Order(3L, "1", 90, 10, OrderType.SELL, "2L");
        Order sellOrder1 = new Order(4L, "2", 100, 10, OrderType.SELL, "1L");
        buyOrders.add(buyOrder);
        buyOrders.add(buyOrder1);
        sellOrders.add(sellOrder);
        sellOrders.add(sellOrder1);
    }


    public synchronized void placeOrder(Order order){
        if(order.getType() == OrderType.BUY) {
            matchBuyOrder(order);
        } else {
            matchSellOrder(order);
        }
        // Nakon svake promene šaljemo update svim klijentima
        broadcastOrderBook();
    }
    private void matchBuyOrder(Order buyOrder){
//        if(sellOrders.isEmpty()){
//            throw new Exception("Prazna sell orders");
//        }
        while (!sellOrders.isEmpty() && buyOrder.getAmount()> 0) {
            Order sellOrder = sellOrders.peek();

            if (sellOrder.getPrice() <= buyOrder.getPrice()) {
                int tradedAmount = Math.min(buyOrder.getAmount(), sellOrder.getAmount());
                double tradedPrice = sellOrder.getPrice();

                //Azuriraj korisnike
                userService.transferShares(
                        buyOrder.getUserId(), sellOrder.getUserId(),
                        tradedAmount, tradedPrice
                );
                //Posalji notifikacije o trgovini
                Trade trade = new Trade(buyOrder.getUserId(), sellOrder.getUserId(), tradedPrice, tradedAmount, System.currentTimeMillis());
                messagingTemplate.convertAndSend("/topic/trade", trade);

                //Azuriranje kolicine
                buyOrder.setAmount(buyOrder.getAmount() - tradedAmount);
                sellOrder.setAmount(sellOrder.getAmount() - tradedAmount);

                if (sellOrder.getAmount() == 0) {
                    sellOrders.poll(); //Izbaci ga
                }
            } else {
                break;
            }
            if (buyOrder.getAmount() > 0) {
                buyOrders.offer(buyOrder);
            }
        }
        }

        private void matchSellOrder(Order sellOrder){
            while (!buyOrders.isEmpty() && sellOrder.getAmount() > 0) {
                Order buyOrder = buyOrders.peek();

                if(buyOrder.getPrice()>= sellOrder.getPrice()){
                    int tradedAmount = Math.min(buyOrder.getAmount(), sellOrder.getAmount());
                    double tradedPrice = buyOrder.getPrice();

                    //Azuriraj korisnike
                    userService.transferShares(
                            buyOrder.getUserId(), sellOrder.getUserId(),
                            tradedAmount, tradedPrice
                    );

                    //Posalji notifikaciju o trgovini
                    Trade trade = new Trade(
                            buyOrder.getUserId(), sellOrder.getUserId(),
                            tradedPrice, tradedAmount, System.currentTimeMillis()
                    );
                    messagingTemplate.convertAndSend("/topic/trade", trade);

                    //Azuriraj kolicine
                    buyOrder.setAmount(buyOrder.getAmount() - tradedAmount);
                    sellOrder.setAmount(sellOrder.getAmount() - tradedAmount);

                    if(buyOrder.getAmount() == 0) {
                        buyOrders.poll();// izbaci ga
                    }
                } else {
                    break; // nema vise matcha
                }
            }
            if(sellOrder.getAmount() > 0) {
                sellOrders.offer(sellOrder);
            }
        }
    public List<Order> getTopBuyOrders(){
        return buyOrders.stream().limit(10).collect(Collectors.toList());
    }
    public List<Order> getTopSellOrders(){
        return sellOrders.stream().limit(10).collect(Collectors.toList());
    }

    private void broadcastOrderBook(){
        Map<String, Object> data = Map.of("topBuyOrders", getTopBuyOrders(),
                                          "topSellOrders", getTopSellOrders());
        Map<String, Object> payload = Map.of("type", "orderbook", "data", data);

        messagingTemplate.convertAndSend("/topic/orderbook", payload);
    }
    }


