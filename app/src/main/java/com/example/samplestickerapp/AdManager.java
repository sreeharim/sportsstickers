package com.example.samplestickerapp;

import android.content.Context;


import com.facebook.ads.InterstitialAd;

import java.util.Date;

public class AdManager {
    // Static fields are shared between all instances.
    public static InterstitialAd interstitialAd;
    public static Date adShownTime = null;

    public AdManager(Context adContext) {
        createAd(adContext);
    }

    public void createAd(Context adContext) {
        interstitialAd = new InterstitialAd(adContext,"484697385608416_484701992274622");

        interstitialAd.loadAd();
    }


}