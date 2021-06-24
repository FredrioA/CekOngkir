package com.example.cekongkir.utils;


import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.Locale;

public class Constants {

    public static final String API_KEY              = "89b50fde087a5e9c8fbe4defb8437de0";
    public static final String BASE_URL             = "https://api.rajaongkir.com/starter/";
    public static boolean tiki = false;
    public static boolean jne = false;
    public static boolean pos = false;

    @NotNull
    public static String formatRupiah(int price) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(localeID);
        return format.format(price);
    }
}
