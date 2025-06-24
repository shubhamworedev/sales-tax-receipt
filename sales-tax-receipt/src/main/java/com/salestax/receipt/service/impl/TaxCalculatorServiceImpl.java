package com.salestax.receipt.service.impl;

import com.salestax.receipt.model.Item;
import com.salestax.receipt.service.TaxCalculatorService;
import com.salestax.receipt.utils.RoundingUtil;

import java.math.BigDecimal;

public class TaxCalculatorServiceImpl implements TaxCalculatorService {
    private static final BigDecimal BASIC_RATE = new BigDecimal("0.10");
    private static final BigDecimal IMPORT_RATE = new BigDecimal("0.05");

    @Override
    public BigDecimal calculateTax(Item item) {
        BigDecimal tax = BigDecimal.ZERO;
        if (!item.getCategory().isExempt())
            tax = tax.add(item.getPrice().multiply(BASIC_RATE));
        if (item.isImported())
            tax = tax.add(item.getPrice().multiply(IMPORT_RATE));
        return RoundingUtil.roundUpToNearestFiveCents(tax);
    }
}