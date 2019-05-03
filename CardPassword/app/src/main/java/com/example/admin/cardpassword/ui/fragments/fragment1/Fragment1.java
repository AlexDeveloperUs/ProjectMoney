package com.example.admin.cardpassword.ui.fragments.fragment1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.cardpassword.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment1 extends Fragment implements View.OnClickListener {

    @BindView(R.id.text_card_number_element_fragment)
    TextView mNumber;
    @BindView(R.id.text_card_cvc_element_fragment)
    TextView mCvc;
    @BindView(R.id.text_card_validity_element_fragment)
    TextView mValidity;
    @BindView(R.id.text_card_holder_name_element_fragment)
    TextView mName;
    @BindView(R.id.text_card_holder_surname_element_fragment)
    TextView mSurname;
    @BindView(R.id.image_element_visa_fragment)
    ImageView mImage;
    @BindView(R.id.text_pin_fragment)
    TextView mPin;

    private int mGetPos;
    private String mGetNum = "";
    private String mGetCvc = "";
    private String mGetValidity = "";
    private String mGetName = "";
    private String mGetSurname = "";
    private String mGetType = "";
    private String mGetPin = "";

    public static Fragment1 newInstance(int pPos, String pNumber, String pCvc, String pValidity, String pName, String pSurname, String pType, String pPin) {

        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putInt("pos", pPos);
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {

            mGetPos = getArguments().getInt("pos", 0);
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment, container, false);
        ButterKnife.bind(this, view);

        view.setOnClickListener(this);
        return view;
    }

    private void setData() {

        float density = Objects.requireNonNull(getActivity()).getResources().getDisplayMetrics().density;

        if (mGetType.toLowerCase().equals("visa")) {

            mImage.setBackgroundResource(R.drawable.visa_rounded);
            mImage.getLayoutParams().height = (int) (density * 50);
            mImage.getLayoutParams().width = (int) (density * 50);
        } else if (mGetType.toLowerCase().equals("mastercard")) {

            mImage.setBackgroundResource(R.drawable.ms_without_border);
            mImage.getLayoutParams().height = (int) (density * 50);
            mImage.getLayoutParams().width = (int) (density * 50);
        }

        mNumber.setText(mGetNum);
        mCvc.setText(mGetCvc);
        mValidity.setText(mGetValidity);
        mName.setText(mGetName);
        mSurname.setText(mGetSurname);
        mPin.setText(mGetPin);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        setData();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {

        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}
