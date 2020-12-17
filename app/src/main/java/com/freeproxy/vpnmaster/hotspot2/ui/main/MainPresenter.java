package com.freeproxy.vpnmaster.hotspot2.ui.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.freeproxy.vpnmaster.hotspot2.base.BasePresenter;
import com.freeproxy.vpnmaster.hotspot2.broadcast.AlarmReceiver;
import com.freeproxy.vpnmaster.hotspot2.data.AppDataHelper;
import com.freeproxy.vpnmaster.hotspot2.data.CallBack;
import com.freeproxy.vpnmaster.hotspot2.utils.Common;

import java.util.Calendar;

public class MainPresenter<V extends IMainActivity> extends BasePresenter<V> implements IMainPresenter<V> {
    AppCompatActivity activity;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    public MainPresenter(AppCompatActivity activity) {
        this.activity = activity;
//        alarmMgr = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(activity, AlarmReceiver.class);
//        intent.setAction("ALARM");
//        alarmIntent = PendingIntent.getBroadcast(activity, 0, intent, 0);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
////        calendar.setTimeInMillis(System.currentTimeMillis()+5000);
//        calendar.set(Calendar.HOUR_OF_DAY, 20);
//
//        boolean alarmUp = PendingIntent.getBroadcast(activity, 0, intent, PendingIntent.FLAG_NO_CREATE) != null;
//        Log.e("TAGMain", "MainPresenter: " + alarmUp);
//        if (!alarmUp)
//            alarmMgr.cancel(alarmIntent);
//        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, alarmIntent);
    }

    @Override
    public void getExample() {
        view.showMessage("message");
    }

    @Override
    public void getPoint(Context mainActivity) {
        AppDataHelper.getInstance().getCoin(mainActivity, new CallBack<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                super.onSuccess(data);
                Common.totalPoint = data;
                if (data == -1)
                    Common.totalPoint = 300;
                view.setPointMain(Common.totalPoint);
            }
        });
    }
}
