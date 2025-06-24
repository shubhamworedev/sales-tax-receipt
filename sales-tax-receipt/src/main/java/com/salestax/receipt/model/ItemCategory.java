package com.salestax.receipt.model;

import lombok.Data;

@Data
public class ItemCategory {
    private String name;
    private boolean isExempt;

    public ItemCategory(String name, boolean isExempt) {
        this.name = name;
        this.isExempt = isExempt;
    }

}
