package com.example.init_app_vpn_native.ui.main.fragment.point;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.init_app_vpn_native.R;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private void initView(){
        numberPicker1.setMaxValue(100);
        numberPicker1.setMinValue(0);
        numberPicker1.setWrapSelectorWheel(true);
        numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Log.e(TAG, "onValueChange: " + numberPicker1.getValue() );
            }
        });
//        numberPicker1.setTextColor(getResources().getColor(R.color.colorWhite));
//        numberPicker1.setTextSize(20);
        numberPicker1.
        ///
        numberPicker2.setMaxValue(100);
        numberPicker2.setMinValue(0);
        numberPicker2.setWrapSelectorWheel(true);
        numberPicker3.setMaxValue(100);
        numberPicker3.setMinValue(0);
        numberPicker3.setWrapSelectorWheel(true);
    }
    public void setNumberPickerTextColor(NumberPicker numberPicker, int color){
        EditText et = ((EditText) numberPicker.getChildAt(0));
        et.setTextColor(getResources().getColor(color));
    }
}