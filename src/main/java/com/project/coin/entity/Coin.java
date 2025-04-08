package com.project.coin.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Coin {
    String name;
    String symbol;
    double price;
}
