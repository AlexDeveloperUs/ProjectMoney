package com.example.admin.cardpassword.ui.activity.create;

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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.cardpassword.R;
import com.github.pinball83.maskededittext.MaskedEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CreateContract.View, View.OnClickListener {

    private static final String EMPTY_STRING = "";
    private CreatePresenter mPresenter;

    @BindView(R.id.spinner)
    Spinner mSpinner;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);
        ButterKnife.bind(this);

        mPresenter = new CreatePresenter(this);
        setSpinnerData();
        textChangeListener();

        mBtnSaveCard.setOnClickListener(this);
    }

    @Override
    protected void onPostResume() {

        super.onPostResume();
        Toast.makeText(this, "Внимание! Заполните обязательные поля: номер карты, PIN, CVC", Toast.LENGTH_LONG).show();
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

    private void setSpinnerData() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.card_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

        String number = mCardNumber.getText().toString();
        mPresenter.cardNumberValidation(number);

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
