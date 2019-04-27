package com.example.admin.cardpassword.ui.activity.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.ui.activity.list.ListActivity;
import com.example.admin.cardpassword.utils.SharedPrefs;

public class AuthMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_auth);

        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefs.SHARED_PREFERENCES_NAME, 0);
        String mPassword = sharedPreferences.getString(SharedPrefs.SHARED_PREFERENCES_KEY, "");

        assert mPassword != null;
        Intent intent;

        if (mPassword.equals("")) {

            intent = new Intent(getApplicationContext(), AuthCreatePasswordActivity.class);
            startActivity(intent);
            finish();
        } else if (mPassword.matches("[\\d]+")) {

            intent = new Intent(getApplicationContext(), AuthCheckPasswordActivity.class);
            startActivity(intent);
            finish();
        } else if (mPassword.contains("skipped")) {

            intent = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);
            finish();
        } else if (mPassword.contains("disabled")) {

            intent = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);
        }
    }
}
