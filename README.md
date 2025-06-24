# Sales Tax Receipt CLI (Java)

A simple Java console application to calculate receipts for shopping baskets. It applies sales tax, import duty, and rounds values as per the rules.

---

## Requirements

- Java 11 or higher  
- No Spring, no GUI — just plain Java console

---

## How to Test and Run

1. Open terminal and go to the project directory:

   cd sales-tax-receipt

2. Run tests:

   mvn test

3. Run the application:

   mvn exec:java

---

## Menu Options

You will be presented with the following menu:

1. Add Item to Basket  
2. View Receipt  
3. Clear Basket  
4. Exit  

Type a number to select the option.

---

## Add Item (Option 1)

The app will prompt you to enter the following:

- Name: required, non-empty string  
- Price: required, a decimal number greater than 0  
- Quantity: required, an integer greater than 0  
- Imported: required, enter "yes" or "no"  
- Category: required, must be one of: BOOK, FOOD, MEDICAL, OTHER (case-insensitive)

---


## View Receipt (Option 2)


Show reciept.

---

## Clear Basket (Option 3)

Removes all items from the basket.

---

## Exit (Option 4)

Closes the application.

---
