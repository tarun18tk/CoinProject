package com.project.coin.dto;

import lombok.Builder;
import lombok.Data;

@Data@Builder
public class CoinDTO {
    String name;
    String symbol;
    double price;
}
