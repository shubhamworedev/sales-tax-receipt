package com.salestax.receipt.service.impl;

import com.salestax.receipt.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuServiceImplTest {

    private ReceiptServiceImpl receiptService;
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    void setup() {
        receiptService = mock(ReceiptServiceImpl.class);
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void shouldAddValidItem() throws Exception {
        String input = String.join("\n",
                "Book",      // name
                "12.49",     // price
                "1",         // qty
                "no",        // imported
                "BOOK"       // category
        );
        Scanner scanner = new Scanner(input);
        MenuServiceImpl menu = new MenuServiceImpl(scanner, receiptService);

        Method addItem = MenuServiceImpl.class.getDeclaredMethod("addItem");
        addItem.setAccessible(true);
        addItem.invoke(menu);

        List<Item> basket = getBasket(menu);
        assertEquals(1, basket.size());
        assertEquals("Book", basket.get(0).getName());
    }

    @Test
    void shouldPrintReceipt() throws Exception {
        Scanner scanner = new Scanner("dummy");
        MenuServiceImpl menu = new MenuServiceImpl(scanner, receiptService);

        List<Item> basket = getBasket(menu);
        Item item = new Item(1, "Book", new BigDecimal("10.00"), false, new ItemCategory("BOOK", true));
        basket.add(item);

        Receipt mockReceipt = new Receipt();
        mockReceipt.addItem(new ReceiptItem(item, new BigDecimal("10.00"), BigDecimal.ZERO));
        when(receiptService.process(any())).thenReturn(mockReceipt);

        Method printReceipt = MenuServiceImpl.class.getDeclaredMethod("printReceipt");
        printReceipt.setAccessible(true);
        printReceipt.invoke(menu);

        String output = outContent.toString();
        assertTrue(output.contains("1 Book: 10.00"));
    }

    @Test
    void shouldClearBasket() throws Exception {
        Scanner scanner = new Scanner("dummy");
        MenuServiceImpl menu = new MenuServiceImpl(scanner, receiptService);

        List<Item> basket = getBasket(menu);
        basket.add(new Item(1, "CD", new BigDecimal("15.00"), false, new ItemCategory("OTHER", false)));

        Method clearMethod = MenuServiceImpl.class.getDeclaredMethod("start");
        clearMethod.setAccessible(true);

        Field scannerField = MenuServiceImpl.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(menu, new Scanner("3\n4\n")); // clear + exit

        clearMethod.invoke(menu);

        assertTrue(getBasket(menu).isEmpty());
        assertTrue(outContent.toString().contains("Basket cleared."));
    }

    private List<Item> getBasket(MenuServiceImpl menu) throws Exception {
        Field basketField = MenuServiceImpl.class.getDeclaredField("basket");
        basketField.setAccessible(true);
        return (List<Item>) basketField.get(menu);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }
}
