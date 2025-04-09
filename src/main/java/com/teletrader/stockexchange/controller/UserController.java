package com.teletrader.stockexchange.controller;

import com.teletrader.stockexchange.model.User;
import com.teletrader.stockexchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    // Dohvati korisnika po ID-u
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User user = userService.getUser(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Ažuriraj balans korisnika
    @PatchMapping("/{userId}/balance")
    public ResponseEntity<?> updateBalance(@PathVariable String userId, @RequestBody double amount) {
        User user = userService.getUser(userId);
        if (user != null) {
            userService.updateBalance(userId, amount);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Transfer akcija između dva korisnika
    @PostMapping("/{buyerId}/transfer/{sellerId}")
    public ResponseEntity<?> transferShares(
            @PathVariable String buyerId,
            @PathVariable String sellerId,
            @RequestParam int amount,
            @RequestParam double price) {

        User buyer = userService.getUser(buyerId);
        User seller = userService.getUser(sellerId);

        if (buyer != null && seller != null) {
            userService.transferShares(buyerId, sellerId, amount, price);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
