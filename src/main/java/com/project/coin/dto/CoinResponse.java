package com.project.coin.dto;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class CoinResponse {

    public String status;
    public CoinData data;    

}
