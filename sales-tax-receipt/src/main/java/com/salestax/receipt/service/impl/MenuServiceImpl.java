package com.salestax.receipt.service.impl;

import com.salestax.receipt.exception.ValidationException;
import com.salestax.receipt.model.Item;
import com.salestax.receipt.model.ItemCategory;
import com.salestax.receipt.model.Receipt;
import com.salestax.receipt.service.MenuService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.math.BigDecimal;

public class MenuServiceImpl implements MenuService {
    private final Scanner scanner;
    private final List<Item> basket = new ArrayList<>();
    private final ReceiptServiceImpl receiptServiceImpl;

    public MenuServiceImpl(Scanner scanner, ReceiptServiceImpl receiptServiceImpl) {
        this.scanner = scanner;
        this.receiptServiceImpl = receiptServiceImpl;
    }

    public MenuServiceImpl() {
        this(new Scanner(System.in), new ReceiptServiceImpl());
    }
    @Override
    public void start() {
        System.out.println("Welcome to Sales Tax Receipt App");
        while (true) {
            System.out.println("\n1. Add Item to Basket");
            System.out.println("2. View Receipt");
            System.out.println("3. Clear Basket");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    printReceipt();
                    break;
                case 3:
                    basket.clear();
                    System.out.println("Basket cleared.");
                    break;
                case 4:
                    System.out.println("Exiting. Thank you!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void addItem() {
        try {
            System.out.print("Enter item name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) throw new ValidationException("Item name cannot be empty.");

            System.out.print("Enter item price: ");
            String priceInput = scanner.nextLine().trim();
            if (priceInput.isEmpty()) throw new ValidationException("Item price cannot be empty.");
            BigDecimal price;
            try {
                price = new BigDecimal(priceInput);
                if (price.compareTo(BigDecimal.ZERO) <= 0)
                    throw new ValidationException("Price must be greater than 0.");
            } catch (NumberFormatException e) {
                throw new ValidationException("Invalid price format. Please enter a numeric value.");
            }

            System.out.print("Enter quantity: ");
            String qtyInput = scanner.nextLine().trim();
            if (qtyInput.isEmpty()) throw new ValidationException("Quantity cannot be empty.");
            int qty;
            try {
                qty = Integer.parseInt(qtyInput);
                if (qty <= 0) throw new ValidationException("Quantity must be a positive integer.");
            } catch (NumberFormatException e) {
                throw new ValidationException("Invalid quantity. Please enter a whole number.");
            }

            System.out.print("Is it imported? (yes/no): ");
            String importedInput = scanner.nextLine().trim().toLowerCase();
            if (!importedInput.equals("yes") && !importedInput.equals("no")) {
                throw new ValidationException("Imported field must be 'yes' or 'no'.");
            }
            boolean imported = importedInput.equals("yes");

            System.out.print("Enter category (BOOK, FOOD, MEDICAL, OTHER): ");
            String cat = scanner.nextLine().trim().toUpperCase();
            if (!(cat.equals("BOOK") || cat.equals("FOOD") || cat.equals("MEDICAL") || cat.equals("OTHER"))) {
                throw new ValidationException("Invalid category. Must be one of: BOOK, FOOD, MEDICAL, OTHER.");
            }
            boolean exempt = !cat.equals("OTHER");

            ItemCategory category = new ItemCategory(cat, exempt);
            Item item = new Item(qty, name, price, imported, category);
            basket.add(item);
            System.out.println("Item added successfully.");

        } catch (ValidationException e) {
            System.out.println("Input Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }
    }

    private void printReceipt() {
        Receipt receipt = receiptServiceImpl.process(basket);
        System.out.println("-----------------------------------------------");
        System.out.println(receipt);
        System.out.println("-----------------------------------------------");
    }
}