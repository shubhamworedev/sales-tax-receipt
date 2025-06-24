package com.salestax.receipt.service;

import com.salestax.receipt.model.Item;
import com.salestax.receipt.model.Receipt;

import java.util.List;

public interface ReceiptService {
    Receipt process(List<Item> items);
}
