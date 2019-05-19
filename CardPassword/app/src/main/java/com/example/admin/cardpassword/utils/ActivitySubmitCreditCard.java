package com.example.admin.cardpassword.utils;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.cardpassword.App;
import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.databinding.ActivitySubmitCreditCardBinding;

import java.util.Objects;


public class ActivitySubmitCreditCard extends AppCompatActivity {

    private boolean showingGray = true;
    private AnimatorSet inSet;
    private AnimatorSet outSet;
    private ActivitySubmitCreditCardBinding activitySubmitCreditCardBinding;
    private Card card;
    private int mInt = 0;
    private String mString = "";
    private ThreadExecutors mExecutors = new ThreadExecutors();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        activitySubmitCreditCardBinding = DataBindingUtil.setContentView(this, R.layout.activity_submit_credit_card);
        card = new Card();

        View.OnClickListener onHelpClickListener = v -> Toast.makeText(ActivitySubmitCreditCard.this, "The CVV Number (\"Card Verification Value\") is a 3 or 4 digit number on your credit and debit cards", Toast.LENGTH_LONG).show();

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
                for (int i = 4; i < s.length(); i += 5) {

                    if (s.toString().charAt(i) != ' ') {

                        s.insert(i, " ");
                    }
                }

                Toast.makeText(getApplicationContext(), s.length()+"", Toast.LENGTH_LONG).show();

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
                if (s.length() > 2 && s.toString().charAt(2) != '/') {

                    s.insert(2, "/");
                }
                lock = false;
            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
//        int height = size.y;

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
                        updateProgressBar(20);
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusableInTouchMode(true);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditPin.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardNumber.requestFocus();
                        return;
                    case 1:
                        updateProgressBar(40);
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusableInTouchMode(true);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditPin.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.requestFocus();
                        return;
                    case 2:
                        updateProgressBar(50);
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusableInTouchMode(true);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditPin.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.requestFocus();
                        return;
                    case 3:
                        updateProgressBar(80);
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusableInTouchMode(true);
                        activitySubmitCreditCardBinding.inputEditPin.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCvvCode.requestFocus();
                        return;
                    case 4:
                        updateProgressBar(100);
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditPin.setFocusableInTouchMode(true);
                        activitySubmitCreditCardBinding.inputEditPin.requestFocus();
                        return;
                    case 5:
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
                        if (mString.equals("")) {

                            Toast.makeText(getApplicationContext(), "Введите номер карты", Toast.LENGTH_LONG).show();
                        } else {

                            if (checkByLuhnAlgorithm(mString)) {

                                activitySubmitCreditCardBinding.viewPager.setCurrentItem(activitySubmitCreditCardBinding.viewPager.getCurrentItem() + 1);
                                handled = true;
                                mInt = 1;
                            }
                        }
                        break;
                    case 1:
                        activitySubmitCreditCardBinding.viewPager.setCurrentItem(activitySubmitCreditCardBinding.viewPager.getCurrentItem() + 1);
                        handled = true;
                        mInt = 2;
                        break;
                    case 2:
                        activitySubmitCreditCardBinding.viewPager.setCurrentItem(activitySubmitCreditCardBinding.viewPager.getCurrentItem() + 1);
                        handled = true;
                        mInt = 3;
                        break;
                    case 3:
                        activitySubmitCreditCardBinding.viewPager.setCurrentItem(activitySubmitCreditCardBinding.viewPager.getCurrentItem() + 1);
                        handled = true;
                        mInt = 4;
                        break;
                    case 4:
                        activitySubmitCreditCardBinding.viewPager.setCurrentItem(activitySubmitCreditCardBinding.viewPager.getCurrentItem() + 1);
                        handled = true;
                        mInt = 5;
                        break;
                    case 5:
                }


            }
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                submit();
                handled = true;
            }
            return handled;
        };

        activitySubmitCreditCardBinding.inputEditCardNumber.setOnEditorActionListener(onEditorActionListener);
        activitySubmitCreditCardBinding.inputEditExpiredDate.setOnEditorActionListener(onEditorActionListener);
        activitySubmitCreditCardBinding.inputEditCardHolder.setOnEditorActionListener(onEditorActionListener);
        activitySubmitCreditCardBinding.inputEditCvvCode.setOnEditorActionListener(onEditorActionListener);
        activitySubmitCreditCardBinding.inputEditPin.setOnEditorActionListener(onEditorActionListener);

        activitySubmitCreditCardBinding.inputEditCardNumber.requestFocus();

        inSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_in);
        outSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_out);
    }

    private class MyPagerAdapter extends PagerAdapter {

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            int resId = 0;
            switch (position) {

                case 0:
                    resId = R.id.input_layout_card_number;
                    break;
                case 1:
                    resId = R.id.input_layout_expired_date;
                    break;
                case 2:
                    resId = R.id.input_layout_card_holder;
                    break;
                case 3:
                    resId = R.id.input_layout_cvv_code;
                    break;
                case 4:
                    resId = R.id.input_layout_pin;
                    break;
                case 5:
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

            return 6;
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

        long number = Long.parseLong(String.valueOf(activitySubmitCreditCardBinding.inputEditCardNumber.getText()).replaceAll("[^0-9]", ""));
        int cvc = Integer.parseInt(Objects.requireNonNull(activitySubmitCreditCardBinding.inputEditCvvCode.getText()).toString());
        String cardHolder = Objects.requireNonNull(activitySubmitCreditCardBinding.inputEditCardHolder.getText()).toString();
        int pin = Integer.parseInt(Objects.requireNonNull(activitySubmitCreditCardBinding.inputEditPin.getText()).toString());
        String validity = Objects.requireNonNull(activitySubmitCreditCardBinding.inputEditExpiredDate.getText()).toString();
        String type = "";

        activitySubmitCreditCardBinding.viewPager.setCurrentItem(5);
        Card newCard = new Card(number, cvc, validity, cardHolder, checkCardType(String.valueOf(activitySubmitCreditCardBinding.inputEditCardNumber.getText())), pin);
        mExecutors.dbExecutor().execute(() -> mExecutors.dbExecutor().execute(() -> App.mInstance.getDataBase().mCardDao().insert(newCard)));
//        card.setCardNumber(Long.parseLong(String.valueOf(activitySubmitCreditCardBinding.inputEditCardNumber.getText()).replaceAll("[^0-9]", "")));
//        card.setCardNumber(Long.parseLong(Objects.requireNonNull(activitySubmitCreditCardBinding.inputEditCardNumber.getText()).toString()));
//        card.setValidity(Objects.requireNonNull(activitySubmitCreditCardBinding.inputEditExpiredDate.getText()).toString());
//        card.setCardHolderSurname(Objects.requireNonNull(activitySubmitCreditCardBinding.inputEditCardHolder.getText()).toString());
//        card.setCVC(Integer.parseInt(Objects.requireNonNull(activitySubmitCreditCardBinding.inputEditCvvCode.getText()).toString()));
//        card.setPin(Integer.parseInt(Objects.requireNonNull(activitySubmitCreditCardBinding.inputEditPin.getText()).toString()));

        Toast.makeText(ActivitySubmitCreditCard.this, card.toString(), Toast.LENGTH_LONG).show();

        new Handler().postDelayed(() -> {

            activitySubmitCreditCardBinding.inputLayoutPin.setVisibility(View.INVISIBLE);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
            activitySubmitCreditCardBinding.labelSecureSubmission.setVisibility(View.VISIBLE);
            hideKeyboard(activitySubmitCreditCardBinding.inputEditPin);
            activitySubmitCreditCardBinding.progressCircle.setVisibility(View.VISIBLE);
        }, 300);
    }

    private void reset() {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        activitySubmitCreditCardBinding.inputLayoutPin.setVisibility(View.VISIBLE);
        activitySubmitCreditCardBinding.progressCircle.setVisibility(View.GONE);
        activitySubmitCreditCardBinding.labelSecureSubmission.setVisibility(View.GONE);
        flipToGray();
        activitySubmitCreditCardBinding.viewPager.setCurrentItem(0);
        activitySubmitCreditCardBinding.inputEditCardNumber.setText("");
        activitySubmitCreditCardBinding.inputEditExpiredDate.setText("");
        activitySubmitCreditCardBinding.inputEditCardHolder.setText("");
        activitySubmitCreditCardBinding.inputEditCvvCode.setText("");
        activitySubmitCreditCardBinding.inputEditPin.setText("");
        activitySubmitCreditCardBinding.inputEditCardNumber.requestFocus();
        showKeyboard(activitySubmitCreditCardBinding.inputEditCardNumber);
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

                    activitySubmitCreditCardBinding.cardGray.setCardElevation(convertDpToPixel(12, ActivitySubmitCreditCard.this));
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

                    activitySubmitCreditCardBinding.cardBlue.setCardElevation(convertDpToPixel(12, ActivitySubmitCreditCard.this));
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

    public static float convertDpToPixel(float dp, Context context){

        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

//    public static float convertPixelsToDp(float px, Context context){
//
//        Resources resources = context.getResources();
//        DisplayMetrics metrics = resources.getDisplayMetrics();
//        return px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_reset:
                reset();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkByLuhnAlgorithm(String pS) {

        if (pS.equals("")) {

            Toast.makeText(getApplicationContext(), "Заполните номер карты", Toast.LENGTH_LONG).show();
            return false;
        } else {

            return checkCardExistence(Long.parseLong(pS.replaceAll("[^0-9]", "")));
        }
    }

    private boolean checkCardExistence(long pNumber) {

        long count = 0;
        long character;

        for (int i = 16; i > 0; i--) {

            character = pNumber % 10;
            pNumber /= 10;

            if (i % 2 == 1) {

                int check = (int) (character * 2);

                if (check > 9) {

                    count += check - 9;
                } else {

                    count += check;
                }
            } else {

                count += character;
            }
        }

        if (count % 10 == 0) {

            Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_LONG).show();
            return true;
        } else {

            Toast.makeText(getApplicationContext(), "fuck", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public String checkCardType(String pS) {

        pS = String.valueOf(Long.parseLong(pS.replaceAll("[^0-9]", "")));

        String mCardType = "";

        if (pS.charAt(0) == '4') {

            mCardType = "visa";
        } else if (pS.charAt(0) == '5' && (pS.charAt(1) == '1' || pS.charAt(1) == '2' || pS.charAt(1) == '3' || pS.charAt(1) == '4'
                || pS.charAt(1) == '5')) {

            mCardType = "mastercard";
        } else if (pS.substring(0, 4).equals("9112")) {

            mCardType = "belcard";
        } else if ((pS.charAt(0) == '5' && (pS.charAt(1) == '0' || pS.charAt(1) == '6' || pS.charAt(1) == '7' || pS.charAt(1) == '8'))
                || (pS.charAt(0) == '6' && (pS.charAt(1) == '3' || pS.charAt(1) == '7'))) {

            mCardType = "maestro";
        }

        return mCardType;
    }
}
