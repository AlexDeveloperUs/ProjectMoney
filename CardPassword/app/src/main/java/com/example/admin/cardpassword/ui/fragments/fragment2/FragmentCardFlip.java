package com.example.admin.cardpassword.ui.fragments.fragment2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.ui.activity.list.ListActivity;
import com.example.admin.cardpassword.utils.OnSwipeTouchListener;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentCardFlip extends Fragment implements View.OnClickListener {

    @BindView(R.id.text_card_name_front_view)
    TextView mFrontCardName;
    @BindView(R.id.text_card_number_front_view)
    TextView mFrontCardNumber;
    @BindView(R.id.text_expired_date_front_view)
    TextView mFrontValidity;
    @BindView(R.id.text_card_holder_front_view)
    TextView mFrontCardHolder;
    @BindView(R.id.text_pin_front_view)
    TextView mFrontPin;
    @BindView(R.id.text_cvv_code_front_view)
    TextView mFrontCvc;
    @BindView(R.id.image_card_element_back_view)
    ImageView mBackImage;
    @BindView(R.id.text_card_name_back_view)
    TextView mBackCardName;
    @BindView(R.id.text_card_number_back_view)
    TextView mBackCardNumber;
    @BindView(R.id.text_expired_date_back_view)
    TextView mBackValidity;
    @BindView(R.id.text_card_holder_back_view)
    TextView mBackCardHolder;
    @BindView(R.id.text_pin_back_view)
    TextView mBackPin;
    @BindView(R.id.text_cvv_code_back_view)
    TextView mBackCvc;
    @BindView(R.id.image_card_element_front_view)
    ImageView mFrontImage;
    @BindView(R.id.action_button_delete_fragment)
    ImageView mDelete;
    @BindView(R.id.action_button_edit_fragment)
    ImageView mEdit;
    @BindView(R.id.flip_view)
    EasyFlipView mFlipView;
    @BindView(R.id.card_constraint_front_view)
    ConstraintLayout mBack;

    private String mGetName = "";
    private String mGetNum = "";
    private String mGetCvc = "";
    private String mGetValidity = "";
    private String mGetCardHolder = "";
    private String mGetType = "";
    private String mGetPin = "";
    private ListActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {

            mGetName = getArguments().getString("name", "");
            mGetNum = getArguments().getString("num", "");
            mGetCvc = getArguments().getString("cvc", "");
            mGetValidity = getArguments().getString("val", "");
            mGetCardHolder = getArguments().getString("cardholder", "");
            mGetType = getArguments().getString("type", "");
            mGetPin = getArguments().getString("pin", "");
        }
        super.onCreate(savedInstanceState);
    }

    public static FragmentCardFlip newInstance(String pName, String pNumber, String pCvc, String pValidity, String pCardholder, String pType, String pPin) {

        FragmentCardFlip fragment = new FragmentCardFlip();
        Bundle args = new Bundle();
        args.putString("name", pName);
        args.putString("num", pNumber);
        args.putString("cvc", pCvc);
        args.putString("val", pValidity);
        args.putString("cardholder", pCardholder);
        args.putString("type", pType);
        args.putString("pin", pPin);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_card_flip, container, false);
        ButterKnife.bind(this, view);

        view.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {

            @Override
            public void onSwipeRight() {

                toActivity();
                super.onSwipeRight();
            }

            @Override
            public void onSwipeLeft() {

                toActivity();
                super.onSwipeLeft();
            }
        });

        mActivity = ((ListActivity) Objects.requireNonNull(getActivity()));

        view.setOnClickListener(this);
        return view;
    }

    private void toActivity() {

        Activity activity = getActivity();
        if (activity != null && !activity.isFinishing() && activity instanceof ListActivity) {

            ((ListActivity) activity).fromFragmentData("");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    private void setData() {

        Drawable back;

        if (mGetName.equals("")) {

            mFrontCardName.setText(R.string.label_card_name);
            mBackCardName.setText(R.string.label_card_name);
        } else {

            mFrontCardName.setText(mGetName);
            mBackCardName.setText(mGetName);
        }

        if (mGetType.toLowerCase().equals("visa")) {

            mFrontImage.setBackgroundResource(R.drawable.ic_visa);
            mBackImage.setBackgroundResource(R.drawable.ic_visa);
            setParams();
            back = getResources().getDrawable(R.drawable.visa_gradient);
            mBack.setBackground(back);
        } else if (mGetType.toLowerCase().equals("mastercard")) {

            mFrontImage.setBackgroundResource(R.drawable.ic_mastercard);
            mBackImage.setBackgroundResource(R.drawable.ic_mastercard);
            setParams();
            back = getResources().getDrawable(R.drawable.red_gradient);
            mBack.setBackground(back);
        } else if (mGetType.toLowerCase().equals("belcard")) {

            mFrontImage.setBackgroundResource(R.drawable.belcard);
            mBackImage.setBackgroundResource(R.drawable.belcard);
            setParams();
            back = getResources().getDrawable(R.drawable.belcard_gradient);
            mBack.setBackground(back);
        } else if (mGetType.toLowerCase().equals("maestro")) {

            mFrontImage.setBackgroundResource(R.drawable.ic_maestro);
            mBackImage.setBackgroundResource(R.drawable.ic_maestro);
            setParams();
            back = getResources().getDrawable(R.drawable.blue_gradient);
            mBack.setBackground(back);
        }

        if (mGetCardHolder.equals("")) {

            mBackCardHolder.setText(R.string.hint_card_holder);
            mFrontCardHolder.setText(R.string.hint_card_holder);
        } else {

            mBackCardHolder.setText(mGetCardHolder);
            mFrontCardHolder.setText(mGetCardHolder);
        }

        mBackCardNumber.setText(appendVoid(mGetNum));
        String hiddenCardNumber = appendVoid(mGetNum).substring(0, 15);
        mFrontCardNumber.setText(hiddenCardNumber + "****");

        mBackCvc.setText(mGetCvc + " ");
        if (mGetCvc.length() == 3) {

            mFrontCvc.setText("*** ");
        } else if (mGetCvc.length() == 4) {

            mFrontCvc.setText("**** ");
        }
        mBackValidity.setText(mGetValidity);
        mFrontValidity.setText(mGetValidity);
        mBackPin.setText(mGetPin);
        mFrontPin.setText("****");
    }

    private void setParams() {

        float density = Objects.requireNonNull(getActivity()).getResources().getDisplayMetrics().density;

        mFrontImage.getLayoutParams().height = (int) (density * 50);
        mFrontImage.getLayoutParams().width = (int) (density * 50);
        mBackImage.getLayoutParams().height = (int) (density * 50);
        mBackImage.getLayoutParams().width = (int) (density * 50);
    }

    private String appendVoid(String pS) {

        String firstSubString = pS.substring(0, 4);
        String secondSubString = pS.substring(4, 8);
        String thirdSubString = pS.substring(8, 12);
        String fourthSubString = pS.substring(12, 16);

        return firstSubString + " " + secondSubString + " " + thirdSubString + " " + fourthSubString;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        setData();
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.image_flip_card, R.id.image_flip_card_back, R.id.action_button_edit_fragment, R.id.action_button_delete_fragment,
            R.id.action_button_back_fragment})
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.action_button_delete_fragment:
                mActivity.deleteCard(mActivity.returnCard());
                break;
            case R.id.action_button_edit_fragment:
                mActivity.editCard(mActivity.returnCard());
                break;
            case R.id.image_flip_card:
                mFlipView.setToHorizontalType();
                mFlipView.setFlipTypeFromBack();
                mFlipView.flipTheView();
                break;
            case R.id.image_flip_card_back:
                mFlipView.setToHorizontalType();
                mFlipView.setFlipTypeFromBack();
                mFlipView.flipTheView();
                break;
            case R.id.action_button_back_fragment:
                toActivity();
                break;
        }
    }
}
