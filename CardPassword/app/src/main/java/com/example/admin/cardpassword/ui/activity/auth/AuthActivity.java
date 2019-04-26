package com.example.admin.cardpassword.ui.activity.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.ui.activity.list.ListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

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

    SharedPreferences sharedPreferences;
     String mPassword = "";

    private static final String SP_NAME = "111111";
    private static final String SP_KEY_FIRST_LAUNCH = "111111";
    private static final String SP_SKIP = "111111";
    private static final String SP_SKIP_CHECK = "111111";

    private boolean firstLaunch;
//    private boolean skipCheck = true;

    private Intent intent;

    private SharedPreferences mPreferences;
    //    private SharedPreferences mSkipCheck;
    String confirmedPassword = "";
    SharedPreferences.Editor editor;

    final String SHORT_PASSWORD = "111111";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);

        firstLaunchCheck();

        mPreferences = getPreferences(MODE_PRIVATE);

    }

    private void firstLaunchCheck() {

        SharedPreferences sharedPreferences = getSharedPreferences(SP_NAME, MODE_PRIVATE);
//        mSkipCheck = getSharedPreferences(SP_SKIP, MODE_PRIVATE);
//        skipCheck = mSkipCheck.getBoolean(SP_SKIP_CHECK, true);
//        firstLaunch = sharedPreferences.getBoolean(SP_KEY_FIRST_LAUNCH, true);
//        if (firstLaunch) {
//
//            sharedPreferences.edit().putBoolean(SP_KEY_FIRST_LAUNCH, false).apply();
//        } else mButtonSkip.setVisibility(View.INVISIBLE);
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

        if (pPassword.length() == 4 && mButtonCancel.getVisibility() == View.INVISIBLE) {

            confirmedPassword = pPassword;
            Toast.makeText(AuthActivity.this, "" + pPassword, Toast.LENGTH_SHORT).show();

            mButtonSkip.setVisibility(View.INVISIBLE);
            mButtonCancel.setVisibility(View.VISIBLE);
            clearPassView();
        } else if (pPassword.length() == 4 && mButtonCancel.getVisibility() == View.VISIBLE) {

            if (confirmedPassword.equals(pPassword)) {

                sharedPreferences = getSharedPreferences("PAS", 0);
                editor = sharedPreferences.edit();
                editor.putString("pas", confirmedPassword);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
                startListActivity();

                Toast.makeText(AuthActivity.this, "Confirmed Password", Toast.LENGTH_SHORT).show();
            } else {

                shakeWrongPass();
                Toast.makeText(AuthActivity.this, "The password are not", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    private void shortPass() {
//
//        mTextPass.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                String password = s.toString();
//
//                if (firstLaunch) {
//
//                    if (s.length() == 4 && mButtonCancel.getVisibility() == View.INVISIBLE) {
//
//                        confirmedPassword = password;
//                        Toast.makeText(AuthActivity.this, "" + password, Toast.LENGTH_SHORT).show();
//
//                        mTextPass.setText("");
//                        mButtonSkip.setVisibility(View.INVISIBLE);
//                        mButtonCancel.setVisibility(View.VISIBLE);
//                        s.clear();
//                    } else if (s.length() == 4 && mButtonCancel.getVisibility() == View.VISIBLE) {
//
//                        String str = s.toString();
//
//                        if (confirmedPassword.equals(str)) {
//
//                            SharedPreferences.Editor editor = mPreferences.edit();
//                            editor.putString(SHORT_PASSWORD, mTextPass.getText().toString());
//                            editor.apply();
//
//                            mSkipCheck.edit().putBoolean(SP_SKIP_CHECK, true).apply();
//                            startListActivity();
//
//                            Toast.makeText(AuthActivity.this, "Confirmed Password", Toast.LENGTH_SHORT).show();
//                        } else
//                            Toast.makeText(AuthActivity.this, "The password are not", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    mButtonSkip.setVisibility(View.INVISIBLE);
//
//                    if (s.length() == 4 && password.equals(mPreferences.getString(SHORT_PASSWORD, ""))) {
//
//                        startListActivity();
//                    } else if (s.length() == 4 && !password.equals(mPreferences.getString(SHORT_PASSWORD, ""))) {
//
//                        Toast.makeText(AuthActivity.this, "Wrong password", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        });
//    }

    private void startListActivity() {

        intent = new Intent(AuthActivity.this, ListActivity.class);
        startActivity(intent);
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
