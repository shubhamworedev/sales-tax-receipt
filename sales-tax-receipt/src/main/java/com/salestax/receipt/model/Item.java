package com.salestax.receipt.model;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Item {
    private int quantity;
    private String name;
    private BigDecimal price;
    private boolean isImported;
    private ItemCategory category;
}

