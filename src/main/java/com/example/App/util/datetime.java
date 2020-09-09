package com.example.App.util;

import java.util.Calendar;

public class datetime {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        java.sql.Date date = new java.sql.Date(currentDate.getTime());
        System.out.println(date);
    }
}
