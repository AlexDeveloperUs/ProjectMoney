package com.example.admin.cardpassword.ui.activity.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.ui.activity.list.ListActivity;
import com.example.admin.cardpassword.utils.SharedPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthCreatePasswordActivity extends AppCompatActivity implements View.OnClickListener {

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

    private String mPassword = "";
    private String confirmedPassword = "";

    private String firstLaunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_create_password);
        ButterKnife.bind(this);

        firstLaunchCheck();
    }

    private void firstLaunchCheck() {

        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefs.SHARED_PREFERENCES_FIRST_LAUNCH_NAME, MODE_PRIVATE);
        firstLaunch = sharedPreferences.getString(SharedPrefs.SHARED_PREFERENCES_FIRST_LAUNCH_KEY, "first");
        assert firstLaunch != null;
        if (firstLaunch.equals("first")) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(SharedPrefs.SHARED_PREFERENCES_FIRST_LAUNCH_KEY, "notFirst");
            editor.apply();
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

        mFirstCircle.setBackgroundResource(R.drawable.void_circle);
        mSecondCircle.setBackgroundResource(R.drawable.void_circle);
        mThirdCircle.setBackgroundResource(R.drawable.void_circle);
        mFourthCircle.setBackgroundResource(R.drawable.void_circle);
        mPassword = "";
    }

    private void shakeWrongPass() {

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
        mLayout.startAnimation(animation);
    }

    private void createShortPassword(String pPassword) {

        if (firstLaunch.equals("first")) {

            if (pPassword.length() == 4 && mButtonCancel.getVisibility() == View.INVISIBLE) {

                confirmedPassword = pPassword;
                Toast.makeText(AuthCreatePasswordActivity.this, "" + pPassword, Toast.LENGTH_SHORT).show();

                mButtonSkip.setVisibility(View.INVISIBLE);
                mButtonCancel.setVisibility(View.VISIBLE);
                clearPassView();
            } else if (pPassword.length() == 4 && mButtonCancel.getVisibility() == View.VISIBLE) {

                if (confirmedPassword.equals(pPassword)) {

                    SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefs.SHARED_PREFERENCES_NAME, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(SharedPrefs.SHARED_PREFERENCES_KEY, confirmedPassword);
                    editor.apply();

                    SharedPreferences.Editor editable = sharedPreferences.edit();
                    editable.putString(SharedPrefs.SHARED_PREFERENCES_FIRST_LAUNCH_KEY, "notFirst");
                    editable.apply();

                    startListActivity();

                    Toast.makeText(AuthCreatePasswordActivity.this, "Confirmed Password", Toast.LENGTH_SHORT).show();
                } else {

                    shakeWrongPass();
                    Toast.makeText(AuthCreatePasswordActivity.this, "The password are not", Toast.LENGTH_SHORT).show();
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
            case R.id.btn_backspace_check_pass:
                if (!mPassword.equals("")) {

                    mPassword = mPassword.substring(0, mPassword.length() - 1);
                    drawCircle(mPassword);
                }
                break;
            case R.id.btn_skip:
                SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefs.SHARED_PREFERENCES_NAME, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SharedPrefs.SHARED_PREFERENCES_KEY, "skipped");
                editor.apply();
                startListActivity();
                break;
            case R.id.btn_cancel:
                mPassword = "";
                mButtonSkip.setVisibility(View.VISIBLE);
                mButtonCancel.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
