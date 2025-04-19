package com.project.coin.service;

import java.util.stream.Collectors;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.project.coin.dto.CoinDTO;
import com.project.coin.dto.CoinRankingCoin;
import com.project.coin.dto.CoinResponse;
import com.project.coin.entity.Coin;

import reactor.core.publisher.Mono;

@Service
public class CoinService {

    @Autowired
    public WebClient client;

    @Cacheable(value="coinsCache", key="'page_' + #page + '_size_' + #size")
    //trying to return all coins using pagination and size
    public Mono<List<CoinDTO>> getAllCoins(int page, int size){
        return client.get().uri("/coins").retrieve().bodyToMono(CoinResponse.class)
        .map(response -> {
            List<CoinDTO> coinList = response.getData().getCoins().stream()
            .map(data-> CoinDTO.builder()
                .name(data.getName())
                .symbol(data.getSymbol())
                .price(data.getPrice()).build()
                ).collect(Collectors.toList());
                
                int fromIndex = page * size;
                int toIndex = Math.min(fromIndex + size, coinList.size());

                if (fromIndex > toIndex) {
                    return List.of(); // return empty if out of bounds
                }

                return coinList.subList(fromIndex, toIndex);
        });
    }


    //to return one coin details
    public Mono<CoinDTO> getCoinDetails(){
        return client.get().uri("/coins").retrieve().bodyToMono(CoinResponse.class).map((response)->{
            CoinRankingCoin coinRankingCoin = response.getData().getCoins().get(0);
            return CoinDTO.builder().name(coinRankingCoin.getName()).symbol(coinRankingCoin.getSymbol()).price(coinRankingCoin.getPrice()).build();
        });
    }
    
    //making our own Coin ojbects
    public CoinDTO getCoinByName(String name){
        Coin coin;
        if(name.equals("BTC")){
            coin=Coin.builder().name("BTC").symbol("duck").price(77000).build();
        }
        else{
            coin=Coin.builder().name("Doge").symbol("dog").price(770).build();    
        }
        CoinDTO coinDto = CoinDTO.builder().name(coin.getName()).symbol(coin.getSymbol()).price(coin.getPrice()).build();
        return coinDto;
    }

}
