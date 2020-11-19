package com.example.init_app_vpn_native.utils.ads;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.ViewGroup;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.Calendar;


public class AdsUtils {
    private static final String TAG = "AdsUtils";
    public static com.google.android.gms.ads.AdView bannerAdmob;
    Context context;
    static long lastRequestInterAdmob = 0, lastRequestNativeAdmob = 0;
    public static UnifiedNativeAd unifiedNativeAds;
    private static AdsUtils adsUtils;
    private long lastRequestBannerFan = 0;
    private long lastRequestInterFan = 0;

    public static AdsUtils getInstance(Context context) {
        if (adsUtils != null) {
            adsUtils.context = context;
        }
        adsUtils = new AdsUtils(context);
        return adsUtils;
    }

    private AdsUtils(Context context) {
        this.context = context;
    }

    public void bannerAdMob(ViewGroup frameAds, String idBanner, com.google.android.gms.ads.AdSize bannerSize) {
        com.google.android.gms.ads.AdView adView = new com.google.android.gms.ads.AdView(context);
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
            com.google.android.gms.ads.InterstitialAd interstitialAd = new com.google.android.gms.ads.InterstitialAd(context);
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
                LocalBroadcastManager.getInstance(context).registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        listener.onAdClosed();
                        LocalBroadcastManager.getInstance(context).unregisterReceiver(this);
                    }
                }, new IntentFilter("CLOSE"));
                AdLoader.Builder builder = new AdLoader.Builder(context, Ads.native_id_admob).withAdListener(new com.google.android.gms.ads.AdListener() {
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
                        Intent intent = new Intent(context, AdsActivity.class);
                        intent.putExtra("type", "native");
                        context.startActivity(intent);
                    }
                });
                AdLoader adLoader = builder.build();
                adLoader.loadAd(new PublisherAdRequest.Builder().build());
            } else {
                LocalBroadcastManager.getInstance(context).registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        listener.onAdClosed();
                        LocalBroadcastManager.getInstance(context).unregisterReceiver(this);
                    }
                }, new IntentFilter("CLOSE"));
                bannerAdmob = new com.google.android.gms.ads.AdView(context);
                bannerAdmob.setAdUnitId(Ads.banner_id_admob);
                bannerAdmob.setAdSize(com.google.android.gms.ads.AdSize.MEDIUM_RECTANGLE);
                bannerAdmob.setAdListener(new com.google.android.gms.ads.AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        Intent intent = new Intent(context, AdsActivity.class);
                        intent.putExtra("type", "banner");
                        context.startActivity(intent);
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
        AdView adView = new AdView(context, idBanner, bannerSize);
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
        InterstitialAd interstitialAd = new InterstitialAd(context, idInter);
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

    public interface FanNativeCallBack {
        void onError();

        void onSuccess();
    }

}