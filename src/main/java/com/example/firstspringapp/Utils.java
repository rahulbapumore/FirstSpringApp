package com.example.firstspringapp;

import java.util.Random;

public class Utils {

    public static String generateToken() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        return "" + Math.abs(random.nextInt()*100000000);

    }

}
