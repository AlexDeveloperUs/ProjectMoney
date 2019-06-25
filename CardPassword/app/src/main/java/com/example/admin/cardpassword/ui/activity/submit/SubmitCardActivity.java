package com.example.admin.cardpassword.ui.activity.submit;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.databinding.ActivitySubmitCreditCardBinding;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cards.pay.paycardsrecognizer.sdk.ScanCardIntent;


public class SubmitCardActivity extends AppCompatActivity implements View.OnClickListener, SubmitCardContract.View {

    private boolean showingGray = true;
    private AnimatorSet inSet;
    private AnimatorSet outSet;
    private ActivitySubmitCreditCardBinding activitySubmitCreditCardBinding;
    private int mInt = 0;
    private String mString = "";
    private boolean mCheckRequestCodeForSave = true;
    private int mId = 0;
    static final int REQUEST_CODE_SCAN_CARD = 1;
    private SubmitCardContract.Presenter mPresenter = new SubmitCardPresenter(this);
    private int mCvc = 0;
    private int mPin = 0;
    private String mName;
    private String mValidity;
    private String mCardHolder;
    private int mIndex = 4;

    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        activitySubmitCreditCardBinding = DataBindingUtil.setContentView(this, R.layout.activity_submit_credit_card);
        ButterKnife.bind(this);

        checkRequestCode();

        View.OnClickListener onHelpClickListener = v -> Toast.makeText(SubmitCardActivity.this, "The CVV Number (\"Card Verification Value\") is a 3 or 4 digit number on your credit and debit cards", Toast.LENGTH_LONG).show();

        activitySubmitCreditCardBinding.iconHelpGray.setOnClickListener(onHelpClickListener);
        activitySubmitCreditCardBinding.iconHelpBlue.setOnClickListener(onHelpClickListener);

        activitySubmitCreditCardBinding.inputEditCardNumber.addTextChangedListener(new TextWatcher() {

            private boolean lock;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() != 0) {

                    flipToBlue();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (lock || s.length() > 19) {

                    return;
                }
                lock = true;

                if (s.length() % 4 == 0 && mIndex < s.length()) {

                    if (s.toString().charAt(mIndex) != ' ') {
                        s.insert(mIndex, " ");
                    }
                    mIndex += 5;
                }

                if (s.length() == 19) {

                    mString = s.toString();
                }
                lock = false;
            }
        });

        activitySubmitCreditCardBinding.inputEditExpiredDate.addTextChangedListener(new TextWatcher() {

            private boolean lock;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (lock || s.length() > 4) {

                    return;
                }
                lock = true;
                if (s.length() > 2 && !(s.toString().contains("/"))) {

                    s.insert(2, "/");
                }
                lock = false;
            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        PagerAdapter adapter = new MyPagerAdapter();
        activitySubmitCreditCardBinding.viewPager.setAdapter(adapter);
        activitySubmitCreditCardBinding.viewPager.setClipToPadding(false);
        activitySubmitCreditCardBinding.viewPager.setPadding(width / 4, 0, width / 4, 0);
        activitySubmitCreditCardBinding.viewPager.setPageMargin(width / 14);
        activitySubmitCreditCardBinding.viewPager.setPagingEnabled(false);
        activitySubmitCreditCardBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {

                    case 0:
                        updateProgressBar(17);
                        activitySubmitCreditCardBinding.inputEditCardName.setFocusableInTouchMode(true);
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditPin.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardName.requestFocus();
                        return;
                    case 1:
                        updateProgressBar(34);
                        activitySubmitCreditCardBinding.inputEditCardName.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusableInTouchMode(true);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditPin.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardNumber.requestFocus();
                        return;
                    case 2:
                        updateProgressBar(51);
                        activitySubmitCreditCardBinding.inputEditCardName.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusableInTouchMode(true);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditPin.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.requestFocus();
                        return;
                    case 3:
                        updateProgressBar(68);
                        activitySubmitCreditCardBinding.inputEditCardName.setFocusable(true);
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusableInTouchMode(true);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditPin.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.requestFocus();
                        return;
                    case 4:
                        updateProgressBar(85);
                        activitySubmitCreditCardBinding.inputEditCardName.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusableInTouchMode(true);
                        activitySubmitCreditCardBinding.inputEditPin.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCvvCode.requestFocus();
                        return;
                    case 5:
                        updateProgressBar(100);
                        activitySubmitCreditCardBinding.inputEditCardName.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditPin.setFocusableInTouchMode(true);
                        activitySubmitCreditCardBinding.inputEditPin.requestFocus();
                        return;
                    case 6:
                        activitySubmitCreditCardBinding.inputEditCardName.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditPin.setFocusable(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        TextView.OnEditorActionListener onEditorActionListener = (v, actionId, event) -> {

            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_NEXT) {

                switch (mInt) {

                    case 0:
                        mName = activitySubmitCreditCardBinding.inputEditCardName.getText().toString().equals("") ? "" :
                                activitySubmitCreditCardBinding.inputEditCardName.getText().toString();
                        activitySubmitCreditCardBinding.viewPager.setCurrentItem(activitySubmitCreditCardBinding.viewPager.getCurrentItem() + 1);
                        handled = true;
                        mInt = 1;
                        break;
                    case 1:
                        if (mPresenter.checkByLuhnAlgorithm(mString)) {

                            activitySubmitCreditCardBinding.viewPager.setCurrentItem(activitySubmitCreditCardBinding.viewPager.getCurrentItem() + 1);
                            handled = true;
                            mInt = 2;
                        }
                        break;
                    case 2:
                        mValidity = activitySubmitCreditCardBinding.inputEditExpiredDate.getText().toString().equals("") ? "" :
                                activitySubmitCreditCardBinding.inputEditExpiredDate.getText().toString();
                        activitySubmitCreditCardBinding.viewPager.setCurrentItem(activitySubmitCreditCardBinding.viewPager.getCurrentItem() + 1);
                        handled = true;
                        mInt = 3;
                        break;
                    case 3:
                        mCardHolder = activitySubmitCreditCardBinding.inputEditCardHolder.getText().toString().equals("") ? "" :
                                activitySubmitCreditCardBinding.inputEditCardHolder.getText().toString();
                        activitySubmitCreditCardBinding.viewPager.setCurrentItem(activitySubmitCreditCardBinding.viewPager.getCurrentItem() + 1);
                        handled = true;
                        mInt = 4;
                        break;
                    case 4:
                        mCvc = activitySubmitCreditCardBinding.inputEditCvvCode.getText().toString().equals("") ? 0 :
                                Integer.parseInt(Objects.requireNonNull(activitySubmitCreditCardBinding.inputEditCvvCode.getText()).toString());
                        if (mPresenter.checkCVC(mCvc)) {
                            activitySubmitCreditCardBinding.viewPager.setCurrentItem(activitySubmitCreditCardBinding.viewPager.getCurrentItem() + 1);
                            handled = true;
                            mInt = 5;
                        }
                        break;
                }
            }
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                mPin = (activitySubmitCreditCardBinding.inputEditPin.getText().toString()).equals("") ? 0 :
                        Integer.parseInt(Objects.requireNonNull(activitySubmitCreditCardBinding.inputEditCvvCode.getText()).toString());
                if (mPresenter.checkPin(mPin)) {

                    submit();
                    handled = true;
                }
            }
            return handled;
        };

        activitySubmitCreditCardBinding.inputEditCardName.setOnEditorActionListener(onEditorActionListener);
        activitySubmitCreditCardBinding.inputEditCardNumber.setOnEditorActionListener(onEditorActionListener);
        activitySubmitCreditCardBinding.inputEditExpiredDate.setOnEditorActionListener(onEditorActionListener);
        activitySubmitCreditCardBinding.inputEditCardHolder.setOnEditorActionListener(onEditorActionListener);
        activitySubmitCreditCardBinding.inputEditCvvCode.setOnEditorActionListener(onEditorActionListener);
        activitySubmitCreditCardBinding.inputEditPin.setOnEditorActionListener(onEditorActionListener);

        activitySubmitCreditCardBinding.inputEditCardName.requestFocus();

        inSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_in);
        outSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_out);
    }

    @OnClick({R.id.action_reset, R.id.image_close, R.id.action_scan})
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.action_reset:
                reset();
                break;
            case R.id.image_close:
                finish();
                break;
            case R.id.action_scan:
                scanCard();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN_CARD) {

            if (resultCode == Activity.RESULT_OK) {

                assert data != null;
                cards.pay.paycardsrecognizer.sdk.Card card = data.getParcelableExtra(ScanCardIntent.RESULT_PAYCARDS_CARD);

                activitySubmitCreditCardBinding.inputEditCardNumber.setText(mPresenter.appendVoid(card.getCardNumber()));
                activitySubmitCreditCardBinding.inputEditExpiredDate.setText(card.getExpirationDate());
                activitySubmitCreditCardBinding.inputEditCardHolder.setText(card.getCardHolderName());
                activitySubmitCreditCardBinding.inputEditCardName.requestFocus();

            }
        }
    }

    private void scanCard() {

        Intent intent = new ScanCardIntent.Builder(this).build();
        startActivityForResult(intent, REQUEST_CODE_SCAN_CARD);
    }

    @Override
    public void showToastNumber() {

        mToast = Toast.makeText(getApplicationContext(), "Заполните номер карты!", Toast.LENGTH_LONG);
        mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        mToast.show();

        activitySubmitCreditCardBinding.inputEditCardNumber.requestFocus();
    }

    @Override
    public void showToastCVC() {

        mToast = Toast.makeText(getApplicationContext(), "Заполните CVC/CVV!", Toast.LENGTH_LONG);
        mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        mToast.show();

        activitySubmitCreditCardBinding.inputEditCvvCode.requestFocus();
    }

    @Override
    public void showToastPin() {

        mToast = Toast.makeText(getApplicationContext(), "Заполните PIN!", Toast.LENGTH_LONG);
        mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        mToast.show();

        activitySubmitCreditCardBinding.inputEditPin.requestFocus();
    }

    @Override
    public void showToastCardExistence() {

        mToast = Toast.makeText(getApplicationContext(), "Карты с таким номером не существует!", Toast.LENGTH_LONG);
        mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        mToast.show();

        activitySubmitCreditCardBinding.inputEditCardNumber.requestFocus();
    }

    private class MyPagerAdapter extends PagerAdapter {

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            int resId = 0;
            switch (position) {

                case 0:
                    resId = R.id.input_layout_card_name;
                    break;
                case 1:
                    resId = R.id.input_layout_card_number;
                    break;
                case 2:
                    resId = R.id.input_layout_expired_date;
                    break;
                case 3:
                    resId = R.id.input_layout_card_holder;
                    break;
                case 4:
                    resId = R.id.input_layout_cvv_code;
                    break;
                case 5:
                    resId = R.id.input_layout_pin;
                    break;
                case 6:
                    resId = R.id.space;
                    break;
            }
            return findViewById(resId);
        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        }

        @Override
        public int getCount() {

            return 7;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

            return view == object;
        }
    }

    private void hideKeyboard(View view) {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void showKeyboard(View view) {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    private void updateProgressBar(int progress) {

        ObjectAnimator animation = ObjectAnimator.ofInt(activitySubmitCreditCardBinding.progressHorizontal, "progress", progress);
        animation.setDuration(300);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    private void submit() {

        mName = Objects.requireNonNull(activitySubmitCreditCardBinding.inputEditCardName.getText()).toString();
        long number = Long.parseLong(String.valueOf(activitySubmitCreditCardBinding.inputEditCardNumber.getText()).replaceAll("[^0-9]", ""));
        mCvc = Integer.parseInt(Objects.requireNonNull(activitySubmitCreditCardBinding.inputEditCvvCode.getText()).toString());
        mValidity = Objects.requireNonNull(activitySubmitCreditCardBinding.inputEditExpiredDate.getText()).toString();
        mCardHolder = Objects.requireNonNull(activitySubmitCreditCardBinding.inputEditCardHolder.getText()).toString();
        mPin = Integer.parseInt(Objects.requireNonNull(activitySubmitCreditCardBinding.inputEditPin.getText()).toString());

        activitySubmitCreditCardBinding.viewPager.setCurrentItem(6);

        if (mCheckRequestCodeForSave) {

            Card newCard = new Card(mName, number, mCvc, mValidity, mCardHolder, mPresenter.checkCardType(String.valueOf(activitySubmitCreditCardBinding.inputEditCardNumber.getText())), mPin);
            mPresenter.createCard(newCard);
        } else {

            Card newCard = new Card(mName, number, mCvc, mValidity, mCardHolder, mPresenter.checkCardType(String.valueOf(activitySubmitCreditCardBinding.inputEditCardNumber.getText())), mPin, mId);
            mPresenter.updateCard(newCard);
        }

        activitySubmitCreditCardBinding.inputLayoutPin.setVisibility(View.INVISIBLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        hideKeyboard(activitySubmitCreditCardBinding.inputEditPin);
        activitySubmitCreditCardBinding.progressCircle.setVisibility(View.VISIBLE);

        new Handler().postDelayed(this::finish, 1000);
    }

    private void reset() {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        activitySubmitCreditCardBinding.inputLayoutPin.setVisibility(View.VISIBLE);
        activitySubmitCreditCardBinding.progressCircle.setVisibility(View.GONE);
        flipToGray();
        activitySubmitCreditCardBinding.viewPager.setCurrentItem(0);
        activitySubmitCreditCardBinding.inputEditCardName.setText("");
        activitySubmitCreditCardBinding.inputEditCardNumber.setText("");
        activitySubmitCreditCardBinding.inputEditExpiredDate.setText("");
        activitySubmitCreditCardBinding.inputEditCardHolder.setText("");
        activitySubmitCreditCardBinding.inputEditCvvCode.setText("");
        activitySubmitCreditCardBinding.inputEditPin.setText("");
        activitySubmitCreditCardBinding.inputEditCardName.requestFocus();
        showKeyboard(activitySubmitCreditCardBinding.inputEditCardName);
    }

    private void flipToGray() {

        if (!showingGray && !outSet.isRunning() && !inSet.isRunning()) {

            showingGray = true;

            activitySubmitCreditCardBinding.cardBlue.setCardElevation(0);
            activitySubmitCreditCardBinding.cardGray.setCardElevation(0);

            outSet.setTarget(activitySubmitCreditCardBinding.cardBlue);
            outSet.start();

            inSet.setTarget(activitySubmitCreditCardBinding.cardGray);
            inSet.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    activitySubmitCreditCardBinding.cardGray.setCardElevation(convertDpToPixel(12, SubmitCardActivity.this));
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            inSet.start();
        }
    }

    private void flipToBlue() {

        if (showingGray && !outSet.isRunning() && !inSet.isRunning()) {

            showingGray = false;

            activitySubmitCreditCardBinding.cardGray.setCardElevation(0);
            activitySubmitCreditCardBinding.cardBlue.setCardElevation(0);

            outSet.setTarget(activitySubmitCreditCardBinding.cardGray);
            outSet.start();

            inSet.setTarget(activitySubmitCreditCardBinding.cardBlue);
            inSet.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    activitySubmitCreditCardBinding.cardBlue.setCardElevation(convertDpToPixel(12, SubmitCardActivity.this));
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            inSet.start();
        }
    }

    public static float convertDpToPixel(float dp, Context context) {

        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @SuppressLint("SetTextI18n")
    private void checkRequestCode() {

        Intent intent = getIntent();
        com.example.admin.cardpassword.data.models.Card card = getIntent().getParcelableExtra("card");
        String code = intent.getStringExtra("REQUEST_CODE");

        String REQUEST_CODE = "2";
        if (code != null && code.equals(REQUEST_CODE)) {

            String cvc = String.valueOf(card.getCVC());
            String pin = String.valueOf(card.getPin());

            mCheckRequestCodeForSave = false;
            activitySubmitCreditCardBinding.inputEditCardName.setText(card.getCardName());
            activitySubmitCreditCardBinding.inputEditCardNumber.setText(mPresenter.appendVoid(String.valueOf(card.mCardNumber)));
            activitySubmitCreditCardBinding.inputEditCvvCode.setText(cvc);
            activitySubmitCreditCardBinding.inputEditExpiredDate.setText(card.getValidity());
            activitySubmitCreditCardBinding.inputEditCardHolder.setText(card.getCardHolderName());
            activitySubmitCreditCardBinding.inputEditPin.setText(pin);
            mId = card.getId();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);
        if (!mCheckRequestCodeForSave) {

            new Handler().postDelayed(this::flipToBlue, 1000);
        }
    }
}
