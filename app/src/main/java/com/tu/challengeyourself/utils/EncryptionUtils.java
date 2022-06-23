package com.tu.challengeyourself.utils;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Base64;

public class EncryptionUtils {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encrypt(String plainText) {
        String b64encoded = Base64.getEncoder().encodeToString(plainText.getBytes());
        String reverse = new StringBuffer(b64encoded).reverse().toString();
        StringBuilder stringBuilder = new StringBuilder();
        final int OFFSET = 4;
        for (int i = 0; i < reverse.length(); i++) {
            stringBuilder.append((char)(reverse.charAt(i) + OFFSET));
        }
        return stringBuilder.toString();
    }
}
