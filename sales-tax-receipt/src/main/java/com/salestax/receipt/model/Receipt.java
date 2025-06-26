package com.salestax.receipt.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public class Receipt {
    private List<Item> items;

    public BigDecimal getTotalTax() {
        return items.stream()
                .map(Item::getTotalTax)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalAmount() {
        return items.stream()
                .map(Item::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        for (Item item : items) {
            String imported="";
            if(item.isImported()) imported="imported ";
            sb.append(item.getQuantity())
                    .append(" ")
                    .append(imported)
                    .append(item.getName())
                    .append(": ")
                    .append(item.getTotalPrice().setScale(2)).append("\n");
        }
        sb.append("Sales Taxes: ").append(getTotalTax().setScale(2)).append("\n");
        sb.append("Total: ").append(getTotalAmount().setScale(2));
        return sb.toString();
    }
}
