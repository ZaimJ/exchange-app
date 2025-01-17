package com.compagny.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Bill {
    private List<LineItem> items;
    private String originalCurrency;
    private String targetCurrency;
    private double totalAmount;
}
