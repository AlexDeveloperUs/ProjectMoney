package com.flexsoft.cardpassword.ui.activity.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;

import com.flexsoft.cardpassword.R;
import com.flexsoft.cardpassword.ui.activity.auth.AuthCheckPasswordActivity;
import com.flexsoft.cardpassword.ui.activity.auth.AuthCreatePasswordActivity;
import com.flexsoft.cardpassword.ui.activity.list.ListActivity;
import com.flexsoft.cardpassword.ui.activity.submit.SubmitCardActivity;
import com.flexsoft.cardpassword.utils.SharedPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    private static final int KEY_LIST_ACTIVITY = 1;
    private static final int KEY_CREATE_ACTIVITY = 2;
    private Class mClass;
    private Context mContext = SettingsActivity.this;

    @BindView(R.id.frame_usage_pass)
    FrameLayout mLayout;
    @BindView(R.id.checkbox)
    CheckBox mCheckBox;
    @BindView(R.id.button_change_pass)
    AppCompatButton mButton;

    private String mPassIsCreated;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        checkIntent();

        mPassIsCreated = SharedPrefs.getCurrentPassword();
        checkPasswordExistence();
    }

    private void checkIntent() {

        Intent intent = getIntent();
        int check = intent.getIntExtra("key", 0);

        if (check == KEY_LIST_ACTIVITY) {

            mClass = ListActivity.class;
        } else if (check == KEY_CREATE_ACTIVITY) {

            mClass = SubmitCardActivity.class;
        }
    }

    @OnClick({R.id.fab_settings, R.id.button_change_pass, R.id.frame_usage_pass})
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {

            case R.id.fab_settings:
                intent = new Intent(mContext, mClass);
                startActivity(intent);
                finish();
                break;
            case R.id.frame_usage_pass:
                mCheckBox.setChecked(!mCheckBox.isChecked());
                if (!mCheckBox.isChecked()) {

                    SharedPrefs.setCurrentPassword(SharedPrefs.DISABLED);
                } else {

                    intent = new Intent(getApplicationContext(), AuthCreatePasswordActivity.class);

                    SharedPrefs.setFirstLaunch(SharedPrefs.FIRST);
                    mCheckBox.setChecked(!mCheckBox.isChecked());
                    startActivity(intent);
                }
                break;
            case R.id.button_change_pass:
                intent = new Intent(getApplicationContext(), AuthCheckPasswordActivity.class);
                intent.putExtra(SharedPrefs.CURRENT_PASSWORD, mPassIsCreated);
                startActivity(intent);
                break;
        }
    }

    private void checkPasswordExistence() {

        String password = SharedPrefs.getCurrentPassword();

        if (password.matches(SharedPrefs.DIGITS)) {

            mCheckBox.setChecked(true);
            mButton.setVisibility(View.VISIBLE);
        } else mButton.setVisibility(View.GONE);
    }
}
