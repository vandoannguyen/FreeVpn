package com.example.init_app_vpn_native.utils.ads;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.init_app_vpn_native.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdBase;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AdsUtils {
    private static final String TAG = "AdsUtils";
    public static com.google.android.gms.ads.AdView bannerAdmob;
    Activity activity;
    static long lastRequestInterAdmob = 0, lastRequestNativeAdmob = 0;
    public static UnifiedNativeAd unifiedNativeAds;
    private static AdsUtils adsUtils;
    private long lastRequestBannerFan = 0;
    private long lastRequestInterFan = 0;
    private NativeAd nativeAdFan;
    private UnifiedNativeAd nativeAdmob;
    private RewardedVideoAd rewardedVideoAd;
    private boolean isLoadingReward = false;
    private boolean isIsClickReward = false;

    public static AdsUtils getInstance(Activity context) {
        if (adsUtils != null) {
            adsUtils.activity = context;
        }
        adsUtils = new AdsUtils(context);
        return adsUtils;
    }

    private AdsUtils(Activity context) {
        this.activity = context;
    }

    public void bannerAdMob(ViewGroup frameAds, String idBanner, com.google.android.gms.ads.AdSize bannerSize) {
        com.google.android.gms.ads.AdView adView = new com.google.android.gms.ads.AdView(activity);
        adView.setAdUnitId(idBanner);
        adView.setAdSize(bannerSize);
        adView.loadAd(new AdRequest.Builder().build());
        frameAds.removeAllViews();
        frameAds.addView(adView);
    }

    public void interAdmob(String idInter, com.google.android.gms.ads.AdListener listener) {
        long now = Calendar.getInstance().getTimeInMillis();
        if (checkLastestRequest(now, lastRequestInterAdmob) > 120 * 1000) {
            lastRequestInterAdmob = Calendar.getInstance().getTimeInMillis();
            com.google.android.gms.ads.InterstitialAd interstitialAd = new com.google.android.gms.ads.InterstitialAd(activity);
            interstitialAd.setAdUnitId(idInter);
            interstitialAd.setAdListener(new com.google.android.gms.ads.AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    listener.onAdClosed();
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    listener.onAdFailedToLoad(i);
                }

                @Override
                public void onAdLeftApplication() {
                    super.onAdLeftApplication();
                    listener.onAdLeftApplication();
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                    listener.onAdOpened();
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    listener.onAdLoaded();
                    interstitialAd.show();
                }

                @Override
                public void onAdClicked() {
                    super.onAdClicked();
                    listener.onAdClicked();
                }

                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                    listener.onAdImpression();
                }
            });
            interstitialAd.loadAd(new AdRequest.Builder().build());
        } else {
            if (checkLastestRequest(now, lastRequestNativeAdmob) > 60 * 1000) {
                lastRequestNativeAdmob = Calendar.getInstance().getTimeInMillis();
                LocalBroadcastManager.getInstance(activity).registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        listener.onAdClosed();
                        LocalBroadcastManager.getInstance(context).unregisterReceiver(this);
                    }
                }, new IntentFilter("CLOSE"));
                AdLoader.Builder builder = new AdLoader.Builder(activity, Ads.native_id_admob).withAdListener(new com.google.android.gms.ads.AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        listener.onAdFailedToLoad(loadAdError);
                    }

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                    }
                }).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        listener.onAdLoaded();
                        unifiedNativeAds = unifiedNativeAd;
                        Intent intent = new Intent(activity, AdsActivity.class);
                        intent.putExtra("type", "native");
                        activity.startActivity(intent);
                    }
                });
                AdLoader adLoader = builder.build();
                adLoader.loadAd(new PublisherAdRequest.Builder().build());
            } else {
                LocalBroadcastManager.getInstance(activity).registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        listener.onAdClosed();
                        LocalBroadcastManager.getInstance(context).unregisterReceiver(this);
                    }
                }, new IntentFilter("CLOSE"));
                bannerAdmob = new com.google.android.gms.ads.AdView(activity);
                bannerAdmob.setAdUnitId(Ads.banner_id_admob);
                bannerAdmob.setAdSize(com.google.android.gms.ads.AdSize.MEDIUM_RECTANGLE);
                bannerAdmob.setAdListener(new com.google.android.gms.ads.AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        Intent intent = new Intent(activity, AdsActivity.class);
                        intent.putExtra("type", "banner");
                        activity.startActivity(intent);
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        listener.onAdFailedToLoad(loadAdError);
                    }
                });
                bannerAdmob.loadAd(new AdRequest.Builder().build());
            }
        }
    }

    private long checkLastestRequest(long now, long request) {
//        if (request == null) request = 0;
        Log.e(TAG, "checkLastestRequest: " + (now - request) / 1000);
        return now - request;
    }

    /**
     * @param frameAds
     * @param idBanner
     * @param bannerSize
     * @param callError  Standard Banner
     *                   <p>
     *                   BANNER_50
     *                   <p>
     *                   320x50
     *                   <p>
     *                   This banner is best suited to phones
     *                   <p>
     *                   Large Banner
     *                   <p>
     *                   BANNER_90
     *                   <p>
     *                   320x90
     *                   <p>
     *                   This banner is best suited to tablets and larger devices
     *                   <p>
     *                   Medium Rectangle
     *                   <p>
     *                   RECTANGLE_HEIGHT_250
     *                   <p>
     *                   300x250
     *                   <p>
     *                   This format is best suited for scrollable feeds or end-of-level screens
     */
    public void bannerFan(ViewGroup frameAds, String idBanner, AdSize bannerSize, FanNativeCallBack callError) {
        long now = Calendar.getInstance().getTimeInMillis();
//        if (checkLastestRequest(now, lastRequestBannerFan) > 30 * 1000) {
//            lastRequestBannerFan = now;
        AdView adView = new AdView(activity, idBanner, bannerSize);
        adView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                callError.onError();
                Log.e(TAG, "onError: fan" + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e(TAG, "onAdLoaded: fan" + ad.toString());
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        adView.loadAd();
        frameAds.removeAllViews();
        frameAds.addView(adView);
//        } else {
//            callError.onError();
//        }
    }


    public void interFan(String idInter, InterstitialAdListener interstitialAdListener) {
//        long now = Calendar.getInstance().getTimeInMillis();
//        if (checkLastestRequest(now, lastRequestInterFan) > 30 * 1000) {
//            lastRequestInterFan = now;
        InterstitialAd interstitialAd = new InterstitialAd(activity, idInter);
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                interstitialAdListener.onInterstitialDisplayed(ad);
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                interstitialAdListener.onInterstitialDismissed(ad);
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                interstitialAdListener.onError(ad, adError);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                interstitialAdListener.onAdClicked(ad);
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                interstitialAdListener.onLoggingImpression(ad);
            }
        });
        interstitialAd.loadAd();
//        } else {
//            interstitialAdListener.onError(null, null);
//        }
    }

    public void rewar_admob(String rewar_id_admob, RewardedVideoAdListener listener) {
        isIsClickReward = true;
        if (rewardedVideoAd == null)
            rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(activity);
        rewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                isLoadingReward = false;
                if (isIsClickReward) {
                    rewardedVideoAd.show();
                }
                listener.onRewardedVideoAdLoaded();
            }

            @Override
            public void onRewardedVideoAdOpened() {
                isIsClickReward = false;
                listener.onRewardedVideoAdOpened();
            }

            @Override
            public void onRewardedVideoStarted() {
                listener.onRewardedVideoStarted();
            }

            @Override
            public void onRewardedVideoAdClosed() {
                listener.onRewardedVideoAdClosed();
                if (rewardedVideoAd != null) {
                    rewardedVideoAd.loadAd(rewar_id_admob, new AdRequest.Builder().build());
                    isLoadingReward = true;
                }
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                listener.onRewarded(rewardItem);
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                listener.onRewardedVideoAdLeftApplication();
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                listener.onRewardedVideoAdFailedToLoad(i);
                if (rewardedVideoAd != null) {
                    rewardedVideoAd.loadAd(rewar_id_admob, new AdRequest.Builder().build());
                    isLoadingReward = true;
                }
            }

            @Override
            public void onRewardedVideoCompleted() {
                listener.onRewardedVideoCompleted();
            }
        });
        if (rewardedVideoAd.isLoaded()) {
            rewardedVideoAd.show();
            return;
        }
        if (!isLoadingReward) {
            rewardedVideoAd.loadAd(rewar_id_admob, new AdRequest.Builder().build());
        }
    }

    public void rewar_fan(com.facebook.ads.RewardedVideoAdListener listener) {
        com.facebook.ads.RewardedVideoAd rewardedVideoAd = new com.facebook.ads.RewardedVideoAd(activity, "YOUR_PLACEMENT_ID");
        rewardedVideoAd.setAdListener(new com.facebook.ads.RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoCompleted() {
                listener.onRewardedVideoCompleted();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                listener.onError(ad, adError);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                rewardedVideoAd.show();
                listener.onAdLoaded(ad);
            }

            @Override
            public void onAdClicked(Ad ad) {
                listener.onAdLoaded(ad);
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                listener.onLoggingImpression(ad);
            }

            @Override
            public void onRewardedVideoClosed() {
                listener.onRewardedVideoClosed();
            }
        });
        rewardedVideoAd.loadAd();
    }

    public void nativeAdmob(final FrameLayout frameLayout, final int layout, final com.google.android.gms.ads.AdListener adListener, String id_inter) {
        UnifiedNativeAd nativeAd;
        AdLoader.Builder builder = new AdLoader.Builder(activity, id_inter).withAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                adListener.onAdClosed();
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                adListener.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                adListener.onAdOpened();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                adListener.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                adListener.onAdImpression();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                adListener.onAdFailedToLoad(errorCode);
//                refresh.setEnabled(true);
                if (frameLayout != null) frameLayout.removeAllViews();
//                frameLayout.setVisibility(View.GONE);
//                Toast.makeText(context, "Failed to load native ad: "
//                        + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adListener.onAdLoaded();
            }
        });

        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
//                FrameLayout frameLayout =
//                        findViewById(idFrrame);
                if (activity.isDestroyed()) {
                    unifiedNativeAd.destroy();
                    return;
                }
                // You must call destroy on old ads when you are done with them,
                // otherwise you will have a memory leak.
                if (nativeAdmob != null) {
                    nativeAdmob.destroy();
                }
                nativeAdmob = unifiedNativeAd;
                UnifiedNativeAdView adView = (UnifiedNativeAdView) activity.getLayoutInflater()
                        .inflate(layout, null);
                populateUnifiedNativeAdView(unifiedNativeAd, adView, layout);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            }

        });
        VideoOptions videoOptions = new VideoOptions.Builder()
//                .setStartMuted(startVideoAdsMuted.isChecked())
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        builder.withNativeAdOptions(adOptions);
        AdLoader adLoader = builder.build();
        adLoader.loadAd(new PublisherAdRequest.Builder().build());
//        videoStatus.setText("");
    }

    public void nativeFan(ViewGroup frameAds, String idNative, int layout, FanNativeCallBack callError) {
        nativeAdFan = new NativeAd(activity, idNative);
        nativeAdFan.setAdListener(new NativeAdListener() {

            @Override
            public void onMediaDownloaded(Ad ad) {
                if (nativeAdFan == null || nativeAdFan != ad) {
                    return;
                }
                inflateAd(activity, nativeAdFan, frameAds, layout);
                callError.onSuccess();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                if (callError != null) {
                    callError.onError();
                }
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (nativeAdFan == null || nativeAdFan != ad) {
                    return;
                }
                inflateAd(activity, nativeAdFan, frameAds, layout);
                callError.onSuccess();
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        nativeAdFan.loadAd(nativeAdFan.buildLoadAdConfig()
                .withMediaCacheFlag(NativeAdBase.MediaCacheFlag.ALL)
                .build());
    }

    private void inflateAd(Context context, NativeAd nativeAd, ViewGroup frmads, int layout) {
        nativeAd.unregisterView();
//        View adView = NativeAdView.render(activity, nativeAd);
//        frmads.removeAllViews();
//        frmads.addView(adView);
        NativeAdLayout nativeAdLayout = new NativeAdLayout(context);
        // Add the Ad view into the ad container.
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        View adView = (LinearLayout) inflater.inflate(layout, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(context, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        AdIconView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);
        nativeAdCallToAction.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    nativeAdCallToAction.setTextColor(0xFFFFFFFF);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    nativeAdCallToAction.setTextColor(0xFFFFFF88);
                }
                return true;
            }
        });
        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        clickableViews.add(nativeAdMedia);
        clickableViews.add(nativeAdIcon);
        clickableViews.add(nativeAdBody);
        // Register the Title and CTA button to listen for clicks.

        nativeAd.registerViewForInteraction(
                adView,
                nativeAdMedia,
                nativeAdIcon,
                clickableViews);
        Log.e(TAG, "inflateAd: " + frmads.getChildCount());
        frmads.removeAllViews();
        frmads.addView(nativeAdLayout);
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
        if (adView.getMediaView() != null && layout != R.layout.ad_unified_draw_navigator_small) {
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

    public interface FanNativeCallBack {
        void onError();

        void onSuccess();
    }

}