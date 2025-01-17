package com.compagny.service.impl;

import com.compagny.dto.BillRequest;
import com.compagny.dto.BillResponse;
import com.compagny.model.LineItem;
import com.compagny.service.BillService;
import com.compagny.service.DiscountService;
import com.compagny.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class BillServiceImpl implements BillService {
    private final DiscountService discountService;
    private final ExchangeRateService exchangeService;

    @Override
    public BillResponse calculateBill(BillRequest request) {
        log.info("calculate the bill from: {} to : {}", request.getOriginalCurrency(), request.getTargetCurrency());

        double totalAmount = request.getItems().stream()
                .mapToDouble(LineItem::getAmount)
                .sum();

        double discountedAmount = totalAmount -
                discountService.calculateDiscount(request.getItems(), request.getUser());

        try {
            double exchangeRate = exchangeService.getExchangeRate(
                    request.getOriginalCurrency(),
                    request.getTargetCurrency()
            );

            return BillResponse.builder()
                    .originalAmount(totalAmount)
                    .discountedAmount(discountedAmount)
                    .convertedAmount(discountedAmount * exchangeRate)
                    .targetCurrency(request.getTargetCurrency())
                    .build();

        } catch (RuntimeException e) {
            log.error("Failed to convert currency: {}", e.getMessage());
            return BillResponse.builder()
                    .originalAmount(totalAmount)
                    .discountedAmount(discountedAmount)
                    .convertedAmount(discountedAmount)
                    .targetCurrency(request.getOriginalCurrency())
                    .build();
        }
    }
}
