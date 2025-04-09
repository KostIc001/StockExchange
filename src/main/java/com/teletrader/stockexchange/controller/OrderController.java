package com.teletrader.stockexchange.controller;

import com.teletrader.stockexchange.model.Order;
import com.teletrader.stockexchange.service.OrderBookService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderBookService orderBookService;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {
        orderBookService.placeOrder(order);
        return ResponseEntity.ok("Order placed successfully");
    }

    @GetMapping("/top-buy")
    public ResponseEntity<List<Order>> getTopBuyOrders() {
        return ResponseEntity.ok(orderBookService.getTopBuyOrders());
    }

    @GetMapping("/top-sell")
    public ResponseEntity<List<Order>> getTopSellOrders() {
        return ResponseEntity.ok(orderBookService.getTopSellOrders());
    }

}
