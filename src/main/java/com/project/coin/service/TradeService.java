package com.project.coin.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class TradeService{
    WebClient webClient;

    public TradeService(@Qualifier("notifyService")WebClient webClient){
        this.webClient=webClient;
    }

    public Mono<String> trade(){
        return webClient.get().uri("/notify").retrieve().toEntity(String.class).map(response -> response.getBody());
    }
}
