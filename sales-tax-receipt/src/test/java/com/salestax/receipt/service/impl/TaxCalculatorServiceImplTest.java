package com.salestax.receipt.service.impl;

import com.salestax.receipt.model.Item;
import com.salestax.receipt.model.ItemCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TaxCalculatorServiceImplTest {

    private TaxCalculatorServiceImpl taxCalculator;

    @BeforeEach
    void setup() {
        taxCalculator = new TaxCalculatorServiceImpl();
    }

    @Test
    void shouldApplyBasicTaxOnly_WhenNotExemptAndNotImported() {
        Item item = new Item(1, "Music CD", new BigDecimal("14.99"), false, new ItemCategory("OTHER", false));
        BigDecimal tax = taxCalculator.calculateTax(item);

        assertEquals(new BigDecimal("1.50"), tax);
    }

    @Test
    void shouldApplyImportTaxOnly_WhenExemptAndImported() {
        Item item = new Item(1, "Imported Chocolate", new BigDecimal("10.00"), true, new ItemCategory("FOOD", true));
        BigDecimal tax = taxCalculator.calculateTax(item);

        assertEquals(new BigDecimal("0.50"), tax);
    }

    @Test
    void shouldApplyBothTaxes_WhenNotExemptAndImported() {
        Item item = new Item(1, "Imported Perfume", new BigDecimal("47.50"), true, new ItemCategory("OTHER", false));
        BigDecimal tax = taxCalculator.calculateTax(item);

        assertEquals(new BigDecimal("7.15"), tax);
    }

    @Test
    void shouldApplyNoTax_WhenExemptAndNotImported() {
        Item item = new Item(1, "Book", new BigDecimal("12.49"), false, new ItemCategory("BOOK", true));
        BigDecimal tax = taxCalculator.calculateTax(item);

        assertEquals(new BigDecimal("0.00"), tax);
    }
}
