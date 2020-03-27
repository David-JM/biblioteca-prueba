package com.ceiba.biblioteca.dominio.util;

import java.util.Arrays;

public final class IsbnUtility {

    public static final String REGEX_FOR_ALL_NON_DIGIT = "\\d+";

    private IsbnUtility() {
    }

    public static String reverse(String isbn) {
        return new StringBuilder(isbn).reverse().toString();
    }

    public static int sumOnlyNumbers(String isbn) {
        return Arrays.stream(isbn.split(""))
                .filter(s -> s.matches(REGEX_FOR_ALL_NON_DIGIT))
                .mapToInt(Integer::parseInt)
                .sum();
    }
}
