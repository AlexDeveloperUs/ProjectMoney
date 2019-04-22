package com.example.admin.cardpassword.ui.activity.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.ui.activity.create.CreateActivity;
import com.example.admin.cardpassword.ui.activity.list.ListActivity;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        checkIntent();
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

    @OnClick({R.id.fab_settings, R.id.image_view_cards_settings})
    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {

            case R.id.fab_settings:
                intent = new Intent(mContext, mClass);
                startActivity(intent);
                break;
            case R.id.image_view_cards_settings:
                intent = new Intent(mContext, ListActivity.class);
                startActivity(intent);
        }
    }
}
