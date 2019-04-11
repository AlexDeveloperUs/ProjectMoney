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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.data.AppDataBase;
import com.example.admin.cardpassword.data.DatabaseClient;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.ui.activity.list.ListActivity;
import com.example.admin.cardpassword.utils.ThreadExecutors;
import com.github.pinball83.maskededittext.MaskedEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateActivity extends AppCompatActivity implements CreateContract.View, View.OnClickListener {

    private static final String EMPTY_STRING = "";
    private CreatePresenter mPresenter;
    private String mCardType = "";
    private ThreadExecutors mExecutors = new ThreadExecutors();
    private AppDataBase mAppDataBase;

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

                insert(mCardType);
                break;
        }
    }

    private void saveCard(String pCardType) {



//        class SaveCard extends AsyncTask<Void, Void, Void> {
//
//
//
//            @Override
//            protected Void doInBackground(Void... pVoids) {
//
//                Card card = new Card();
//                card.setCardNumber(mByteCardNumber);
//                card.setCVC(mByteCvc);
//                card.setValidity(mByteValidity);
//                card.setCardHolderName(cardHoldersName);
//                card.setCardHolderSurname(cardHoldersSurname);
//                card.setCardType(pCardType);
//                card.setPin(mBytePin);
//
//                DatabaseClient.getmInstance(getApplicationContext()).getAppDataBase().mCardDao().insert(card);
//
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void pVoid) {
//
//                super.onPostExecute(pVoid);
//                finish();
//
//                Intent intent = new Intent();
//                setResult(RESULT_OK, intent);
//                finish();
//
//                Toast.makeText(getApplicationContext(), mByteCardNumber + "\n" + mByteCvc + " " + mByteValidity + " " + mBytePin, Toast.LENGTH_LONG).show();
//            }
        }

//        SaveCard saveCard = new SaveCard();
//        saveCard.execute();
//    }

    private void insert(String pCardType) {

        final String cvc = mCardCvc.getText().toString();
        final String cardNumber;
        final String validity;
        final String cardHoldersName;
        final String cardHoldersSurname;
        final String pin = mCardPin.getText().toString();
        String mValidityContains = mCardValidity.getText().toString();
        String mCardNumberCheck = mCardNumber.getText().toString();
        String holdersName = mCardHoldersName.getText().toString();
        String holdersSurname = mCardHoldersSurname.getText().toString();


        if (mCardNumberCheck.toLowerCase().contains("x")) {

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

            mCvcLayout.setError(EMPTY_STRING);
            mPinLayout.setError("Введите пин-код");
            mCardPin.requestFocus();
            return;
        }

        if (!holdersName.isEmpty()) {

            cardHoldersName = holdersName;
        } else cardHoldersName = "";

        if (!holdersSurname.isEmpty()) {

            cardHoldersSurname = holdersSurname;
        } else cardHoldersSurname = "";


        if (!mValidityContains.equals("") && mValidityContains.contains("/")) {

            validity = mValidityContains.replaceAll("[^A-Za-zА-Яа-я0-9]", "");
        } else
            validity = "0";

        if (mCardNumberCheck.contains("-")) {
            mNumberLayout.setError(EMPTY_STRING);

            cardNumber = mCardNumberCheck.replaceAll("[^A-Za-zА-Яа-я0-9]", "");
            mPinLayout.setError(EMPTY_STRING);
        } else cardNumber = null;

        Long mByteCardNumber = Long.parseLong(cardNumber);
        short mByteCvc = Short.parseShort(cvc);
        short mByteValidity = Short.parseShort(validity);
        short mBytePin = Short.valueOf(pin);

        mExecutors.dbExecutor().execute(() -> DatabaseClient.getmInstance(getApplicationContext()).getAppDataBase().mCardDao().insert(new Card(mByteCardNumber, mByteCvc, mByteValidity, cardHoldersName, cardHoldersSurname, pCardType, mBytePin)));

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
