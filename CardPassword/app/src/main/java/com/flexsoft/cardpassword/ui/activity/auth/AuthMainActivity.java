package com.flexsoft.cardpassword.ui.activity.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.flexsoft.cardpassword.R;
import com.flexsoft.cardpassword.ui.activity.list.ListActivity;
import com.flexsoft.cardpassword.utils.SharedPrefs;

public class AuthMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_auth);

        SharedPrefs sharedPrefs = new SharedPrefs(this);

        String password = SharedPrefs.getCurrentPassword();

        assert password != null;

        Intent intent;

        if (password.equals(SharedPrefs.EMPTY_STRING)) {

            intent = new Intent(getApplicationContext(), AuthCreatePasswordActivity.class);
            startActivity(intent);
            finish();
        } else if (password.matches(SharedPrefs.DIGITS)) {

            intent = new Intent(getApplicationContext(), AuthCheckPasswordActivity.class);
            startActivity(intent);
            finish();
        } else if (password.contains(SharedPrefs.SKIPPED)) {

            intent = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);
            finish();
        } else if (password.contains(SharedPrefs.DISABLED)) {

            intent = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);
        }
    }
}
