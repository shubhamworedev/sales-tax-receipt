package com.salestax.receipt;

import com.salestax.receipt.exception.InvalidInputException;
import com.salestax.receipt.model.Item;
import com.salestax.receipt.model.ItemCategory;
import com.salestax.receipt.model.Receipt;
import com.salestax.receipt.utils.InputValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Item> items = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Welcome to Sales Tax Receipt CLI");

        while (true) {
            try {
                System.out.print("\nEnter item :\n> ");
                String input = scanner.nextLine().trim();

                Item item = parseInputLine(input);
                items.add(item);
                System.out.println("Item added: " + item);

                while(true) {
                    System.out.println("\nWhat would you like to do next?");
                    System.out.println("1. Add more");
                    System.out.println("2. Show receipt and exit");

                    String choice = scanner.nextLine().trim();
                    if ("2".equals(choice)) {
                        Receipt receipt = new Receipt(items);
                        System.out.println("\n\n"+receipt.print());
                        return;
                    }
                    else if ("1".equals(choice)) {
                        break;
                    } else {
                        System.out.println("Invalid choice.");
                    }
                }

            } catch (InvalidInputException e) {
                System.out.printf("❌ Invalid input (%s): %s%n", e.getField(), e.getMessage());
            } catch (Exception e) {
                System.out.println("❌ Unexpected error: " + e.getMessage());
            }
        }

    }

    private static Item parseInputLine(String line) {
        Pattern pattern = Pattern.compile("^(\\d+)\\s+(.+?)\\s+at\\s+(\\d+(\\.\\d{1,2})?)$");
        Matcher matcher = pattern.matcher(line);

        if (!matcher.matches()) {
            throw new InvalidInputException("Invalid input format. Expected: '<quantity> <item name> at <price>'", "input");
        }

        String quantityStr = matcher.group(1);
        String rawName = matcher.group(2);
        String priceStr = matcher.group(3);

        int quantity = InputValidator.validateQuantity(quantityStr);
        BigDecimal price = InputValidator.validatePrice(priceStr);

        boolean imported = rawName.toLowerCase().contains("imported");
        String name = InputValidator.validateName(rawName.replace("imported","").trim());

        String categoryKey;
        String lowerName = name.toLowerCase();
        if (lowerName.contains("book")) {
            categoryKey = "BOOK";
        } else if (lowerName.contains("chocolate") || lowerName.contains("food")) {
            categoryKey = "FOOD";
        } else if (lowerName.contains("pills") || lowerName.contains("tablet") || lowerName.contains("medicine")) {
            categoryKey = "MEDICAL";
        } else {
            categoryKey = "OTHER";
        }

        String finalCategory = InputValidator.validateCategory(categoryKey);
        boolean isExempt = !finalCategory.equals("OTHER");

        ItemCategory category = new ItemCategory(finalCategory, isExempt);

        return new Item(quantity, name, price, imported, category);
    }
}
