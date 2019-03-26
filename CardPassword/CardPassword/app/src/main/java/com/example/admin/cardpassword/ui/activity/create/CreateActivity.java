package com.example.admin.cardpassword.ui.activity.create;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.data.DatabaseClient;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.ui.activity.list.ListActivity;
import com.github.pinball83.maskededittext.MaskedEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CreateContract.View, View.OnClickListener {

    private static final String EMPTY_STRING = "";
    private CreatePresenter mPresenter;
    private String mCardType = "";

    @BindView(R.id.btn_visa)
    RadioButton mButtonVisa;
    @BindView(R.id.btn_master_card)
    RadioButton mButtonMasterCard;
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
    @BindView(R.id.btn_save_card)
    Button mBtnSaveCard;
    @BindView(R.id.card_number_layout)
    TextInputLayout mNumberLayout;
    @BindView(R.id.card_cvc_layout)
    TextInputLayout mCvcLayout;
    @BindView(R.id.card_pin_layout)
    TextInputLayout mPinLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);
        ButterKnife.bind(this);

        mPresenter = new CreatePresenter(this);
        textChangeListener();

        mBtnSaveCard.setOnClickListener(this);
        mButtonVisa.setOnClickListener(this);
        mButtonMasterCard.setOnClickListener(this);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

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

                String number = mCardNumber.getText().toString();
                mPresenter.cardNumberValidation(number);

                saveCard();
                break;
        }
    }

    private void saveCard() {

        final String cardNumber = mCardNumber.getText().toString().trim();
        final String cvc = mCardCvc.getText().toString().trim();
        final String validity = mCardValidity.getText().toString().trim();
        final String cardHoldersName = mCardHoldersName.getText().toString().trim();
        final String cardHoldersSurname = mCardHoldersSurname.getText().toString().trim();
        final String pin = mCardPin.getText().toString().trim();


        if (cardNumber.isEmpty()) {

            mNumberLayout.setError("Введите корректный номер карты");
            mCardNumber.requestFocus();
            return;
        }


        if (cvc.isEmpty()) {

            mCvcLayout.setError("Заполните cvc");
            mCardCvc.requestFocus();
            return;
        }

        if (pin.isEmpty()) {

            mPinLayout.setError("Введите пин-код");
            mCardPin.requestFocus();
            return;
        }

        class SaveCard extends AsyncTask<Void, Void, Void> {

            private Byte mByteCardNumber = Byte.valueOf(cardNumber);
            private Byte mByteCvc = Byte.valueOf(cvc);
            private Byte mByteValidity = Byte.valueOf(validity);
            private Byte mBytePin = Byte.valueOf(pin);

            @Override
            protected Void doInBackground(Void... pVoids) {

                Card card = new Card();
                card.setCardNumber(mByteCardNumber);
                card.setCVC(mByteCvc);
                card.setValidity(mByteValidity);
                card.setCardHolderName(cardHoldersName);
                card.setCardHolderSurname(cardHoldersSurname);
                card.setCardType(mCardType);
                card.setPin(mBytePin);

                DatabaseClient.getmInstance(getApplicationContext()).getAppDataBase().mCardDao().insert(card);

                return null;
            }

            @Override
            protected void onPostExecute(Void pVoid) {

                super.onPostExecute(pVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), ListActivity.class));
            }
        }

        SaveCard saveCard = new SaveCard();
        saveCard.execute();
    }

    @Override
    public void showError() {

        mNumberLayout.setError("Введите корректные данные");

    }

    @Override
    public boolean shouldShowError() {

        int length = mCardNumber.length();
        return length >= 0 && length < 16;
    }

    @Override
    public void hideError() {

        mNumberLayout.setError(EMPTY_STRING);
    }
}
