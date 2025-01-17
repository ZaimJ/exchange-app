/**
 * Package containing service interfaces and implementations.
 */
package com.compagny.service;


import com.compagny.model.LineItem;
import com.compagny.model.UserExchange;

import java.util.List;

public interface DiscountService {
    double calculateDiscount(List<LineItem> items, UserExchange user);
}
