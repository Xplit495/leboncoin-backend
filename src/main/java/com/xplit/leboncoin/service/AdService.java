package com.xplit.leboncoin.service;

import com.xplit.leboncoin.model.Ad;

import java.util.List;

public class AdService {

    public static void showAds(List<Ad> ads) {
        for (int i = 0; i < 40; i++) {System.out.println('\n');}
        System.out.println("\nListe des annonces :");
        ads.forEach(System.out::println);
    }
}
