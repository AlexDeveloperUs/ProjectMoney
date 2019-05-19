package com.example.admin.cardpassword.ui.activity.create;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.ui.activity.list.ListActivity;
import com.example.admin.cardpassword.ui.activity.settings.SettingsActivity;
import com.example.admin.cardpassword.utils.ActivitySubmitCreditCard;
import com.github.pinball83.maskededittext.MaskedEditText;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cards.pay.paycardsrecognizer.sdk.Card;
import cards.pay.paycardsrecognizer.sdk.ScanCardIntent;

public class CreateActivity extends AppCompatActivity implements CreateContract.View, View.OnClickListener {

    private static final String EMPTY_STRING = "";
    private CreatePresenter mPresenter;
    private boolean mCheckRequestCodeForSave = true;
    private int mId = 0;
    static final int REQUEST_CODE_SCAN_CARD = 1;

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
    @BindView(R.id.btn_save_card)
    CardView mCardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);
        ButterKnife.bind(this);

        mPresenter = new CreatePresenter(this);
        checkRequestCode();
    }

    @Override
    protected void onPostResume() {

        super.onPostResume();
        Toast.makeText(this, "Внимание! Для корректной работы приложения, заполните все поля", Toast.LENGTH_LONG).show();
    }

    private void changeTextValidity() {

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

    @OnClick({R.id.btn_save_card, R.id.fab_create, R.id.image_view_settings_create, R.id.btn_action_card_photo, R.id.btn_action_animated_creating_card})
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {

            case R.id.btn_save_card:
                mCardView.setBackgroundColor(Color.parseColor("#D81B60"));
                insert();
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
            case R.id.btn_action_card_photo:
                scanCard();
                break;
            case R.id.btn_action_animated_creating_card:
                intent = new Intent(CreateActivity.this, ActivitySubmitCreditCard.class);
                startActivity(intent);
                finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN_CARD) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                Card card = data.getParcelableExtra(ScanCardIntent.RESULT_PAYCARDS_CARD);

                mCardNumber.setMaskedText(String.valueOf(card.getCardNumber()));
                mCardValidity.setText(card.getExpirationDate());
                mCardHoldersName.setText(card.getCardHolderName());
                String cardData = "Card number: " + card.getCardNumberRedacted() + "\n"
                        + "Card holder: " + card.getCardHolderName() + "\n"
                        + "Card expiration date: " + card.getExpirationDate();
            } else if (resultCode == Activity.RESULT_CANCELED) {
            } else {
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            View v = getCurrentFocus();
            if (v instanceof EditText) {

                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void insert() {

        String cardNumber = Objects.requireNonNull(mCardNumber.getText()).toString();
        String cardCvc = mCardCvc.getText().toString();
        String cardValidity = mCardValidity.getText().toString();
        String cardHolderName = mCardHoldersName.getText().toString();
        String cardHolderSurname = mCardHoldersSurname.getText().toString();
        String cardPin = mCardPin.getText().toString();

        mPresenter.checkDataValidation(cardNumber, cardCvc, cardValidity, cardHolderName, cardHolderSurname, cardPin, mCheckRequestCodeForSave, mId);
    }

    private void checkRequestCode() {

        Intent intent = getIntent();
        com.example.admin.cardpassword.data.models.Card card = getIntent().getParcelableExtra("card");
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
            mCardPin.setText(pin);
            mId = card.getId();
        } else changeTextValidity();
    }

    @Override
    public void showExistenceError() {

        mNumberLayout.setError("Карты с таким номером не существует!");
        mCardNumber.requestFocus();
    }

    @Override
    public void showNumberError() {

        mNumberLayout.setError("Введите номер карты");
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

    @Override
    public void createAndUpdateCard() {

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void scanCard() {
        Intent intent = new ScanCardIntent.Builder(this).build();
        startActivityForResult(intent, REQUEST_CODE_SCAN_CARD);
    }
}
