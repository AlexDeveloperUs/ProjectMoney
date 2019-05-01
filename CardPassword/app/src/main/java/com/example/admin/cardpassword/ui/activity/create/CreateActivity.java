package com.example.admin.cardpassword.ui.activity.create;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.ui.activity.list.ListActivity;
import com.example.admin.cardpassword.ui.activity.settings.SettingsActivity;
import com.github.pinball83.maskededittext.MaskedEditText;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateActivity extends AppCompatActivity implements CreateContract.View, View.OnClickListener {

    private static final String EMPTY_STRING = "";
    private CreatePresenter mPresenter;
    private String mCardType = "visa";
    private boolean mCheckRequestCodeForSave = true;
    private int mId = 0;

    @BindView(R.id.edit_text_card_number)
    MaskedEditText mCardNumber;
    @BindView(R.id.edit_text_card_cvc_cvv)
    EditText mCardCvc;
    @BindView(R.id.edit_text_card_validity)
    EditText mCardValidity;
    @BindView(R.id.edit_text_card_holders_name)
    EditText mCardHoldersName;
    @BindView(R.id.edit_text_card_holders_surname)
    EditText mCardHoldersSurname;
    @BindView(R.id.edit_text_card_pin)
    EditText mCardPin;
    @BindView(R.id.card_number_layout)
    TextInputLayout mNumberLayout;
    @BindView(R.id.card_cvc_layout)
    TextInputLayout mCvcLayout;
    @BindView(R.id.card_pin_layout)
    TextInputLayout mPinLayout;
    @BindView(R.id.bottom_app_bar_create_card)
    BottomAppBar mBottomAppBar;
    @BindView(R.id.fab_create)
    FloatingActionButton mButtonBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);
        ButterKnife.bind(this);

        mPresenter = new CreatePresenter(this);
        checkRequestCode();
        InputMethodManager img = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        img.showSoftInput(mCardNumber, 0);
    }

    @Override
    protected void onPostResume() {

        super.onPostResume();
        Toast.makeText(this, "Внимание! Для корректной работы приложения, заполните все поля", Toast.LENGTH_LONG).show();
    }

    private void textChangeListener() {

        mCardValidity.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String string = s.toString();

                if (s.length() % 2 == 0 && string.length() <= 3 && !string.contains("/")) {

                    string += "/";
                    mCardValidity.setText(string);
                    mCardValidity.setSelection(string.length());
                }
            }
        });
    }

    @OnClick({R.id.btn_visa, R.id.btn_master_card, R.id.btn_save_card, R.id.fab_create, R.id.image_view_settings_create})
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {

            case R.id.btn_visa:
                mCardType = "visa";
                Toast.makeText(this, "Visa", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_master_card:
                mCardType = "mastercard";
                Toast.makeText(this, "MasterCard", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_save_card:
                insert(mCardType);
                break;
            case R.id.fab_create:
                intent = new Intent(CreateActivity.this, ListActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.image_view_settings_create:
                intent = new Intent(CreateActivity.this, SettingsActivity.class);
                intent.putExtra("key", 2);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void insert(String pCardType) {

        String cardNumber = Objects.requireNonNull(mCardNumber.getText()).toString();
        String cardCvc = mCardCvc.getText().toString();
        String cardValidity = mCardValidity.getText().toString();
        String cardHolderName = mCardHoldersName.getText().toString();
        String cardHolderSurname = mCardHoldersSurname.getText().toString();
        String cardPin = mCardPin.getText().toString();

        mPresenter.checkDataValidation(cardNumber, cardCvc, cardValidity, cardHolderName, cardHolderSurname,  pCardType, cardPin, mCheckRequestCodeForSave, mId);

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void checkRequestCode() {

        Intent intent = getIntent();
        Card card = getIntent().getParcelableExtra("card");
        String code = intent.getStringExtra("REQUEST_CODE");

        String REQUEST_CODE = "2";
        if (code != null && code.equals(REQUEST_CODE)) {

            String cvc = String.valueOf(card.getCVC());
            String pin = String.valueOf(card.getPin());

            mCheckRequestCodeForSave = false;
            mCardNumber.setMaskedText(String.valueOf(card.getCardNumber()));
            mCardCvc.setText(cvc);
            mCardValidity.setText(card.getValidity());
            mCardHoldersName.setText(card.getCardHolderName());
            mCardHoldersSurname.setText(card.getCardHolderSurname());
            mCardType = card.getCardType();
            mCardPin.setText(pin);
            mId = card.getId();
        } else textChangeListener();
    }

    @Override
    public void showExistenceError() {

        mNumberLayout.setError("Карты с таким номером не существует!");
        mCardNumber.requestFocus();
    }

    @Override
    public void removeExistenceError() {

        mNumberLayout.setError(EMPTY_STRING);
    }

    @Override
    public void showNumberError() {

        mNumberLayout.setError("Error");
        mCardNumber.requestFocus();
    }

    @Override
    public void showCvcError() {

        mCvcLayout.setError("Заполните cvc");
        mCardCvc.requestFocus();
    }

    @Override
    public void showPinError() {

        mPinLayout.setError("Введите пин-код");
        mCardPin.requestFocus();
    }

    @Override
    public void removeNumberError() {

        mNumberLayout.setError(EMPTY_STRING);
    }

    @Override
    public void removeCvcError() {

        mCvcLayout.setError(EMPTY_STRING);
    }

    @Override
    public void removePinError() {

        mPinLayout.setError(EMPTY_STRING);
    }
}
