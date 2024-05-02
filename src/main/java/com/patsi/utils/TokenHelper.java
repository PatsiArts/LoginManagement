package com.patsi.utils;

public class TokenHelper {
    public static String removeBearer(String token) {
        if (token.contains("Bearer")) {
            return token.substring(7);
        } else
            return token;
    }
}
