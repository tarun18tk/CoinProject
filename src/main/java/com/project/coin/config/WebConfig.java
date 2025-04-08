package com.project.coin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {
    
    @Bean
    public WebClient webClientConfig(){
     return WebClient.builder()
                    .baseUrl("https://api.coinranking.com/v2")
                    .defaultHeader("x-access-token", "coinrankingc5fa5d59b13aabd89c4e233c18b99a76f49426862e2bd7f6")
                    .build(); 
        // return WebClient.builder()
        //                 .baseUrl("http://localhost:9090/api/coins")
        //                 .build();
    }
}
