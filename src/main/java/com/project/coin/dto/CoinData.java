package com.project.coin.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoinData {

    public List<CoinRankingCoin> coins;

}
