package com.salestax.receipt;
import com.salestax.receipt.model.Item;
import com.salestax.receipt.model.ItemCategory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemTest {

    @Test
    void testCalculateTax_basicItem() {
        ItemCategory other = new ItemCategory("OTHER", false);
        Item item = new Item(1, "music CD", new BigDecimal("14.99"), false, other);
        assertEquals(new BigDecimal("1.50"), item.calculateTax());
    }

    @Test
    void testCalculateTax_importedExemptItem() {
        ItemCategory food = new ItemCategory("FOOD", true);
        Item item = new Item(1, "imported chocolates", new BigDecimal("10.00"), true, food);
        assertEquals(new BigDecimal("0.50"), item.calculateTax());
    }

    @Test
    void testCalculateTax_importedAndTaxableItem() {
        ItemCategory other = new ItemCategory("OTHER", false);
        Item item = new Item(1, "imported perfume", new BigDecimal("47.50"), true, other);
        assertEquals(new BigDecimal("7.15"), item.calculateTax());
    }

    @Test
    void testGetTotalPrice_withTax() {
        ItemCategory other = new ItemCategory("OTHER", false);
        Item item = new Item(2, "imported perfume", new BigDecimal("47.50"), true, other);
        assertEquals(new BigDecimal("109.30"), item.getTotalPrice());
    }
}
