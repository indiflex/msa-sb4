package com.msa.user.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class Client {
    private final RestClient accountClient;
    private final RestClient stockClient;

    public Client(@Value("${client.account}") String accountUri, @Value("${client.stock}") String stockUri) {
        this.accountClient = RestClient.builder().baseUrl(accountUri).build();
        this.stockClient = RestClient.builder().baseUrl(stockUri).build();
    }

    public AccountDTO getAccountInfo(Long userid) {
        try {
            return accountClient.get().uri("/api/accounts/{userid}", userid).retrieve().body(AccountDTO.class);
        } catch (Exception e) {
            return null;
        }
    }

    public StockDTO getStockInfo(Long userid) {
        try {
            return stockClient.get().uri("/api/stocks/{userid}", userid).retrieve().body(StockDTO.class);
        } catch (Exception e) {
            return null;
        }
    }
}
