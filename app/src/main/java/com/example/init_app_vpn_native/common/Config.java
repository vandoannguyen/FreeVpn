package com.example.init_app_vpn_native.common;

import com.example.init_app_vpn_native.data.api.model.Country;
import com.example.init_app_vpn_native.data.api.model.Server;

import java.util.List;

public class Config {
    public static String FileUsername;
    public static String FilePassword;
    public static boolean isAppDetails = false;
    public static boolean isConnectionDetails = false;
    public static String tokenApp = "";
    public static Server currentServer;
    public static List<Country> listCountry;
    public static Integer coin;
    public static boolean isDebug = true;
    public static boolean isFastConnect = true;
    public static Country currentCountry;
    public static boolean isDataLoaded = false;
    static String PREF_USAGE = "daily_usage";
    public static String StringCountDown;
    public static long LongDataUsage;

}