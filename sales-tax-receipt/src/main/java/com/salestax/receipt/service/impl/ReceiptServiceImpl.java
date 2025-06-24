package com.salestax.receipt.service.impl;

import com.salestax.receipt.model.Item;
import com.salestax.receipt.model.Receipt;
import com.salestax.receipt.model.ReceiptItem;
import com.salestax.receipt.service.ReceiptService;

import java.math.BigDecimal;
import java.util.List;

public class ReceiptServiceImpl implements ReceiptService {
    private final TaxCalculatorServiceImpl taxCalculatorServiceImpl;

    public ReceiptServiceImpl() {
        this(new TaxCalculatorServiceImpl());
    }

    public ReceiptServiceImpl(TaxCalculatorServiceImpl taxCalculatorServiceImpl) {
        this.taxCalculatorServiceImpl = taxCalculatorServiceImpl;
    }


    @Override
    public Receipt process(List<Item> items) {
        Receipt receipt = new Receipt();
        for (Item item : items) {
            BigDecimal tax = taxCalculatorServiceImpl.calculateTax(item)
                .multiply(BigDecimal.valueOf(item.getQuantity()));
            BigDecimal total = item.getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()))
                .add(tax);
            receipt.addItem(new ReceiptItem(item, total, tax));
        }
        return receipt;
    }
}