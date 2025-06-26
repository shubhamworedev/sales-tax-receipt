package com.salestax.receipt.model;
import com.salestax.receipt.utils.RoundingUtil;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Item {
    private final int quantity;
    private final String name;
    private final BigDecimal price;
    private final boolean imported;
    private final ItemCategory category;

    public Item(int quantity, String name, BigDecimal price, boolean imported, ItemCategory category) {
        this.quantity = quantity;
        this.name = name;
        this.price = price;
        this.imported = imported;
        this.category = category;
    }

    public BigDecimal calculateTax() {
        BigDecimal tax = BigDecimal.ZERO;

        if (!category.isTaxExempt()) {
            tax = tax.add(price.multiply(BigDecimal.valueOf(0.10)));
        }
        if (imported) {
            tax = tax.add(price.multiply(BigDecimal.valueOf(0.05)));
        }

        return RoundingUtil.roundUpToNearestFiveCents(tax);
    }

    public BigDecimal getTotalPrice() {
        return price.add(calculateTax()).multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return quantity + " " + (imported ? "imported " : "") + name + ": " + getTotalPrice();
    }

    public BigDecimal getTotalTax() {
        return calculateTax().multiply(BigDecimal.valueOf(quantity));
    }
}
