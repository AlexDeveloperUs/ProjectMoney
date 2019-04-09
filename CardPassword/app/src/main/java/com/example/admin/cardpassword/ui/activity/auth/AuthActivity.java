package com.example.admin.cardpassword.ui.activity.auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.ui.activity.list.ListContract;
import com.example.admin.cardpassword.ui.adapters.CardListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_six)
    ImageButton mButtonSix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);

        mButtonSix.setOnClickListener(this);
    }

    public void onClick(View v) {

        Toast.makeText(this, "U just pressed 6 btn", Toast.LENGTH_LONG).show();
    }
}
