package com.salestax.receipt.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Data
public class Receipt {
    private List<ReceiptItem> items = new ArrayList<>();

    public void addItem(ReceiptItem item) {
        items.add(item);
    }

    @Override
    public String toString() {
        BigDecimal totalTax = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        StringBuilder sb = new StringBuilder();

        for (ReceiptItem item : items) {
            sb.append(item).append("\n");
            totalTax = totalTax.add(item.getTaxAmount());
            total = total.add(item.getTotalPrice());
        }

        sb.append("Sales Taxes: ").append(totalTax.setScale(2)).append("\n");
        sb.append("Total: ").append(total.setScale(2));
        return sb.toString();
    }
}