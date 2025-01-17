package com.compagny.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BillResponse {
    private double originalAmount;
    private double discountedAmount;
    private double convertedAmount;
    private String targetCurrency;
}
