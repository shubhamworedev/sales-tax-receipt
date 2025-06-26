package com.salestax.receipt.model;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ReceiptItem {
    private Item item;
    private BigDecimal totalPrice;
    private BigDecimal taxAmount;

    public ReceiptItem(Item item, BigDecimal totalPrice, BigDecimal taxAmount) {
        this.item = item;
        this.totalPrice = totalPrice;
        this.taxAmount = taxAmount;
    }

    @Override
    public String toString() {
        return item.getQuantity() + " " + (item.isImported() ? "imported " : "") +
                item.getName() + ": " + totalPrice.setScale(2);
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}