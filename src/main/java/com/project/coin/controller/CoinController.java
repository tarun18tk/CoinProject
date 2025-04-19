package com.project.coin.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.coin.dto.ApiResponse;
import com.project.coin.dto.CoinDTO;
import com.project.coin.entity.Coin;
import com.project.coin.service.CoinService;
import com.project.coin.service.TradeService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/coins")
public class CoinController {

    CoinService coinService;
    TradeService tradeService;

    public CoinController(CoinService coinService, TradeService tradeService){
        this.coinService=coinService;
        this.tradeService=tradeService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<ApiResponse<CoinDTO>> getCoinInfo(@PathVariable String name){
        return ResponseEntity.ok().body(ApiResponse.<CoinDTO>builder().success(true).message("Got the coin").data(coinService.getCoinByName(name)).timeStamp(LocalDateTime.now()).build());
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

    //Mono responses

    @GetMapping("/getOneCoin")
    public Mono<ResponseEntity<ApiResponse<CoinDTO>>> getFromAPIMono(){
        return coinService.getCoinDetails().map(data -> ResponseEntity.ok(ApiResponse.<CoinDTO>builder().success(true).data(data).message("Fetched one coin").build()));
    }

    @GetMapping("/coinFromAPI")
    public Mono<ResponseEntity<ApiResponse<List<CoinDTO>>>> getFromAPI(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "2") int size){
        return coinService.getAllCoins(page,size).map(response -> ResponseEntity.ok(ApiResponse.<List<CoinDTO>>builder().success(true).data(response).timeStamp(LocalDateTime.now()).build()));
    }

    @GetMapping("/trade")
    public Mono<ResponseEntity<String>> tradingCoins(){
        return tradeService.trade()
            .map(response -> ResponseEntity.ok(response));
        //logic to trade coins maybe
        //and then lets call the other api to notify 
        // return tradeService.tradeCoins().map(response -> ResponseEntity.ok().body(response));
    }

    @PostMapping("/addNewCoin")
    public ResponseEntity<String> addedNewCoin(){
        //some logic to add coin to our platform or something
        return ResponseEntity.status(HttpStatus.OK).body("Some string");
    }

}