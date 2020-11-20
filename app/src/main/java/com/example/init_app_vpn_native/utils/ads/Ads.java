package com.example.init_app_vpn_native.utils.ads;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.example.init_app_vpn_native.R;
import com.example.init_app_vpn_native.common.Config;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import static com.facebook.ads.AdSettings.IntegrationErrorMode.INTEGRATION_ERROR_CRASH_DEBUG_MODE;

public class Ads {
    public static int percents = 50;
    public static boolean is_show_inter = true;
    public static boolean is_show_banner = true;
    public static String native_id_admob = "ca-app-pub-3940256099942544/1044960115";
    Activity activity;
    private static Ads ads;
    public static String banner_id_admob = "ca-app-pub-3940256099942544/6300978111";
    public static String inter_id_admob = "ca-app-pub-3940256099942544/1033173712";
    public static String banner_id_fan = "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID";
    public static String inter_id_fan = "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID";
    public static String native_id_fan = "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID";
    public static boolean is_show_admob = false;
    public static boolean is_load_failed = true;
    private String rewar_id_admob = "ca-app-pub-3940256099942544/5224354917";

    public void initAds() {
        MobileAds.initialize(activity, Config.isDebug ? activity.getString(R.string.app_ads_id_debug) : activity.getString(R.string.app_ads_id_release));
        AudienceNetworkAds.initialize(activity);
        AdSettings.setIntegrationErrorMode(INTEGRATION_ERROR_CRASH_DEBUG_MODE);
    }

    public static Ads getInstance(Activity activity) {
        if (ads != null) {
            ads.activity = activity;
        } else ads = new Ads(activity);
        return ads;
    }

    private Ads(Activity activity) {
        this.activity = activity;
    }

    public void banner(ViewGroup frameAds, AdsSize adsSize) {
        if (is_show_admob) {
            AdsUtils.getInstance(activity).bannerAdMob(frameAds,
                    banner_id_admob,
                    adsSize == AdsSize.BANNER ? AdSize.SMART_BANNER
                            : adsSize == AdsSize.LARGE
                            ? AdSize.LARGE_BANNER
                            : AdSize.MEDIUM_RECTANGLE);
        } else {
            AdsUtils.getInstance(activity).bannerFan(frameAds, banner_id_fan, adsSize == AdsSize.BANNER ? com.facebook.ads.AdSize.BANNER_HEIGHT_50
                    : adsSize == AdsSize.LARGE
                    ? com.facebook.ads.AdSize.BANNER_HEIGHT_90
                    : com.facebook.ads.AdSize.RECTANGLE_HEIGHT_250, new AdsUtils.FanNativeCallBack() {
                @Override
                public void onError() {
                    if (is_load_failed)
                        AdsUtils.getInstance(activity).bannerAdMob(frameAds,
                                banner_id_admob,
                                adsSize == AdsSize.BANNER ? AdSize.SMART_BANNER
                                        : adsSize == AdsSize.LARGE
                                        ? AdSize.LARGE_BANNER
                                        : AdSize.MEDIUM_RECTANGLE);
                    else frameAds.setVisibility(View.GONE);
                }

                @Override
                public void onSuccess() {

                }
            });
        }
    }

    public void banner(ViewGroup frameAds, AdsSize adsSize, boolean is_dmob) {
        if (is_dmob) {
            AdsUtils.getInstance(activity).bannerAdMob(frameAds,
                    banner_id_admob,
                    adsSize == AdsSize.BANNER ? AdSize.SMART_BANNER
                            : adsSize == AdsSize.LARGE
                            ? AdSize.LARGE_BANNER
                            : AdSize.MEDIUM_RECTANGLE);
        } else {
            AdsUtils.getInstance(activity).bannerFan(frameAds, banner_id_fan, adsSize == AdsSize.BANNER ? com.facebook.ads.AdSize.BANNER_HEIGHT_50
                    : adsSize == AdsSize.LARGE
                    ? com.facebook.ads.AdSize.BANNER_HEIGHT_90
                    : com.facebook.ads.AdSize.RECTANGLE_HEIGHT_250, new AdsUtils.FanNativeCallBack() {
                @Override
                public void onError() {
                    if (is_load_failed)
                        AdsUtils.getInstance(activity).bannerAdMob(frameAds,
                                banner_id_admob,
                                adsSize == AdsSize.BANNER ? AdSize.SMART_BANNER
                                        : adsSize == AdsSize.LARGE
                                        ? AdSize.LARGE_BANNER
                                        : AdSize.MEDIUM_RECTANGLE);
                }

                @Override
                public void onSuccess() {

                }
            });
        }
    }

    public void inter(CallBackInter callBackInter) {
        if (is_show_admob) {
            AdsUtils.getInstance(activity).interAdmob(inter_id_admob, new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    callBackInter.adClose();
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    callBackInter.adLoadFailed(i);
                }
            });
        } else {
            AdsUtils.getInstance(activity).interFan(inter_id_fan, new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {

                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    callBackInter.adClose();
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    if (is_load_failed) {
                        AdsUtils.getInstance(activity).interAdmob(inter_id_admob, new AdListener() {
                            @Override
                            public void onAdClosed() {
                                super.onAdClosed();
                                callBackInter.adClose();
                            }

                            @Override
                            public void onAdFailedToLoad(int i) {
                                super.onAdFailedToLoad(i);
                                callBackInter.adLoadFailed(i);
                            }
                        });
                    } else {
                        callBackInter.adLoadFailed(-1);
                    }
                }

                @Override
                public void onAdLoaded(Ad ad) {

                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            });
        }
    }

    public void inter(CallBackInter callBackInter, boolean is_dmob) {
        if (is_dmob) {
            AdsUtils.getInstance(activity).interAdmob(inter_id_admob, new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    callBackInter.adClose();
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    callBackInter.adLoadFailed(i);
                }
            });
        } else {
            AdsUtils.getInstance(activity).interFan(inter_id_fan, new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {

                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    callBackInter.adClose();
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    if (is_load_failed) {
                        AdsUtils.getInstance(activity).interAdmob(inter_id_admob, new AdListener() {
                            @Override
                            public void onAdClosed() {
                                super.onAdClosed();
                                callBackInter.adClose();
                            }

                            @Override
                            public void onAdFailedToLoad(int i) {
                                super.onAdFailedToLoad(i);
                                callBackInter.adLoadFailed(i);
                            }
                        });
                    } else {
                        callBackInter.adLoadFailed(-1);
                    }
                }

                @Override
                public void onAdLoaded(Ad ad) {

                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            });
        }
    }

    public void rewared(CallBackRewared rewared) {
//        if (is_show_admob) {
            AdsUtils.getInstance(activity).rewar_admob(rewar_id_admob, new RewardedVideoAdListener() {

                @Override
                public void onRewardedVideoAdLoaded() {

                }

                @Override
                public void onRewardedVideoAdOpened() {

                }

                @Override
                public void onRewardedVideoStarted() {

                }

                @Override
                public void onRewardedVideoAdClosed() {
                    rewared.adClose();

                }

                @Override
                public void onRewarded(RewardItem rewardItem) {
                    rewared.adRewared();
                }

                @Override
                public void onRewardedVideoAdLeftApplication() {

                }

                @Override
                public void onRewardedVideoAdFailedToLoad(int i) {
                    rewared.adLoadFailed(i);
                }

                @Override
                public void onRewardedVideoCompleted() {

                }
            });
//        } else {
//            AdsUtils.getInstance(activity).rewar_fan(new com.facebook.ads.RewardedVideoAdListener() {
//                @Override
//                public void onRewardedVideoCompleted() {
//                    rewared.adRewared();
//                }
//
//                @Override
//                public void onLoggingImpression(Ad ad) {
//
//                }
//
//                @Override
//                public void onRewardedVideoClosed() {
//                    rewared.adClose();
//                }
//
//                @Override
//                public void onError(Ad ad, AdError adError) {
//                    Log.e("TAG", "onError: " + adError.getErrorMessage());
////                    rewared.adLoadFailed(0);
////                    if (is_load_failed)
////                        AdsUtils.getInstance(activity).rewar_admob(new RewardedVideoAdListener() {
////
////                            @Override
////                            public void onRewardedVideoAdLoaded() {
////                            }
////
////                            @Override
////                            public void onRewardedVideoAdOpened() {
////
////                            }
////
////                            @Override
////                            public void onRewardedVideoStarted() {
////
////                            }
////
////                            @Override
////                            public void onRewardedVideoAdClosed() {
////                                rewared.adClose();
////
////                            }
////
////                            @Override
////                            public void onRewarded(RewardItem rewardItem) {
////                                rewared.adRewared();
////                            }
////
////                            @Override
////                            public void onRewardedVideoAdLeftApplication() {
////
////                            }
////
////                            @Override
////                            public void onRewardedVideoAdFailedToLoad(int i) {
////                                rewared.adLoadFailed(i);
////                            }
////
////                            @Override
////                            public void onRewardedVideoCompleted() {
////
////                            }
////                        });
////                    else {
////                        rewared.adLoadFailed(-1);
////                    }
//
//                }
//
//                @Override
//                public void onAdLoaded(Ad ad) {
//
//                }
//
//                @Override
//                public void onAdClicked(Ad ad) {
//
//                }
//            });
//        }
    }

    public enum AdsSize {
        BANNER,
        MEDIUM,
        LARGE
    }
    public interface CallBackRewared {

        void adClose();

        void adLoadFailed(int i);

        void adRewared();
    }
    public interface CallBackInter {

        void adClose();

        void adLoadFailed(int i);
    }
}
