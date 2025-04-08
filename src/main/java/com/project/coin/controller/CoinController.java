package com.project.coin.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.coin.dto.CoinDTO;
import com.project.coin.entity.Coin;
import com.project.coin.service.CoinService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/coins")
public class CoinController {

    CoinService coinService;

    public CoinController(CoinService coinService){
        this.coinService=coinService;
    }

    @GetMapping("/{name}")
    public CoinDTO getCoinInfo(@PathVariable String name){
        return coinService.getCoinByName(name);
    }

    @GetMapping("/allCoins")
    public List<CoinDTO> getAllCoins(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size){
        //since we dont have any database for that we will just return data from here
        List<Coin> listOfCoin = List.of(Coin.builder().name("BTC").symbol("$").price(770000).build(),
                                        Coin.builder().name("Doge").symbol("dog").price(800).build(),
                                        Coin.builder().name("Pi").symbol("#").price(90).build());
        
// could have used pagenation Pageable pageable = PageRequest.of(page, size);
// Page<Coin> page = coinRepository.findAll(pageable); ------ we will see later 

        List<CoinDTO> res = listOfCoin.stream().map(coin->
                            CoinDTO.builder().name(coin.getName()).symbol(coin.getSymbol()).price(coin.getPrice()).build()).collect(Collectors.toList());                                
        return res;
    }

    @GetMapping("/coinFromAPI")
    public Mono<List<CoinDTO>> getFromAPI(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "2") int size){
        return coinService.getAllCoins(page,size);
    }

}