package com.freeproxy.vpnmaster.hotspot2.common;

import com.freeproxy.vpnmaster.hotspot2.data.api.model.Auth;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.Country;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.Server;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.User;

import java.util.List;

public class Config {
    public static String FileUsername;
    public static String FilePassword;
    public static boolean isAppDetails = false;
    public static boolean isConnectionDetails = false;
    public static String tokenApp = "";
    public static User user = null;
    public static Server currentServer;
    public static List<Country> listCountry;
    public static Integer coin;
    public static boolean isFastConnect = true;
    public static Country currentCountry;
    public static boolean isDataLoaded = false;
    static String PREF_USAGE = "daily_usage";
    public static String StringCountDown;
    public static long LongDataUsage;

}