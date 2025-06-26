package com.salestax.receipt;

import com.salestax.receipt.model.Item;
import com.salestax.receipt.model.ItemCategory;
import com.salestax.receipt.model.Receipt;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReceiptTest {

    @Test
    void testGetTotalTax_withMocks() {
        Item mockItem1 = mock(Item.class);
        Item mockItem2 = mock(Item.class);

        when(mockItem1.getTotalTax()).thenReturn(new BigDecimal("2.50"));
        when(mockItem2.getTotalTax()).thenReturn(new BigDecimal("1.00"));

        Receipt receipt = new Receipt(List.of(mockItem1, mockItem2));
        assertEquals(new BigDecimal("3.50"), receipt.getTotalTax());

        verify(mockItem1).getTotalTax();
        verify(mockItem2).getTotalTax();
    }

    @Test
    void testGetTotalAmount_withMocks() {
        Item mockItem1 = mock(Item.class);
        Item mockItem2 = mock(Item.class);

        when(mockItem1.getTotalPrice()).thenReturn(new BigDecimal("10.00"));
        when(mockItem2.getTotalPrice()).thenReturn(new BigDecimal("20.00"));

        Receipt receipt = new Receipt(List.of(mockItem1, mockItem2));
        assertEquals(new BigDecimal("30.00"), receipt.getTotalAmount());

        verify(mockItem1).getTotalPrice();
        verify(mockItem2).getTotalPrice();
    }

    @Test
    void testPrint_actualData() {
        ItemCategory food = new ItemCategory("FOOD", true);
        Item item = new Item(1, "box of chocolates", new BigDecimal("10.00"), true, food);
        Receipt receipt = new Receipt(List.of(item));

        String expected = "1 imported box of chocolates: 10.50\n" +
                "Sales Taxes: 0.50\n" +
                "Total: 10.50";

        assertEquals(expected, receipt.print());
    }
}
