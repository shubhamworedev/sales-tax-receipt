package com.salestax.receipt.service.impl;

import com.salestax.receipt.model.Item;
import com.salestax.receipt.model.ItemCategory;
import com.salestax.receipt.model.Receipt;
import com.salestax.receipt.model.ReceiptItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReceiptServiceImplTest {

    private TaxCalculatorServiceImpl mockTaxService;
    private ReceiptServiceImpl receiptService;

    @BeforeEach
    void setUp() {
        mockTaxService = mock(TaxCalculatorServiceImpl.class);
        receiptService = new ReceiptServiceImpl(mockTaxService);
    }

    @Test
    void shouldCalculateReceiptUsingMockedTax() {
        Item item = new Item(2, "CD", new BigDecimal("15.00"), false, new ItemCategory("OTHER", false));
        when(mockTaxService.calculateTax(item)).thenReturn(new BigDecimal("1.50"));

        Receipt receipt = receiptService.process(List.of(item));
        ReceiptItem receiptItem = receipt.getItems().get(0);

        assertEquals(new BigDecimal("3.00"), receiptItem.getTaxAmount());
        assertEquals(new BigDecimal("33.00"), receiptItem.getTotalPrice());

        verify(mockTaxService, times(1)).calculateTax(item);
    }
}
