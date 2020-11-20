package com.example.init_app_vpn_native.ui.faq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        List<String> question1 = new ArrayList<>();
        question1.add("Just tap 'GO' button. If it shows 'Connected', it means Oneadx VPN works. Please tick 'I trust this application' the first time you connect");
        List<String> question2 = new ArrayList<String>();
        question2.add("It's 100% free(no money or credit card needed) forever with unlimited data and time, but P2P or BitTorrent is not allowed. Also, Oneadx VPN " +
                "can protect you WIFI hotspot security and privacy. It will never steal any user's information. ");

        List<String> question3 = new ArrayList<String>();
        question3.add("There may be some reasons: ");
        question3.add("(1) If it shows 'Network Unavailable', it means the network is not stable, you should ensure your mobile phone is connected to the internet.");
        question3.add("(2) If it shows 'The server is busy, please try again or try another one', it means current server is busy or blocked in your regon. You can select another server, or try again.");
        question3.add("(3) If it show 'No server available now, please update the APP', you need to update Oneadx VPN to the latest version. ");
        question3.add("(4) If it shows 'Connecting...' for a long time, you should clean up Oneadx VPN's APP data, and restart Oneadx VPN to connect");
        question3.add("(5) If all the above does not work, you can uninstall OneAdx VPN, and re-install it. Usually it will work well. ");

        List<String> question4 = new ArrayList<>();
        question4.add("Please click the national flag at the left bottom of main panel in homepage. Then choose the fastest and most stable server, and " +
                "reconnect. The fastest server is usually in the regon close to the country you are in. If all servers are not stable, please contact us via Facebook, or you can email to oneadx@gmail.com. Our Facebook page is....." +
                "We will reply to you as soon as possible.");

        List<String> question5 = new ArrayList<String>();
        question5.add("Please click the national flag at the left bottom of main panel in homepage. Then choose IP, region or server you prefer and connect.");

        List<String> question6 = new ArrayList<String>();
        question6.add("Please contact us via Facebook, or you can email to oneadx@gmail.com. Our Facebook page is http........... We will reply to you as soon as possible.");

        List<String> question7 = new ArrayList<String>();
        question7.add("Please don't use BitTorrent with VPN, or your account will be blocked. ");

        expandableListDetail.put(" What to if Oneadx VPN doesn't provide the IP, server or resgion you want?", question6);
        expandableListDetail.put(" Is Oneadx VPN connect?", question2);
        expandableListDetail.put(" What to do if the connection is unstable or slow?", question4);
        expandableListDetail.put(" Don't use P2P or BitTorrent!", question7);
        expandableListDetail.put(" Why can't Oneadx VPN connect?", question3);
        expandableListDetail.put(" How to use Oneadx VPN?", question1);
        expandableListDetail.put(" How to select IP server or region you prefer?", question5);
        return expandableListDetail;
    }
}
