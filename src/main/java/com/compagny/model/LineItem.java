package com.compagny.model;


import com.compagny.model.type.ItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LineItem {
    private String name;
    private ItemType category;
    private double amount;
}
