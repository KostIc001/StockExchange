package com.teletrader.stockexchange.service;

import com.teletrader.stockexchange.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private Map<String, User> users = new ConcurrentHashMap<>();

    public UserService() {

        users.put("user1", new User("user1", 10000.0, new HashMap<>(Map.of("APPLE", 50))));
        users.put("user2", new User("user2", 20000.0, new HashMap<>(Map.of("GOOGLE", 100))));
    }

    //Dohvati korisnia po ID-u
    public User getUser(String userId) {
        return users.get(userId);
    }

    //Azuriraj racun korisnika
    public void updateBalance(String userId, double amount) {
        User user = users.get(userId);
        if (user != null) {
            double newBalance = user.getBalance() + amount;
            user.setBalance(newBalance);
        }

    }
    //Transfer akcija izmedju kupaca i prodavaca
    public void transferShares(String buyerId, String sellerId, int amount, double price){
        User buyer = getUser(buyerId);
        User seller = getUser(sellerId);

        if(buyer != null && seller != null){
            double transactionAmount = amount * price;
        //proveri da li prodavac ima dovoljno akcija
        Integer sellerShares = seller.getStocks().getOrDefault("share", 0);
            if(sellerShares >= amount){
                //Azuriraj akcije i balans prodavca
                seller.getStocks().put("share", sellerShares - amount);
                seller.setBalance(seller.getBalance() + transactionAmount);

              Integer buyerShares = buyer.getStocks().getOrDefault("share", 0);
              buyer.getStocks().put("share", buyerShares + amount);
              buyer.setBalance(buyer.getBalance() - transactionAmount);
            }
        }
    }

}
