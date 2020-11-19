package com.example.init_app_vpn_native.utils.ads;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.init_app_vpn_native.R;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

public class AdsActivity extends Activity {
    ImageView imgClose;
    FrameLayout frmAds;

    @Override
    public void onBackPressed() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("CLOSE"));
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        String type = getIntent().getStringExtra("type");
        imgClose = findViewById(R.id.imgCancelAds);
        frmAds = findViewById(R.id.frmAds);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (type.equals("native")) {
            inflateAdView(R.layout.ad_unified_draw_navigator_inter_native, AdsUtils.unifiedNativeAds);
        }
        if (type.equals("banner")) {
            View view = getLayoutInflater().inflate(R.layout.banner_ads, null, false);
            FrameLayout frm = view.findViewById(R.id.frmAdsBannerAdmob);
            frm.addView(AdsUtils.bannerAdmob);
            frmAds.removeAllViews();
            frmAds.addView(view);
        }
    }

    private void inflateAdView(int layout, UnifiedNativeAd unifiedNativeAds) {
        UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater()
                .inflate(layout, null);
        populateUnifiedNativeAdView(unifiedNativeAds, adView, layout);
        frmAds.removeAllViews();
        frmAds.addView(adView);

    }

    private void populateUnifiedNativeAdView(UnifiedNativeAd unifiedNativeAd, UnifiedNativeAdView adView, int layout) {
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setMediaView((com.google.android.gms.ads.formats.MediaView) adView.findViewById(R.id.ad_media));
//        adView.setMediaView(null);
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        if (adView.getPriceView() != null) {
            if (unifiedNativeAd.getPrice() == null || unifiedNativeAd.getPrice().isEmpty())
                adView.getPriceView().setVisibility(View.GONE);
            else {
                ((TextView) adView.getPriceView()).setText(unifiedNativeAd.getPrice());
                if (unifiedNativeAd.getPrice().equals("0"))
                    adView.getPriceView().setVisibility(View.GONE);
            }
        }
        if (adView.getStoreView() != null) {
            if (unifiedNativeAd.getStore() == null || unifiedNativeAd.getStore().isEmpty())
                adView.getStoreView().setVisibility(View.GONE);
            else {
                ((TextView) adView.getStoreView()).setText(unifiedNativeAd.getStore());
            }
        }
        if (adView.getIconView() != null) {
            if (unifiedNativeAd.getIcon() == null)
                adView.getIconView().setVisibility(View.GONE);
            else {
                adView.getIconView().setVisibility(View.VISIBLE);
                ((ImageView) adView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
            }
        }
        if (adView.getHeadlineView() != null) {
            if (unifiedNativeAd.getHeadline() == null || unifiedNativeAd.getHeadline().isEmpty())
                adView.getHeadlineView().setVisibility(View.GONE);
            else {
                adView.getHeadlineView().setVisibility(View.VISIBLE);
                ((TextView) adView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
            }
        }
        if (adView.getAdvertiserView() != null) {
            if (unifiedNativeAd.getAdvertiser() == null || unifiedNativeAd.getAdvertiser().isEmpty())
                adView.getAdvertiserView().setVisibility(View.GONE);
            else {
                adView.getAdvertiserView().setVisibility(View.VISIBLE);
                ((TextView) adView.getAdvertiserView()).setText(unifiedNativeAd.getAdvertiser());
            }
        }
        if (adView.getBodyView() != null) {
            if (unifiedNativeAd.getBody() == null || unifiedNativeAd.getBody().isEmpty())
                adView.getBodyView().setVisibility(View.GONE);
            else {
                adView.getBodyView().setVisibility(View.VISIBLE);
                ((TextView) adView.getBodyView()).setText(unifiedNativeAd.getBody());
            }
        }
        if (adView.getStarRatingView() != null) {
            if (unifiedNativeAd.getStarRating() == null)
                adView.getStarRatingView().setVisibility(View.GONE);
            else {
                adView.getStarRatingView().setVisibility(View.VISIBLE);
                ((RatingBar) adView.getStarRatingView()).setRating(unifiedNativeAd.getStarRating().floatValue());
            }
        }
        if (adView.getMediaView() != null) {
            if (unifiedNativeAd.getMediaContent() == null)
                adView.getMediaView().setVisibility(View.GONE);
            else {
                adView.getMediaView().setVisibility(View.VISIBLE);
                ((com.google.android.gms.ads.formats.MediaView) adView.getMediaView()).setMediaContent(unifiedNativeAd.getMediaContent());
            }
        }
        if (adView.getCallToActionView() != null) {
            if (unifiedNativeAd.getCallToAction() == null || unifiedNativeAd.getCallToAction().isEmpty())
                adView.getCallToActionView().setVisibility(View.GONE);
            else {
                adView.getCallToActionView().setVisibility(View.VISIBLE);
                ((TextView) adView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
            }
        }
        adView.setNativeAd(unifiedNativeAd);
    }

}