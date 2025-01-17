package com.compagny.service.impl;

import com.compagny.model.LineItem;
import com.compagny.model.UserExchange;
import com.compagny.model.type.ItemType;
import com.compagny.model.type.UserType;
import com.compagny.service.DiscountService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {
    private static final double EMPLOYEE_DISCOUNT = 0.30;
    private static final double AFFILIATE_DISCOUNT = 0.10;
    private static final double LOYALTY_DISCOUNT = 0.05;
    private static final double BULK_DISCOUNT_THRESHOLD = 100.0;
    private static final double BULK_DISCOUNT_AMOUNT = 5.0;

    @Override
    public double calculateDiscount(List<LineItem> items, UserExchange user) {
        double nonGroceryAmount = items.stream()
                .filter(item -> item.getCategory() != ItemType.GROCERY)
                .mapToDouble(LineItem::getAmount)
                .sum();

        double percentageDiscount = calculatePercentageDiscount(user) * nonGroceryAmount;
        double bulkDiscount = calculateBulkDiscount(items.stream().mapToDouble(LineItem::getAmount).sum());

        return percentageDiscount + bulkDiscount;
    }

    private double calculatePercentageDiscount(UserExchange user) {
        if (user.getType() == UserType.EMPLOYEE) return EMPLOYEE_DISCOUNT;
        if (user.getType() == UserType.AFFILIATE) return AFFILIATE_DISCOUNT;
        if (isLoyalCustomer(user)) return LOYALTY_DISCOUNT;
        return 0.0;
    }

    private boolean isLoyalCustomer(UserExchange user) {
        return user.getType() == UserType.CUSTOMER &&
                ChronoUnit.YEARS.between(user.getRegistrationDate(), LocalDateTime.now()) > 2;
    }

    private double calculateBulkDiscount(double amount) {
        return Math.floor(amount / BULK_DISCOUNT_THRESHOLD) * BULK_DISCOUNT_AMOUNT;
    }
}


