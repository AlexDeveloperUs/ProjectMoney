package com.example.admin.cardpassword.ui.fragments.fragment1;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.ui.activity.list.ListActivity;
import com.example.admin.cardpassword.utils.OnSwipeTouchListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Frag extends Fragment implements View.OnClickListener{

    @BindView(R.id.text_card_number_card_fragment)
    TextView mNumber;
    @BindView(R.id.text_card_cvc_card_fragment)
    TextView mCvc;
    @BindView(R.id.text_card_validity_card_fragment)
    TextView mValidity;
    @BindView(R.id.text_card_holder_name_card_fragment)
    TextView mName;
    @BindView(R.id.text_card_holder_surname_element)
    TextView mSurname;
    @BindView(R.id.image_card_fragment)
    ImageView mImage;
    @BindView(R.id.text_pin_card_fragment)
    TextView mPin;

    ListActivity mListActivity;

    private String mGetNum = "";
    private String mGetCvc = "";
    private String mGetValidity = "";
    private String mGetName = "";
    private String mGetSurname = "";
    private String mGetType = "";
    private String mGetPin = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {

            mGetNum = getArguments().getString("num", "");
            mGetCvc = getArguments().getString("cvc", "");
            mGetValidity = getArguments().getString("val", "");
            mGetName = getArguments().getString("name", "");
            mGetSurname = getArguments().getString("surname", "");
            mGetType = getArguments().getString("type", "");
            mGetPin = getArguments().getString("pin", "");
        }
        super.onCreate(savedInstanceState);
    }

    public static Frag newInstance(String pNumber, String pCvc, String pValidity, String pName, String pSurname, String pType, String pPin) {

        Frag fragment = new Frag();
        Bundle args = new Bundle();
        args.putString("num", pNumber);
        args.putString("cvc", pCvc);
        args.putString("val", pValidity);
        args.putString("name", pName);
        args.putString("surname", pSurname);
        args.putString("type", pType);
        args.putString("pin", pPin);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_card, container, false);
        ButterKnife.bind(this, view);

        view.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {

            @Override
            public void onSwipeRight() {

                Toast.makeText(getActivity(), "lol", Toast.LENGTH_LONG).show();
                ListActivity.check = true;

                toActivity("");
                super.onSwipeRight();
            }

            @Override
            public void onSwipeLeft() {

                toActivity("");
                super.onSwipeLeft();
            }
        });

        view.setOnClickListener(this);
        return view;
    }

    public void toActivity(String data) {
        Activity activity = getActivity();
        if (activity != null && !activity.isFinishing() && activity instanceof ListActivity) {
            ((ListActivity) activity).fromFragmentData(data);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    private void setData() {

        if (mGetType.toLowerCase().equals("visa")) {

            mImage.setBackgroundResource(R.drawable.visa_rounded);
            setParams();
        } else if (mGetType.toLowerCase().equals("mastercard")) {

            mImage.setBackgroundResource(R.drawable.ms_without_border);
            setParams();
        } else if (mGetType.toLowerCase().equals("belcard")) {

            mImage.setBackgroundResource(R.drawable.belcard);
            setParams();
        } else if (mGetType.toLowerCase().equals("maestro")) {

            mImage.setBackgroundResource(R.drawable.maestro);
            setParams();
        }

        if (mGetName.equals("") && mGetSurname.equals("")) {

            mName.setText(R.string.text_void_et_name);
            mSurname.setText(R.string.text_void_et_surname);
        } else {

            mName.setText(mGetName);
            mSurname.setText(mGetSurname);
        }

        mNumber.setText(appendMinus(mGetNum));
        mCvc.setText(mGetCvc);
        mValidity.setText(mGetValidity);
        mPin.setText(mGetPin);
    }

    private void setParams() {

        float density = Objects.requireNonNull(getActivity()).getResources().getDisplayMetrics().density;

        mImage.getLayoutParams().height = (int) (density * 50);
        mImage.getLayoutParams().width = (int) (density * 50);
    }

    private String appendMinus(String pS) {

        String firstSubString = pS.substring(0, 4);
        String secondSubString = pS.substring(4, 8);
        String thirdSubString = pS.substring(8, 12);
        String fourthSubString = pS.substring(12, 16);

        return firstSubString + "-" + secondSubString + "-" + thirdSubString + "-" + fourthSubString;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        setData();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {


    }
}
