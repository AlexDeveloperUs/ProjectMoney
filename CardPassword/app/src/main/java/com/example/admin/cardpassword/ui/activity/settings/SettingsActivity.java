package com.example.admin.cardpassword.ui.activity.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.ui.activity.auth.AuthCheckPasswordActivity;
import com.example.admin.cardpassword.ui.activity.auth.AuthCreatePasswordActivity;
import com.example.admin.cardpassword.ui.activity.create.CreateActivity;
import com.example.admin.cardpassword.ui.activity.list.ListActivity;
import com.example.admin.cardpassword.utils.SharedPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int KEY_LIST_ACTIVITY = 1;
    private static final int KEY_CREATE_ACTIVITY = 2;
    private Class mClass;
    private Context mContext = SettingsActivity.this;

    @BindView(R.id.switch_create_pass)
    Switch mSwitchCreate;
    @BindView(R.id.switch_change_pass)
    Switch mSwitchChange;
    @BindView(R.id.switch_usage_pass)
    Switch mSwitchUsage;
    @BindView(R.id.bottom_app_bar_settings)
    BottomAppBar mBottomAppBar;
    @BindView(R.id.image_view_cards_settings)
    ImageView mImageViewCards;

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

            mClass = CreateActivity.class;
        }
    }

    @OnClick({R.id.fab_settings, R.id.image_view_cards_settings, R.id.switch_create_pass, R.id.switch_usage_pass, R.id.switch_change_pass})
    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {

            case R.id.fab_settings:
                intent = new Intent(mContext, mClass);
                startActivity(intent);
                finish();
                break;
            case R.id.image_view_cards_settings:
                intent = new Intent(mContext, ListActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.switch_create_pass:
                intent = new Intent(getApplicationContext(), AuthCreatePasswordActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefs.SHARED_PREFERENCES_FIRST_LAUNCH_NAME, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SharedPrefs.SHARED_PREFERENCES_FIRST_LAUNCH_KEY, "first");
                editor.apply();
                startActivity(intent);
                mSwitchCreate.setChecked(false);
                break;
            case R.id.switch_usage_pass:
                if (!mSwitchUsage.isChecked()) {

                    SharedPreferences.Editor edit = mSharedPreferences.edit();
                    edit.putString(SharedPrefs.SHARED_PREFERENCES_KEY, "disabled");
                    edit.apply();
                }
                break;
            case R.id.switch_change_pass:
                intent = new Intent(getApplicationContext(), AuthCheckPasswordActivity.class);
                intent.putExtra("currentPass", mPassIsCreated);
                startActivity(intent);
                break;
        }
    }

    private void checkPasswordExistence() {

        String mPassword = mSharedPreferences.getString(SharedPrefs.SHARED_PREFERENCES_KEY, "");

        assert mPassword != null;

        if (mPassword.equals("")) {

        } else if (mPassword.matches("[\\d]+")) {

            mSwitchUsage.setChecked(true);
        } else if (mPassword.contains("skipped")) {

        }
    }
}
