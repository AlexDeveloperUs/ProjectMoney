package com.flexsoft.cardpassword.ui.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flexsoft.cardpassword.R;
import com.flexsoft.cardpassword.ui.activity.list.ListActivity;
import com.flexsoft.cardpassword.utils.SharedPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthCheckPasswordActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @BindView(R.id.first_void_circle_create)
    ImageView mFirstCircle;
    @BindView(R.id.second_void_circle_create)
    ImageView mSecondCircle;
    @BindView(R.id.third_void_circle_create)
    ImageView mThirdCircle;
    @BindView(R.id.fourth_void_circle_create)
    ImageView mForthCircle;
    @BindView(R.id.linear_pass_check)
    LinearLayout mLayout;
    @BindView(R.id.text_confirm)
    TextView mTextView;

    private String mCheck = SharedPrefs.EMPTY_STRING;
    private String mPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_check_password);
        ButterKnife.bind(this);

        mPassword = SharedPrefs.getCurrentPassword();
    }

    @OnClick({R.id.btn_one_check_pass, R.id.btn_two_check_pass, R.id.btn_three_check_pass, R.id.btn_four_check_pass,
            R.id.btn_five_check_pass, R.id.btn_six_check_pass, R.id.btn_seven_check_pass, R.id.btn_eight_check_pass,
            R.id.btn_nine_check_pass, R.id.btn_zero_check_pass, R.id.btn_backspace_check_pass})
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_one_check_pass:
                mCheck = mCheck.concat("1");
                drawCircle(mCheck);
                break;
            case R.id.btn_two_check_pass:
                mCheck = mCheck.concat("2");
                drawCircle(mCheck);
                break;
            case R.id.btn_three_check_pass:
                mCheck = mCheck.concat("3");
                drawCircle(mCheck);
                break;
            case R.id.btn_four_check_pass:
                mCheck = mCheck.concat("4");
                drawCircle(mCheck);
                break;
            case R.id.btn_five_check_pass:
                mCheck = mCheck.concat("5");
                drawCircle(mCheck);
                break;
            case R.id.btn_six_check_pass:
                mCheck = mCheck.concat("6");
                drawCircle(mCheck);
                break;
            case R.id.btn_seven_check_pass:
                mCheck = mCheck.concat("7");
                drawCircle(mCheck);
                break;
            case R.id.btn_eight_check_pass:
                mCheck = mCheck.concat("8");
                drawCircle(mCheck);
                break;
            case R.id.btn_nine_check_pass:
                mCheck = mCheck.concat("9");
                drawCircle(mCheck);
                break;
            case R.id.btn_zero_check_pass:
                mCheck = mCheck.concat("0");
                drawCircle(mCheck);
                break;
            case R.id.btn_backspace_check_pass:
                if (!mCheck.equals(SharedPrefs.EMPTY_STRING)) {

                    mCheck = mCheck.substring(0, mCheck.length() - 1);
                    drawCircle(mCheck);
                }
                break;
        }
    }

    private void shakeWrongPass() {

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
        mLayout.startAnimation(animation);
    }

    private void drawCircle(String pString) {

        switch (pString.length()) {

            case 0:
                mFirstCircle.setBackgroundResource(R.drawable.void_circle);
                break;
            case 1:
                mFirstCircle.setBackgroundResource(R.drawable.fill_circle);
                mSecondCircle.setBackgroundResource(R.drawable.void_circle);
                break;
            case 2:
                mSecondCircle.setBackgroundResource(R.drawable.fill_circle);
                mThirdCircle.setBackgroundResource(R.drawable.void_circle);
                break;
            case 3:
                mThirdCircle.setBackgroundResource(R.drawable.fill_circle);
                mForthCircle.setBackgroundResource(R.drawable.void_circle);
                break;
            case 4:
                mForthCircle.setBackgroundResource(R.drawable.fill_circle);
                checkIntent();
                break;
        }
    }

    private void checkIntent() {

        Intent intent = getIntent();
        String currentPassword = intent.getStringExtra(SharedPrefs.CURRENT_PASSWORD);
        String pas = currentPassword == null ? SharedPrefs.EMPTY_STRING : currentPassword;

        if (pas.equals(SharedPrefs.EMPTY_STRING)) {

            mTextView.setVisibility(View.VISIBLE);

            if (mCheck.equals(mPassword)) {

                intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
                finish();
            } else shakeWrongPass();
        } else {

            if (currentPassword.equals(mCheck)) {

                SharedPrefs.setFirstLaunch(SharedPrefs.FIRST);

                intent = new Intent(getApplicationContext(), AuthCreatePasswordActivity.class);
                startActivity(intent);
            }
        }
    }
}
