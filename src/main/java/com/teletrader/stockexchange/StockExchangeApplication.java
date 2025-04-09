package com.teletrader.stockexchange;

import com.teletrader.stockexchange.enums.OrderType;
import com.teletrader.stockexchange.model.Order;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockExchangeApplication {

	public static void main(String[] args) {



		SpringApplication.run(StockExchangeApplication.class, args);
	}

}
