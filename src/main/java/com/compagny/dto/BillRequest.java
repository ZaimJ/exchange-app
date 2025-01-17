/**
 * Package containing DTO.
 */
package com.compagny.dto;

import com.compagny.model.LineItem;
import com.compagny.model.UserExchange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class BillRequest {
    private List<LineItem> items;
    private String originalCurrency;
    private String targetCurrency;
    private UserExchange user;
}
