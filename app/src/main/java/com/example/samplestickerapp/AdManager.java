package com.example.samplestickerapp;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Date;

public class AdManager {
    // Static fields are shared between all instances.
    public static InterstitialAd interstitialAd;
    public static Date adShownTime = null;

    public AdManager(Context adContext) {
        createAd(adContext);
    }

    public void createAd(Context adContext) {
        // Create an ad.
        interstitialAd = new InterstitialAd(adContext);
        interstitialAd.setAdUnitId(Constants.INTERSTITIAL_AD_ID);

        AdRequest adRequest = new AdRequest.Builder().build();
        //AdRequest adRequest = new AdRequest.Builder().addTestDevice("6706477978B844800256B79CEBE84DA6").build();

        // Load the interstitial ad.
        interstitialAd.loadAd(adRequest);
    }


}