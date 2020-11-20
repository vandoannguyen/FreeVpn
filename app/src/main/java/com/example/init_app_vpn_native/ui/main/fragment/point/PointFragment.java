package com.example.init_app_vpn_native.ui.main.fragment.point;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.init_app_vpn_native.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
<<<<<<< HEAD
import com.example.init_app_vpn_native.ui.enterCode.EnterCodesActivity;
import com.example.init_app_vpn_native.ui.invite.InviteFriendActivity;
=======

import com.example.init_app_vpn_native.ui.main.fragment.point.EnterCodesActivity;
import com.example.init_app_vpn_native.ui.main.fragment.point.InviteFriendActivity;
import com.example.init_app_vpn_native.utils.AdsUtils;
import com.example.init_app_vpn_native.utils.Common;
>>>>>>> fee38a3... push code 16h 1911
import com.example.init_app_vpn_native.utils.SharedPrefsUtils;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PointFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PointFragment extends Fragment {
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
    int a1, a2, a3;
    TextView txtCoinDialog;
    LinearLayout lineGetIt, lineCredits;
    ImageView imgDay1, imgDay2, imgDay3, imgDay4, imgDay5, imgDay6, imgDay7;
    ImageView check1, check2, check3, check4, check5, check6, check7;
    LinearLayout lineGotIt;
    ProgressDialog progressDialog;
    private Handler mHandler = new Handler();
    private int nCounter = 0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PointFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PointFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PointFragment newInstance(String param1, String param2) {
        PointFragment fragment = new PointFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        if (Common.checktap == 1) {
            CountDown();
        }
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
        CountDownTimer Count = new CountDownTimer(15000, 1000) {
            public void onTick(long millisUntilFinished) {
                long str = millisUntilFinished / 1000;
                String TimeFinished = String.valueOf(str);
                txtTapCoin.setText(TimeFinished);
            }

            public void onFinish() {
                txtTapCoin.setText("+100~990");
                Common.checktap = 0;
                relTapCoin.setBackgroundColor(getResources().getColor(R.color.colorTapCoin));
            }
        };
        Count.start();
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
                //SharedPrefsUtils.getInstance(getContext()).putInt("points",istr);
                Common.points = istr;
                Toast.makeText(getContext(), "+" + aa + "Success", Toast.LENGTH_SHORT).show();
                Common.checktap = 1;
                dialog.dismiss();
                txtTapCoin.setText("...");
                relTapCoin.setBackgroundColor(getResources().getColor(R.color.text_gray));
                initView();
            }
        });
        lineCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        int checkin = SharedPrefsUtils.getInstance(view.getContext()).getInt("checkin");
        //day1
        imgDay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkin == 0) {
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("checkin", 1);
                    imgDay1.setImageDrawable(getResources().getDrawable(R.drawable.day11));
                    check1.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                }
            }
        });
        if (checkin == 1) {
            imgDay1.setImageDrawable(getResources().getDrawable(R.drawable.day11));
            check2.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
        }
        //day2
        imgDay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkin == 1) {
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("checkin", 2);
                    imgDay2.setImageDrawable(getResources().getDrawable(R.drawable.day22));
                    check2.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                }
            }
        });
        if (checkin == 2) {
            imgDay1.setImageDrawable(getResources().getDrawable(R.drawable.day11));
            imgDay2.setImageDrawable(getResources().getDrawable(R.drawable.day22));
            check3.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
        }
        //day3
        imgDay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkin == 2) {
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("checkin", 3);
                    imgDay3.setImageDrawable(getResources().getDrawable(R.drawable.day33));
                    check3.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                }
            }
        });
        if (checkin == 3) {
            imgDay1.setImageDrawable(getResources().getDrawable(R.drawable.day11));
            imgDay2.setImageDrawable(getResources().getDrawable(R.drawable.day22));
            imgDay3.setImageDrawable(getResources().getDrawable(R.drawable.day33));
            check4.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
        }
        //day4
        imgDay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkin == 3) {
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("checkin", 4);
                    imgDay4.setImageDrawable(getResources().getDrawable(R.drawable.day44));
                    check4.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                }
            }
        });
        if (checkin == 4) {
            imgDay1.setImageDrawable(getResources().getDrawable(R.drawable.day11));
            imgDay2.setImageDrawable(getResources().getDrawable(R.drawable.day22));
            imgDay3.setImageDrawable(getResources().getDrawable(R.drawable.day33));
            imgDay4.setImageDrawable(getResources().getDrawable(R.drawable.day44));
            check5.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
        }
        //day5
        imgDay5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkin == 4) {
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("checkin", 5);
                    imgDay5.setImageDrawable(getResources().getDrawable(R.drawable.day55));
                    check5.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                }
            }
        });
        if (checkin == 5) {
            imgDay1.setImageDrawable(getResources().getDrawable(R.drawable.day11));
            imgDay2.setImageDrawable(getResources().getDrawable(R.drawable.day22));
            imgDay3.setImageDrawable(getResources().getDrawable(R.drawable.day33));
            imgDay4.setImageDrawable(getResources().getDrawable(R.drawable.day44));
            imgDay5.setImageDrawable(getResources().getDrawable(R.drawable.day55));
            check6.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
        }
        //day6
        imgDay6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkin == 5) {
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("checkin", 6);
                    imgDay6.setImageDrawable(getResources().getDrawable(R.drawable.day66));
                    check6.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                }
            }
        });
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
        imgDay7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkin == 6) {
                    SharedPrefsUtils.getInstance(view.getContext()).putInt("checkin", 7);
                    imgDay7.setImageDrawable(getResources().getDrawable(R.drawable.day77));
                    check7.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                }
            }
        });
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
                if (checkin == 1) {
                    Common.points = 200;
                    Toast.makeText(getActivity(), "+200 Success", Toast.LENGTH_SHORT).show();
                } else if (checkin == 2) {
                    Common.points = 300;
                    Toast.makeText(getActivity(), "+300 Success", Toast.LENGTH_SHORT).show();
                } else if (checkin == 3) {
                    Common.points = 400;
                    Toast.makeText(getActivity(), "+400 Success", Toast.LENGTH_SHORT).show();
                } else if (checkin == 4) {
                    Common.points = 500;
                    Toast.makeText(getActivity(), "+500 Success", Toast.LENGTH_SHORT).show();
                } else if (checkin == 5) {
                    Common.points = 600;
                    Toast.makeText(getActivity(), "+600 Success", Toast.LENGTH_SHORT).show();
                } else if (checkin == 6) {
                    Common.points = 900;
                    Toast.makeText(getActivity(), "+900 Success", Toast.LENGTH_SHORT).show();
                } else if (checkin == 7) {
                    Common.points = 1000;
                    Toast.makeText(getActivity(), "+1000 Success", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }
        });
    }
    @OnClick({R.id.lineTapCoin, R.id.lineWatchVideo, R.id.lineCheckin, R.id.lineInvite, R.id.lineCodes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lineTapCoin:
                if (Common.checktap == 0) {
                    Random rd = new Random();
                    a1 = rd.nextInt(9);
                    a2 = rd.nextInt(9);
                    a3 = rd.nextInt(9);
                    numberPicker1.setValue(a1);
                    numberPicker2.setValue(a2);
                    numberPicker3.setValue(a3);
                    showDialogGetPoint();
                } else {
                    Toast.makeText(getActivity(), "Please waiting 15 seconds", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.lineWatchVideo:
                progressDialog.show();
                AdsUtils.getInstance(getActivity()).rewar_admob(new RewardedVideoAdListener() {
                    @Override
                    public void onRewardedVideoAdLoaded() {
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onRewardedVideoAdOpened() {

                    }

                    @Override
                    public void onRewardedVideoStarted() {

                    }

                    @Override
                    public void onRewardedVideoAdClosed() {
                        Common.points = 200;
                        Toast.makeText(getActivity(), "+200 Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRewarded(RewardItem rewardItem) {

                    }

                    @Override
                    public void onRewardedVideoAdLeftApplication() {

                    }

                    @Override
                    public void onRewardedVideoAdFailedToLoad(int i) {

                    }

                    @Override
                    public void onRewardedVideoCompleted() {

                    }
                });
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
        }
    }


}