package com.salestax.receipt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@AllArgsConstructor
@Setter
@Getter
public class ItemCategory {
    private final String name;
    private final boolean taxExempt;

    private static final Set<String> EXEMPT_CATEGORIES = Set.of("book", "food", "medical");

    public ItemCategory(String name) {
        this.name = name.toLowerCase();
        this.taxExempt = EXEMPT_CATEGORIES.contains(this.name);
    }

    public String getName() {
        return name;
    }

    public boolean isTaxExempt() {
        return taxExempt;
    }

    @Override
    public String toString() {
        return name;
    }
}
