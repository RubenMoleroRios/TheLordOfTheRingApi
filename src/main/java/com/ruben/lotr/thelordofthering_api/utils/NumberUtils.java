package com.ruben.lotr.thelordofthering_api.utils;

public class NumberUtils {
    public static boolean isDouble(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str.trim());
            return true; // si no lanza excepción, es convertible
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
