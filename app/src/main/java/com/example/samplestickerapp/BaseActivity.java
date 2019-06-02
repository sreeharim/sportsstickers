/*
 * Copyright (c) WhatsApp Inc. and its affiliates.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.samplestickerapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;



public abstract class BaseActivity extends AppCompatActivity {
    FrameLayout content;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static final class MessageDialogFragment extends DialogFragment {
        private static final String ARG_TITLE_ID = "title_id";
        private static final String ARG_MESSAGE = "message";

        public static DialogFragment newInstance(@StringRes int titleId, String message) {
            DialogFragment fragment = new MessageDialogFragment();
            Bundle arguments = new Bundle();
            arguments.putInt(ARG_TITLE_ID, titleId);
            arguments.putString(ARG_MESSAGE, message);
            fragment.setArguments(arguments);
            return fragment;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            @StringRes final int title = getArguments().getInt(ARG_TITLE_ID);
            String message = getArguments().getString(ARG_MESSAGE);

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity())
                    .setMessage(message)
                    .setCancelable(true)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> dismiss());

            if (title != 0) {
                dialogBuilder.setTitle(title);
            }
            return dialogBuilder.create();
        }
    }

    @SuppressLint("NewApi")
    protected void setupAdAtBottom() {
        content = (FrameLayout) findViewById(android.R.id.content);
        // inflate ad layout and set it to bottom by layouparams
        final LinearLayout ad = (LinearLayout) getLayoutInflater()
                .inflate(R.layout.fb_banner, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        ad.setLayoutParams(params);

        // adding viewtreeobserver to get height of ad layout , so that
        // android.R.id.content will set margin of that height
        ViewTreeObserver vto = ad.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    ad.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    ad.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                int width = ad.getMeasuredWidth();
                int height = ad.getMeasuredHeight();
                Log.i("ad hight", height + "");
                setSpaceForAd(height);

            }

        });
        addLayoutToContent(ad);

    }

    private void setSpaceForAd(int height) {

        // content.getChildAt(0).setPadding(0, 0, 0, 50);
        View child0 = content.getChildAt(0);
        FrameLayout.LayoutParams layoutparams = (android.widget.FrameLayout.LayoutParams) child0
                .getLayoutParams();
        layoutparams.bottomMargin = height;
        child0.setLayoutParams(layoutparams);

    }

    private void addLayoutToContent(View ad) {
        content.addView(ad);
        AdView adView = new com.facebook.ads.AdView(this, getResources().getString(R.string.banner_id), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adView.loadAd();

    }
}
