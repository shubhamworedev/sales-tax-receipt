package com.salestax.receipt.utils;

import com.salestax.receipt.exception.InvalidInputException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class InputValidator {

    private static final List<String> VALID_CATEGORIES = Arrays.asList("BOOK", "FOOD", "MEDICAL", "OTHER");

    public static String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Item name cannot be empty.", "name");
        }
        return name.trim();
    }

    public static BigDecimal validatePrice(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException("Price cannot be empty.", "price");
        }
        try {
            BigDecimal price = new BigDecimal(input.trim());
            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new InvalidInputException("Price must be greater than 0.", "price");
            }
            return price;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid price format. Please enter a numeric value.", "price");
        }
    }

    public static int validateQuantity(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException("Quantity cannot be empty.", "quantity");
        }
        try {
            int qty = Integer.parseInt(input.trim());
            if (qty <= 0) {
                throw new InvalidInputException("Quantity must be greater than 0.", "quantity");
            }
            return qty;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid quantity format. Please enter an integer.", "quantity");
        }
    }


    public static String validateCategory(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException("Category cannot be empty.", "category");
        }
        String cat = input.trim().toUpperCase();
        if (!VALID_CATEGORIES.contains(cat)) {
            throw new InvalidInputException("Invalid category. Must be one of: BOOK, FOOD, MEDICAL, OTHER.", "category");
        }
        return cat;
    }
}
