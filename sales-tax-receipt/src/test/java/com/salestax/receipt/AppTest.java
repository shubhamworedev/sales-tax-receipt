package com.salestax.receipt;

import com.salestax.receipt.model.Item;
import com.salestax.receipt.model.ItemCategory;
import com.salestax.receipt.model.Receipt;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void testReceiptOutput_singleItem() {
        ItemCategory medical = new ItemCategory("MEDICAL", true);
        Item item = new Item(1, "headache pills", new BigDecimal("9.75"), true, medical);
        Receipt receipt = new Receipt(List.of(item));

        String expected = "1 imported headache pills: 10.25\n" +
                "Sales Taxes: 0.50\n" +
                "Total: 10.25";

        assertEquals(expected, receipt.print());
    }

    @Test
    void testReceiptOutput_multipleItems() {
        ItemCategory book = new ItemCategory("BOOK", true);
        ItemCategory food = new ItemCategory("FOOD", true);
        ItemCategory other = new ItemCategory("OTHER", false);

        Item item1 = new Item(1, "book", new BigDecimal("12.49"), false, book);
        Item item2 = new Item(1, "music CD", new BigDecimal("14.99"), false, other);
        Item item3 = new Item(1, "chocolate bar", new BigDecimal("0.85"), false, food);

        Receipt receipt = new Receipt(List.of(item1, item2, item3));

        String expected = "1 book: 12.49\n" +
                "1 music CD: 16.49\n" +
                "1 chocolate bar: 0.85\n" +
                "Sales Taxes: 1.50\n" +
                "Total: 29.83";

        assertEquals(expected, receipt.print());
    }

    @Test
    void testParseInputLine_validImportedItem() throws Exception {
        Method parseMethod = App.class.getDeclaredMethod("parseInputLine", String.class);
        parseMethod.setAccessible(true);

        String input = "1 imported bottle of perfume at 27.99";
        Item item = (Item) parseMethod.invoke(null, input);

        assertEquals(1, item.getQuantity());
        assertEquals("bottle of perfume", item.getName());
        assertTrue(item.isImported());
        assertEquals(new BigDecimal("27.99"), item.getPrice());
        assertEquals("OTHER", item.getCategory().getName());
        assertFalse(item.getCategory().isTaxExempt());
    }
}
