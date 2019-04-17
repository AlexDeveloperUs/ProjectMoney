package com.example.admin.cardpassword.ui.activity.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.ui.activity.list.ListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.edit_text_pass)
    EditText mTextPass;
    @BindView(R.id.btn_cancel)
    Button mButtonCancel;
    @BindView(R.id.btn_skip)
    Button mButtonSkip;

    private static final String SP_NAME = "00";
    private static final String SP_KEY_FIRST_LAUNCH = "00";
    private static final String SP_SKIP = "00";
    private static final String SP_SKIP_CHECK = "00";

    private boolean firstLaunch;
    private boolean skipCheck;

    Intent intent;

    SharedPreferences mPreferences;
    SharedPreferences mSharedPreferences;
    SharedPreferences mSkipCheck;
    String confirmedPassword = "";

    final String SHORT_PASSWORD = "00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);

        firstLaunchCheck();

        mPreferences = getPreferences(MODE_PRIVATE);
    }

    private void firstLaunchCheck() {

        mSharedPreferences = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        mSkipCheck = getSharedPreferences(SP_SKIP, MODE_PRIVATE);
        skipCheck = mSkipCheck.getBoolean(SP_SKIP_CHECK, true);
        firstLaunch = mSharedPreferences.getBoolean(SP_KEY_FIRST_LAUNCH, true);
        if (firstLaunch) {

            mSharedPreferences.edit().putBoolean(SP_KEY_FIRST_LAUNCH, false).apply();
        } else mButtonSkip.setVisibility(View.INVISIBLE);

        if (skipCheck) {

            shortPass();
        } else startListActivity();
    }

    private void shortPass() {

        mTextPass.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String password = s.toString();

                if (firstLaunch) {

                    if (s.length() == 4 && mButtonCancel.getVisibility() == View.INVISIBLE) {

                        confirmedPassword = password;
                        Toast.makeText(AuthActivity.this, "" + password, Toast.LENGTH_SHORT).show();

                        mTextPass.setText("");
                        mButtonSkip.setVisibility(View.INVISIBLE);
                        mButtonCancel.setVisibility(View.VISIBLE);
                        s.clear();
                    } else if (s.length() == 4 && mButtonCancel.getVisibility() == View.VISIBLE) {

                        String str = s.toString();

                        if (confirmedPassword.equals(str)) {

                            SharedPreferences.Editor editor = mPreferences.edit();
                            editor.putString(SHORT_PASSWORD, mTextPass.getText().toString());
                            editor.apply();

                            startListActivity();

                            Toast.makeText(AuthActivity.this, "Confirmed Password", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(AuthActivity.this, "The password are not", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mButtonSkip.setVisibility(View.INVISIBLE);

                    if (s.length() == 4 && password.equals(mPreferences.getString(SHORT_PASSWORD, ""))) {

                        startListActivity();
                    } else if (s.length() == 4 && !password.equals(mPreferences.getString(SHORT_PASSWORD, ""))) {

                        Toast.makeText(AuthActivity.this, "Wrong password", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void startListActivity() {

        intent = new Intent(AuthActivity.this, ListActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.btn_one, R.id.btn_two, R.id.btn_three, R.id.btn_four, R.id.btn_five, R.id.btn_six,
            R.id.btn_seven, R.id.btn_eight, R.id.btn_nine, R.id.btn_zero, R.id.btn_backspace, R.id.btn_skip,
            R.id.btn_cancel})
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_one:
                mTextPass.append("1");
                break;
            case R.id.btn_two:
                mTextPass.append("2");
                break;
            case R.id.btn_three:
                mTextPass.append("3");
                break;
            case R.id.btn_four:
                mTextPass.append("4");
                break;
            case R.id.btn_five:
                mTextPass.append("5");
                break;
            case R.id.btn_six:
                mTextPass.append("6");
                break;
            case R.id.btn_seven:
                mTextPass.append("7");
                break;
            case R.id.btn_eight:
                mTextPass.append("8");
                break;
            case R.id.btn_nine:
                mTextPass.append("9");
                break;
            case R.id.btn_zero:
                mTextPass.append("0");
                break;
            case R.id.btn_backspace:
                String string = mTextPass.getText().toString();
                if (!string.equals("")) {

                    String stringWithoutLastChar = string.substring(0, string.length() - 1);
                    mTextPass.setText(stringWithoutLastChar);
                    mTextPass.setSelection(mTextPass.getText().length());
                }
                break;
            case R.id.btn_skip:
                mSkipCheck.edit().putBoolean(SP_SKIP_CHECK, false).apply();
                startListActivity();
                break;
            case R.id.btn_cancel:
                mTextPass.setText("");
                mButtonSkip.setVisibility(View.VISIBLE);
                mButtonCancel.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
