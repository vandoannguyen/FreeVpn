package com.example.init_app_vpn_native.ui.main.fragment.point;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.init_app_vpn_native.R;
import com.example.init_app_vpn_native.ui.enterCode.EnterCodesActivity;
import com.example.init_app_vpn_native.ui.invite.InviteFriendActivity;
import com.example.init_app_vpn_native.ui.main.fragment.FragmentCallback;
import com.example.init_app_vpn_native.utils.Common;
import com.example.init_app_vpn_native.utils.SharedPrefsUtils;
import com.example.init_app_vpn_native.utils.ads.Ads;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PointFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PointFragment extends Fragment {
    @BindView(R.id.linearRandom)
    LinearLayout linearRandom;
    private String TAG = "PointFragment";
    @BindView(R.id.numberPick1)
    NumberPicker numberPicker1;
    @BindView(R.id.numberPick2)
    NumberPicker numberPicker2;
    @BindView(R.id.numberPick3)
    NumberPicker numberPicker3;
    @BindView(R.id.lineTapCoin)
    LinearLayout lineTapCoin;
    @BindView(R.id.lineWatchVideo)
    LinearLayout lineWatchVideo;
    @BindView(R.id.lineCheckin)
    LinearLayout lineCheckin;
    @BindView(R.id.lineInvite)
    LinearLayout lineInvite;
    @BindView(R.id.lineCodes)
    LinearLayout lineCodes;
    @BindView(R.id.txtTapCoin)
    TextView txtTapCoin;
    @BindView(R.id.relTapCoin)
    RelativeLayout relTapCoin;
    @BindView(R.id.txtWatchVideo)
    TextView txtWatchVideo;
    @BindView(R.id.relWatchVideo)
    RelativeLayout relWatchVideo;
    int a1, a2, a3;
    TextView txtCoinDialog, txtCheckin;
    LinearLayout lineGetIt, lineCredits;
    ImageView imgDay1, imgDay2, imgDay3, imgDay4, imgDay5, imgDay6, imgDay7;
    ImageView check1, check2, check3, check4, check5, check6, check7;
    LinearLayout lineGotIt;
    ProgressDialog progressDialog;
    private Handler mHandler = new Handler();
    private int nCounter = 0;
    FragmentCallback callback;
    CountDownTimer Count, CountTwo;
    private boolean isRandomingPoint = false;
    private long lastWatchedVideo = 0;

    public PointFragment() {
        // Required empty public constructor
    }

    public PointFragment(FragmentCallback callback) {
        this.callback = callback;
    }

    // TODO: Rename and change types and number of parameters
    public static PointFragment newInstance(FragmentCallback callback) {
        PointFragment fragment = new PointFragment(callback);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point, container, false);
        ButterKnife.bind(this, view);
        linearRandom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        initView();
        return view;
    }

    private void initView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
//        if (Common.checktap == 1) {
//            CountDown();
//        }
        //numberpicker 1
        numberPicker1.setMaxValue(9);
        numberPicker1.setMinValue(0);
        numberPicker1.setWrapSelectorWheel(true);
        numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Log.e(TAG, "onValueChange: " + numberPicker1.getValue());
            }
        });

        //numberpicker 2
        numberPicker2.setMaxValue(9);
        numberPicker2.setMinValue(0);
        numberPicker2.setWrapSelectorWheel(true);
        numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Log.e(TAG, "onValueChange: " + numberPicker2.getValue());
            }
        });

        //numberpicker 3
        numberPicker3.setMaxValue(9);
        numberPicker3.setMinValue(0);
        numberPicker3.setWrapSelectorWheel(true);
        numberPicker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Log.e(TAG, "onValueChange: " + numberPicker3.getValue());
            }
        });

    }

    //Tap to Get Points
    private void changeValueByOne(final NumberPicker higherPicker, final boolean increment) {
        Method method;
        try {
            method = higherPicker.getClass().getDeclaredMethod("changeValueByOne", boolean.class);
            method.setAccessible(true);
            method.invoke(higherPicker, increment);
        } catch (final NoSuchMethodException e) {
            e.printStackTrace();
        } catch (final IllegalArgumentException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        } catch (final InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void CountDown() {
        Count = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                long str = millisUntilFinished / 1000;
                String TimeFinished = String.valueOf(str);
                txtTapCoin.setText(TimeFinished + "s");
                relTapCoin.setBackgroundColor(getResources().getColor(R.color.text_gray));
            }

            public void onFinish() {
                Common.checktap = 0;
                txtTapCoin.setText("+100~990");
                relTapCoin.setBackgroundColor(getResources().getColor(R.color.colorTapCoin));
                Log.e(TAG, "onFinish: " + Common.checktap);

            }
        };
        Count.start();
    }

    public void CountDownTwo() {
        CountTwo = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                long str = millisUntilFinished / 1000;
                String TimeFinished = String.valueOf(str);
                txtWatchVideo.setText(TimeFinished + "s");
                relWatchVideo.setBackgroundColor(getResources().getColor(R.color.text_gray));
            }

            public void onFinish() {
                txtWatchVideo.setText("+100~990");
                relWatchVideo.setBackgroundColor(getResources().getColor(R.color.colorTapCoin));
            }
        };
        CountTwo.start();
    }

    public void showDialogGetPoint() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_getpoint, null);
        txtCoinDialog = alertLayout.findViewById(R.id.txtCoinDialog);
        lineGetIt = alertLayout.findViewById(R.id.lineGetIt);
        lineCredits = alertLayout.findViewById(R.id.lineCredits);
        String a11 = String.valueOf(a1);
        String a22 = String.valueOf(a2);
        String a33 = String.valueOf(a3);
        String aa = a11 + a22 + a33;
        int istr = Integer.parseInt(aa);
        txtCoinDialog.setText("+" + a11 + a22 + a33 + "  Points");
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertLayout);
        alert.setCancelable(true);
        AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        lineGetIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                Common.totalPoint += istr;
                Common.checktap = 1;
                dialog.dismiss();
                txtTapCoin.setText("...");
                Ads.getInstance(getActivity()).inter(new Ads.CallBackInter() {
                    @Override
                    public void adClose() {
                        callback.setPoint(Common.totalPoint);
                        CountDown();
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "+" + istr + " Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void adLoadFailed(int i) {

                    }
                });
                //initView();
            }
        });
        lineCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                Common.totalPoint += istr + istr;
                Ads.getInstance(getActivity()).rewared(new Ads.CallBackRewared() {
                    @Override
                    public void adClose() {

                    }

                    @Override
                    public void adLoadFailed(int i) {
                        Toast.makeText(getContext(), "Load ads failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void adRewared() {
                        callback.setPoint(Common.totalPoint);
                        CountDown();
                        progressDialog.dismiss();
                        dialog.dismiss();
                        Toast.makeText(getContext(), "+" + istr + istr + " Success", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        dialog.show();
    }

    //Check-in
    public void showDialogCheckin() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_checkin, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertLayout);
        alert.setCancelable(true);
        AlertDialog dialog = alert.create();
        initDialogCheckin(alertLayout, dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void initDialogCheckin(View view, Dialog dialog) {
        imgDay1 = view.findViewById(R.id.imgDay1);
        imgDay2 = view.findViewById(R.id.imgDay2);
        imgDay3 = view.findViewById(R.id.imgDay3);
        imgDay4 = view.findViewById(R.id.imgDay4);
        imgDay5 = view.findViewById(R.id.imgDay5);
        imgDay6 = view.findViewById(R.id.imgDay6);
        imgDay7 = view.findViewById(R.id.imgDay7);
        check1 = view.findViewById(R.id.imgCheck1);
        check2 = view.findViewById(R.id.imgCheck2);
        check3 = view.findViewById(R.id.imgCheck3);
        check4 = view.findViewById(R.id.imgCheck4);
        check5 = view.findViewById(R.id.imgCheck5);
        check6 = view.findViewById(R.id.imgCheck6);
        check7 = view.findViewById(R.id.imgCheck7);
        lineGotIt = view.findViewById(R.id.lineGotIt);
        txtCheckin = view.findViewById(R.id.txtCheckin);
        txtCheckin.setVisibility(View.GONE);
        int checkin = SharedPrefsUtils.getInstance(view.getContext()).getInt("checkin");
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        Log.e(TAG, "initView: " + today.monthDay);
        Common.days = SharedPrefsUtils.getInstance(getActivity()).getInt("days");
        //setText
        if (Common.days == today.monthDay) {
            txtCheckin.setVisibility(View.VISIBLE);
        }
        //day1
        if (checkin == 0) {
            check1.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
        }
        if (checkin == 1) {
            imgDay1.setImageDrawable(getResources().getDrawable(R.drawable.day11));
            check2.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
        }
        //day2
        if (checkin == 2) {
            imgDay1.setImageDrawable(getResources().getDrawable(R.drawable.day11));
            imgDay2.setImageDrawable(getResources().getDrawable(R.drawable.day22));
            check3.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
        }
        //day3
        if (checkin == 3) {
            imgDay1.setImageDrawable(getResources().getDrawable(R.drawable.day11));
            imgDay2.setImageDrawable(getResources().getDrawable(R.drawable.day22));
            imgDay3.setImageDrawable(getResources().getDrawable(R.drawable.day33));
            check4.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
        }
        //day4
        if (checkin == 4) {
            imgDay1.setImageDrawable(getResources().getDrawable(R.drawable.day11));
            imgDay2.setImageDrawable(getResources().getDrawable(R.drawable.day22));
            imgDay3.setImageDrawable(getResources().getDrawable(R.drawable.day33));
            imgDay4.setImageDrawable(getResources().getDrawable(R.drawable.day44));
            check5.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
        }
        //day5
        if (checkin == 5) {
            imgDay1.setImageDrawable(getResources().getDrawable(R.drawable.day11));
            imgDay2.setImageDrawable(getResources().getDrawable(R.drawable.day22));
            imgDay3.setImageDrawable(getResources().getDrawable(R.drawable.day33));
            imgDay4.setImageDrawable(getResources().getDrawable(R.drawable.day44));
            imgDay5.setImageDrawable(getResources().getDrawable(R.drawable.day55));
            check6.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
        }
        //day6
        if (checkin == 6) {
            imgDay1.setImageDrawable(getResources().getDrawable(R.drawable.day11));
            imgDay2.setImageDrawable(getResources().getDrawable(R.drawable.day22));
            imgDay3.setImageDrawable(getResources().getDrawable(R.drawable.day33));
            imgDay4.setImageDrawable(getResources().getDrawable(R.drawable.day44));
            imgDay5.setImageDrawable(getResources().getDrawable(R.drawable.day55));
            imgDay6.setImageDrawable(getResources().getDrawable(R.drawable.day66));
            check7.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
        }
        //day7
        if (checkin == 7) {
            imgDay1.setImageDrawable(getResources().getDrawable(R.drawable.day11));
            imgDay2.setImageDrawable(getResources().getDrawable(R.drawable.day22));
            imgDay3.setImageDrawable(getResources().getDrawable(R.drawable.day33));
            imgDay4.setImageDrawable(getResources().getDrawable(R.drawable.day44));
            imgDay5.setImageDrawable(getResources().getDrawable(R.drawable.day55));
            imgDay6.setImageDrawable(getResources().getDrawable(R.drawable.day66));
            imgDay7.setImageDrawable(getResources().getDrawable(R.drawable.day77));
            check1.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
        }
        //line
        lineGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                if (checkin == 0 || checkin == 7) {
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("days", today.monthDay);
                    //
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("checkin", 1);
                    imgDay1.setImageDrawable(getResources().getDrawable(R.drawable.day11));
                    check1.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                    Ads.getInstance(getActivity()).inter(new Ads.CallBackInter() {
                        @Override
                        public void adClose() {
                            Common.totalPoint += 200;
                            callback.setPoint(Common.totalPoint);
                            Toast.makeText(getActivity(), "+200 Success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void adLoadFailed(int i) {
                            Toast.makeText(getActivity(), "Load ads failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (checkin == 1 && Common.days != today.monthDay) {
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("days", today.monthDay);
                    //
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("checkin", 2);
                    imgDay2.setImageDrawable(getResources().getDrawable(R.drawable.day22));
                    check2.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                    Ads.getInstance(getActivity()).inter(new Ads.CallBackInter() {
                        @Override
                        public void adClose() {
                            Common.totalPoint += 300;
                            callback.setPoint(Common.totalPoint);
                            Toast.makeText(getActivity(), "+300 Success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void adLoadFailed(int i) {
                            Toast.makeText(getActivity(), "Load ads failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (checkin == 2 && Common.days != today.monthDay) {
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("days", today.monthDay);
                    //
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("checkin", 3);
                    imgDay3.setImageDrawable(getResources().getDrawable(R.drawable.day33));
                    check3.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                    Ads.getInstance(getActivity()).inter(new Ads.CallBackInter() {
                        @Override
                        public void adClose() {
                            Common.totalPoint += 400;
                            callback.setPoint(Common.totalPoint);
                            Toast.makeText(getActivity(), "+400 Success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void adLoadFailed(int i) {
                            Toast.makeText(getActivity(), "Load ads failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (checkin == 3 && Common.days != today.monthDay) {
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("days", today.monthDay);
                    //
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("checkin", 4);
                    imgDay4.setImageDrawable(getResources().getDrawable(R.drawable.day44));
                    check4.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                    Ads.getInstance(getActivity()).inter(new Ads.CallBackInter() {
                        @Override
                        public void adClose() {
                            Common.totalPoint += 500;
                            callback.setPoint(Common.totalPoint);
                            Toast.makeText(getActivity(), "+500 Success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void adLoadFailed(int i) {
                            Toast.makeText(getActivity(), "Load ads failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (checkin == 4 && Common.days != today.monthDay) {
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("days", today.monthDay);
                    //
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("checkin", 5);
                    imgDay5.setImageDrawable(getResources().getDrawable(R.drawable.day55));
                    check5.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                    Ads.getInstance(getActivity()).inter(new Ads.CallBackInter() {
                        @Override
                        public void adClose() {
                            Common.totalPoint += 600;
                            callback.setPoint(Common.totalPoint);
                            Toast.makeText(getActivity(), "+600 Success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void adLoadFailed(int i) {
                            Toast.makeText(getActivity(), "Load ads failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (checkin == 5 && Common.days != today.monthDay) {
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("days", today.monthDay);
                    //
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("checkin", 6);
                    imgDay6.setImageDrawable(getResources().getDrawable(R.drawable.day66));
                    check6.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                    Ads.getInstance(getActivity()).inter(new Ads.CallBackInter() {
                        @Override
                        public void adClose() {
                            Common.totalPoint += 900;
                            callback.setPoint(Common.totalPoint);
                            Toast.makeText(getActivity(), "+900 Success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void adLoadFailed(int i) {
                            Toast.makeText(getActivity(), "Load ads failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (checkin == 6 && Common.days != today.monthDay) {
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("days", today.monthDay);
                    //
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("checkin", 7);
                    imgDay7.setImageDrawable(getResources().getDrawable(R.drawable.day77));
                    check7.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                    Ads.getInstance(getActivity()).inter(new Ads.CallBackInter() {
                        @Override
                        public void adClose() {
                            Common.totalPoint += 1000;
                            callback.setPoint(Common.totalPoint);
                            Toast.makeText(getActivity(), "+1000 Success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void adLoadFailed(int i) {
                            Toast.makeText(getActivity(), "Load ads failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                progressDialog.dismiss();
                initView();
                dialog.dismiss();
            }
        });
    }

    @OnClick({R.id.lineTapCoin, R.id.lineWatchVideo, R.id.lineCheckin, R.id.lineInvite, R.id.lineCodes, R.id.numberPick1, R.id.numberPick2, R.id.numberPick3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lineTapCoin:
                if (Common.checktap == 0 && !isRandomingPoint) {
                    isRandomingPoint = true;
                    Random rd = new Random();
                    int ptram = rd.nextInt(9);
                    int ran1 = 0;
                    if (ptram < 7) {
                        ran1 = rd.nextInt(5);
                    } else {
                        ran1 = rd.nextInt(9);
                    }
                    int ran2 = rd.nextInt(9);
                    int ran3 = rd.nextInt(9);
                    Log.e(TAG, "onViewClicked1: " + ran1);
                    Log.e(TAG, "onViewClicked2: " + ran2);
                    Log.e(TAG, "onViewClicked3: " + ran3);
//                    int ard1 = rd.nextInt(30);
//                    int ard2 = rd.nextInt(30);
//                    int ard3 = rd.nextInt(30);
                    IncreaseValue increasePicker1 = new IncreaseValue(numberPicker1, 20 + ran1);
                    increasePicker1.run(1);
                    IncreaseValue increasePicker2 = new IncreaseValue(numberPicker2, 20 + ran2);
                    increasePicker2.run(1);
                    IncreaseValue increasePicker3 = new IncreaseValue(numberPicker3, 20 + ran3);
                    increasePicker3.run(1);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            a1 = numberPicker1.getValue();
                            a2 = numberPicker2.getValue();
                            a3 = numberPicker3.getValue();
                            showDialogGetPoint();
                            isRandomingPoint = false;
                        }
                    }, 5000);
                } else {
                    Toast.makeText(getActivity(), "Please waiting 15 seconds", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.lineWatchVideo:
                long now = Calendar.getInstance().getTimeInMillis();
                if ((now - lastWatchedVideo) > 60000) {
                    progressDialog.show();
                    Random rds = new Random();
                    int rdd = rds.nextInt(9);
                    int randVideo = 0;
                    if (rdd < 7) {
                        randVideo = 500;
                    } else {
                        randVideo = 990;
                    }
                    int rdCoin = rds.nextInt(randVideo - 100) + 100;
                    Ads.getInstance(getActivity()).rewared(new Ads.CallBackRewared() {
                        @Override
                        public void adClose() {
                            CountDownTwo();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void adLoadFailed(int i) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Load ads failed", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void adRewared() {
                            Common.totalPoint += rdCoin;
                            callback.setPoint(Common.totalPoint);
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "+" + rdCoin + " Success", Toast.LENGTH_SHORT).show();
                        }
                    });
                    lastWatchedVideo = now;
                } else {
                    Toast.makeText(getContext(), "Wait for 1 minute", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.lineCheckin:
                showDialogCheckin();
                break;
            case R.id.lineInvite:
                Intent intentInvite = new Intent(getActivity(), InviteFriendActivity.class);
                startActivity(intentInvite);
                break;
            case R.id.lineCodes:
                Intent intentCode = new Intent(getActivity(), EnterCodesActivity.class);
                startActivity(intentCode);
                break;
            case R.id.numberPick1: {
                break;
            }
            case R.id.numberPick2: {
                break;
            }
            case R.id.numberPick3: {
                break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Count != null) {
            Count.cancel();
            Count = null;
        }
        if (CountTwo != null) {
            CountTwo.cancel();
            CountTwo = null;
        }
    }
}