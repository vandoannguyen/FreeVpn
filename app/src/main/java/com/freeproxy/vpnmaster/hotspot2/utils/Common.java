package com.freeproxy.vpnmaster.hotspot2.utils;

import java.util.Random;

public class Common {
    public static int totalPoint = 0;
    //public static int points = 0;
    public static int check = 0;
    public static int checktap = 0;
    public static int days = 0;
    public static int minCountryCoin = 100;
    public static int percentInter = 100;

    public static boolean getPercent() {
        return new Random().nextInt(100) < percentInter;
    }
}
