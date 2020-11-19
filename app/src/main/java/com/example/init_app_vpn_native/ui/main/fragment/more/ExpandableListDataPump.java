package com.example.init_app_vpn_native.ui.main.fragment.more;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> question1 = new ArrayList<>();
        question1.add("Just tap 'GO' button. If it shows 'Connected', it means Oneadx VPN works. Please tick 'I trust this application the first time you connect'");

        List<String> question2 = new ArrayList<String>();
        question2.add("It's 100% free(no money or credit card needed) forever with unlimited data and time, but P2P or BitTorrent is not allowed.");

        List<String> question3 = new ArrayList<String>();
        question3.add("What are you doing....");


        List<String> question4 = new ArrayList<>();
        question4.add("India");

        List<String> question5 = new ArrayList<String>();
        question5.add("Brazil");

        List<String> question6 = new ArrayList<String>();
        question6.add("United States");

        List<String> question7 = new ArrayList<String>();
        question7.add("United States");

        expandableListDetail.put("Question 1: How to use Oneadx VPN?", question1);
        expandableListDetail.put("Question 2: Is Oneadx VPN connect?", question2);
        expandableListDetail.put("Question 3: Why can't Oneadx VPN connect?", question3);
        expandableListDetail.put("Question 4: What to do if the connection is unstable or slow?", question4);
        expandableListDetail.put("Question 5: How to select IP server or region you prefer?", question5);
        expandableListDetail.put("Question 6: What to if Oneadx VPN doesn't provide the IP, server or resgion you want?", question6);
        expandableListDetail.put("Question 7: Don't use P2P or BitTorrent!", question7);
        return expandableListDetail;
    }
}
