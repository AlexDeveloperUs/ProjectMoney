package com.flexsoft.cardpassword.ui.activity.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int KEY_LIST_ACTIVITY = 1;
    private static final int KEY_CREATE_ACTIVITY = 2;
    private Class mClass;
    private Context mContext = SettingsActivity.this;

    @BindView(R.id.frame_usage_pass)
    FrameLayout mLayout;
    @BindView(R.id.frame_change_pass)
    FrameLayout mChangeLayout;
    @BindView(R.id.checkbox)
    CheckBox mCheckBox;
    @BindView(R.id.button_change_pass)
    AppCompatButton mButton;

    private String mPassIsCreated;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        checkIntent();

        mSharedPreferences = getSharedPreferences(SharedPrefs.SHARED_PREFERENCES_NAME, 0);
        mPassIsCreated = mSharedPreferences.getString(SharedPrefs.SHARED_PREFERENCES_KEY, "");

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
    @Override
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

                    SharedPreferences.Editor edit = mSharedPreferences.edit();
                    edit.putString(SharedPrefs.SHARED_PREFERENCES_KEY, "disabled");
                    mButton.setVisibility(View.GONE);
                    edit.apply();
                } else {

                    intent = new Intent(getApplicationContext(), AuthCreatePasswordActivity.class);
                    SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefs.SHARED_PREFERENCES_FIRST_LAUNCH_NAME, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(SharedPrefs.SHARED_PREFERENCES_FIRST_LAUNCH_KEY, "first");
                    editor.apply();
                    mCheckBox.setChecked(!mCheckBox.isChecked());
                    startActivity(intent);
                }
                break;
            case R.id.button_change_pass:
                intent = new Intent(getApplicationContext(), AuthCheckPasswordActivity.class);
                intent.putExtra("currentPass", mPassIsCreated);
                startActivity(intent);
                break;
        }
    }

    private void checkPasswordExistence() {

        String mPassword = mSharedPreferences.getString(SharedPrefs.SHARED_PREFERENCES_KEY, "");

        assert mPassword != null;

        if (mPassword.matches("[\\d]+")) {

            mCheckBox.setChecked(true);
            mButton.setVisibility(View.VISIBLE);
        } else mButton.setVisibility(View.GONE);
    }
}
