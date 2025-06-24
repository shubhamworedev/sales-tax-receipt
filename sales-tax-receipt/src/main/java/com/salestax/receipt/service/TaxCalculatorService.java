package com.salestax.receipt.service;

import com.salestax.receipt.model.Item;

import java.math.BigDecimal;

public interface TaxCalculatorService {
    BigDecimal calculateTax(Item item);
}
