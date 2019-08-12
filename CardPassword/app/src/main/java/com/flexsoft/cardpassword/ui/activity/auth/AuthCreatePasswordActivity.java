package com.flexsoft.cardpassword.ui.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.flexsoft.cardpassword.R;
import com.flexsoft.cardpassword.ui.activity.list.ListActivity;
import com.flexsoft.cardpassword.utils.SharedPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthCreatePasswordActivity extends AppCompatActivity {

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
    ImageView mFourthCircle;
    @BindView(R.id.linear_pass)
    LinearLayout mLayout;
    @BindView(R.id.btn_cancel)
    Button mButtonCancel;
    @BindView(R.id.btn_skip)
    Button mButtonSkip;

    private String mPassword = SharedPrefs.EMPTY_STRING;
    private String confirmedPassword = SharedPrefs.EMPTY_STRING;

    private String firstLaunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_create_password);
        ButterKnife.bind(this);

        firstLaunchCheck();
    }

    private void firstLaunchCheck() {

        firstLaunch = SharedPrefs.getFirstLaunch();

        assert firstLaunch != null;
        if (firstLaunch.equals(SharedPrefs.FIRST)) {

            SharedPrefs.setFirstLaunch(SharedPrefs.NOT_FIRST);
        }
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
                mFourthCircle.setBackgroundResource(R.drawable.void_circle);
                break;
            case 4:
                mFourthCircle.setBackgroundResource(R.drawable.fill_circle);
                createShortPassword(pString);
                break;
        }
    }

    private void clearPassView() {

        clearCircles();
        mPassword = SharedPrefs.EMPTY_STRING;
    }

    private void shakeWrongPass() {

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
        mLayout.startAnimation(animation);
    }

    private void createShortPassword(String pPassword) {

        if (firstLaunch.equals(SharedPrefs.FIRST)) {

            if (pPassword.length() == 4 && mButtonCancel.getVisibility() == View.GONE) {

                confirmedPassword = pPassword;

                setButtonsVisibility(false);

                clearPassView();
            } else if (pPassword.length() == 4 && mButtonCancel.getVisibility() == View.VISIBLE) {

                if (confirmedPassword.equals(pPassword)) {

                    SharedPrefs.setCurrentPassword(confirmedPassword);
                    SharedPrefs.setFirstLaunch(SharedPrefs.NOT_FIRST);
                    startListActivity();
                } else {

                    shakeWrongPass();
                    Toast.makeText(AuthCreatePasswordActivity.this, R.string.password_doesnt_match, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void startListActivity() {

        Intent intent = new Intent(AuthCreatePasswordActivity.this, ListActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.btn_one_check_pass, R.id.btn_two_check_pass, R.id.btn_three_check_pass, R.id.btn_four_check_pass, R.id.btn_five_check_pass, R.id.btn_six_check_pass,
            R.id.btn_seven_check_pass, R.id.btn_eight_check_pass, R.id.btn_nine_check_pass, R.id.btn_zero_check_pass, R.id.btn_backspace_check_pass, R.id.btn_skip,
            R.id.btn_cancel})
    public void onClick(View v) {

        if (mPassword.length() < 4) {
            switch (v.getId()) {

                case R.id.btn_one_check_pass:
                    mPassword = mPassword.concat("1");
                    drawCircle(mPassword);
                    break;
                case R.id.btn_two_check_pass:
                    mPassword = mPassword.concat("2");
                    drawCircle(mPassword);
                    break;
                case R.id.btn_three_check_pass:
                    mPassword = mPassword.concat("3");
                    drawCircle(mPassword);
                    break;
                case R.id.btn_four_check_pass:
                    mPassword = mPassword.concat("4");
                    drawCircle(mPassword);
                    break;
                case R.id.btn_five_check_pass:
                    mPassword = mPassword.concat("5");
                    drawCircle(mPassword);
                    break;
                case R.id.btn_six_check_pass:
                    mPassword = mPassword.concat("6");
                    drawCircle(mPassword);
                    break;
                case R.id.btn_seven_check_pass:
                    mPassword = mPassword.concat("7");
                    drawCircle(mPassword);
                    break;
                case R.id.btn_eight_check_pass:
                    mPassword = mPassword.concat("8");
                    drawCircle(mPassword);
                    break;
                case R.id.btn_nine_check_pass:
                    mPassword = mPassword.concat("9");
                    drawCircle(mPassword);
                    break;
                case R.id.btn_zero_check_pass:
                    mPassword = mPassword.concat("0");
                    drawCircle(mPassword);
                    break;
            }
        }

        switch (v.getId()) {

            case R.id.btn_backspace_check_pass:
                if (!mPassword.equals(SharedPrefs.EMPTY_STRING)) {

                    mPassword = mPassword.substring(0, mPassword.length() - 1);
                    drawCircle(mPassword);
                }
                break;
            case R.id.btn_skip:

                SharedPrefs.setCurrentPassword(SharedPrefs.SKIPPED);
                startListActivity();
                break;
            case R.id.btn_cancel:
                clearPassView();
                setButtonsVisibility(true);
                break;
        }
    }

    void clearCircles() {

        mFirstCircle.setBackgroundResource(R.drawable.void_circle);
        mSecondCircle.setBackgroundResource(R.drawable.void_circle);
        mThirdCircle.setBackgroundResource(R.drawable.void_circle);
        mFourthCircle.setBackgroundResource(R.drawable.void_circle);
    }

    private void setButtonsVisibility(boolean pCheck) {
        mButtonSkip.setVisibility(pCheck ? View.VISIBLE : View.GONE);
        mButtonCancel.setVisibility(pCheck ? View.GONE : View.VISIBLE);
    }
}
