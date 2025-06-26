package com.salestax.receipt.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RoundingUtil {
    public static BigDecimal roundUpToNearestFiveCents(BigDecimal value) {
        return new BigDecimal(Math.ceil(value.doubleValue() * 20.0) / 20.0)
                .setScale(2, RoundingMode.HALF_UP);
    }
}