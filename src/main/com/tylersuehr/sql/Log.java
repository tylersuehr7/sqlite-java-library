package com.tylersuehr.sql;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 */
final class Log {
    static void i(Object context, String msg) {
        System.out.println(context.getClass().getSimpleName().toUpperCase() + " > " + msg);
    }

    static void wtf(Object context, Exception ex) {
        System.out.println(context.getClass().getSimpleName().toUpperCase() + " > " + ex.getMessage());
        ex.printStackTrace();
    }

    static void wtf(String tag, Exception ex) {
        System.out.println(tag + " > " + ex.getMessage());
        ex.printStackTrace();
    }
}