package com.example.admin.cardpassword.ui.activity.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.cardpassword.R;

public class AuthMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_auth);

        SharedPreferences sharedPreferences = getSharedPreferences("PAS", 0);
        String mPassword = sharedPreferences.getString("pas", "");

        assert mPassword != null;
        if (mPassword.equals("")) {

            Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
            startActivity(intent);
            finish();
        } else {

            Intent intent = new Intent(getApplicationContext(), AuthCheckPass.class);
            startActivity(intent);
            finish();
        }
    }
}
