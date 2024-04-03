package com.patsi.utils;

public class TokenHelper {
    public static String removeBearer(String token) {
        return token.substring(7);
    }
}
