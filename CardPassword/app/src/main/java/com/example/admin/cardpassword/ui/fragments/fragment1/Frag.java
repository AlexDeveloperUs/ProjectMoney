package com.example.admin.cardpassword.ui.fragments.fragment1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.ui.activity.list.ListActivity;
import com.example.admin.cardpassword.utils.OnSwipeTouchListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.card_fragment)
    View mCard;
    @BindView(R.id.action_button_delete)
    FloatingActionButton mDelete;
    @BindView(R.id.action_button_edit)
    FloatingActionButton mEdit;

    private String mGetNum = "";
    private String mGetCvc = "";
    private String mGetValidity = "";
    private String mGetName = "";
    private String mGetSurname = "";
    private String mGetType = "";
    private String mGetPin = "";
    private ListActivity mActivity;

    private int[] background = {R.drawable.recangle_rounded_green, R.drawable.rectangle_rounded_indigo, R.drawable.rectangle_rounded_lime,
            R.drawable.rectangle_rounded_purple, R.drawable.rectangle_rounded_red, R.drawable.rectangle_rounded_teal};

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

                toActivity("");
                super.onSwipeRight();
            }

            @Override
            public void onSwipeLeft() {

                toActivity("");
                super.onSwipeLeft();
            }
        });

        mActivity = ((ListActivity) Objects.requireNonNull(getActivity()));

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
        setColors();
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

    @OnClick({R.id.action_button_delete, R.id.action_button_edit})
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.action_button_delete:
                mActivity.deleteCard(mActivity.returnCard());
                break;
            case R.id.action_button_edit:
                mActivity.editCard(mActivity.returnCard());
                break;
        }
    }

    private void setColors() {


        @SuppressLint("Recycle")
        TypedArray array = Objects.requireNonNull(getActivity()).getResources().obtainTypedArray(R.array.some_colors);

        int[] colores = new int[array.length()];
        for (int i = 0; i < array.length(); i++) {

            colores[i] = array.getColor(i, 0);
        }
        array.recycle();

        int pos;

        if (mActivity.getPos() > colores.length) {

            pos = (mActivity.getPos() % colores.length);
        } else {

            pos = mActivity.getPos();
        }

        Drawable back = getResources().getDrawable(background[pos]);
        mCard.setBackground(back);

//        colors[pI % colors.length]

        mDelete.setBackgroundTintList(ColorStateList.valueOf(colores[pos]));
        mEdit.setBackgroundTintList(ColorStateList.valueOf(colores[pos]));
    }
}
