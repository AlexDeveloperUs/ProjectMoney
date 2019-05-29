package com.example.admin.cardpassword.ui.activity.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.ui.activity.create.CreateActivity;
import com.example.admin.cardpassword.ui.activity.settings.SettingsActivity;
import com.example.admin.cardpassword.ui.adapters.CardListAdapter;
import com.example.admin.cardpassword.ui.fragments.fragment2.FragmentCardFlip;
import com.example.admin.cardpassword.utils.ActivitySubmitCreditCard;
import com.example.admin.cardpassword.utils.LadderLayoutManager;
import com.example.admin.cardpassword.utils.LadderSimpleSnapHelper;
import com.example.admin.cardpassword.utils.OnSwipeTouchListener;
import com.example.admin.cardpassword.utils.VerticalSampleChildDecorateHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class ListActivity extends AppCompatActivity implements ListContract.View, CardListAdapter.OnClickListener, View.OnClickListener, View.OnLongClickListener {

    private static final int CREATE_CARD_REQUEST = 1;
    private static final int EDIT_CARD_REQUEST = 2;
    private List<Card> mCardList = new ArrayList<>();
    private CardListAdapter mAdapter;
    private ListContract.Presenter mPresenter = new ListPresenter(this);
    private String mCardName = "";
    private String mNumber = "";
    private String mCvc = "";
    private String mValidity = "";
    private String mName = "";
    private String mType = "";
    private String mPin = "";
    private ConstraintSet constraintSet;
    private int pos;
    private int tp = 0;
    private int tt = 0;
    private String fromFragment;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    @BindView(R.id.fragment_card)
    FrameLayout mCardLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.container)
    ConstraintLayout mLayout;
    @BindView(R.id.view_stub)
    ViewStub mViewStub;
    @BindView(R.id.image_add)
    ImageView mAdd;
    ImageView mBack;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        ButterKnife.bind(this);

        loadCards();

        mAdapter = new CardListAdapter(this, mCardList, this, this);
        constraintSet = new ConstraintSet();
        fragmentManager = getSupportFragmentManager();

        fromFragment = "";

        mCardLayout.setOnTouchListener(new OnSwipeTouchListener(this) {

            public void onSwipeLeft() {

                Objects.requireNonNull(fragment.getView()).setVisibility(View.GONE);

                fromFragment = "";

                setRecyclerViewToTop();
            }

            public void onSwipeRight() {

                fromFragment = "";

                Objects.requireNonNull(fragment.getView()).setVisibility(View.GONE);

                setRecyclerViewToTop();
            }
        });

        initViews();
    }

    @Override
    public void onItemClick(View pView, int pI) {

        getData(mAdapter.getCardAtPos(pI));

        pos = pI;

        if (tt == 1) {

            Objects.requireNonNull(fragment.getView()).setVisibility(View.VISIBLE);
        }
        fragment = fragmentManager.findFragmentById(R.id.fragment_card);

            fragment = FragmentCardFlip.newInstance(mCardName, mNumber, mCvc, mValidity, mName, mType, mPin);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_card, fragment)
                    .commit();

        constraintSet.clone(mLayout);
        constraintSet.connect(R.id.recycler_view, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        constraintSet.connect(R.id.recycler_view, ConstraintSet.BOTTOM, R.id.horizontal_guideline_list_cards, ConstraintSet.BOTTOM, 0);
        constraintSet.applyTo(mLayout);

        fromFragment = "y";
        initViews();

        tt = 1;
    }

    @Override
    protected void onResume() {

        super.onResume();
        loadCards();
    }

    @OnClick({R.id.image_add, R.id.image_back_view_stub, R.id.image_add_view_stub, R.id.image_settings_view_stub})
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {

            case R.id.image_add:
                intent = new Intent(ListActivity.this, CreateActivity.class);
                startActivityForResult(intent, CREATE_CARD_REQUEST);
                break;
            case R.id.image_settings_view_stub:
                intent = new Intent(ListActivity.this, SettingsActivity.class);
                intent.putExtra("key", 1);
                startActivity(intent);
                finish();
                break;
            case R.id.image_add_view_stub:
                intent = new Intent(ListActivity.this, CreateActivity.class);
                startActivityForResult(intent, CREATE_CARD_REQUEST);
                break;
            case R.id.image_back_view_stub:
                mViewStub.setVisibility(View.GONE);
                mAdd.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        assert data != null;
        mAdapter.notifyDataSetChanged();
    }

    public void getData(Card pCard) {

        mCardName = pCard.getCardName();
        mNumber = String.valueOf(pCard.getCardNumber());
        mCvc = String.valueOf(pCard.getCVC());
        mValidity = pCard.getValidity();
        mName = pCard.getCardHolderName();
        mType = pCard.getCardType();
        mPin = String.valueOf(pCard.getPin());
    }

    public void editCard(Card pCard) {

        String request = "2";

        Intent intent = new Intent(ListActivity.this, ActivitySubmitCreditCard.class);
        intent.putExtra("card", pCard);
        intent.putExtra("REQUEST_CODE", request);

        startActivityForResult(intent, EDIT_CARD_REQUEST);
    }

    public Context cont() {

        return getApplicationContext();
    }

    public void deleteCard(Card pCard) {

        mPresenter.deleteCard(pCard);
        setDefaultRecyclerView();
    }

    private void loadCards() {

        mPresenter.loadCards();
    }

    private void setData(List<Card> pCardList) {

        mAdapter.addItems(pCardList);
    }

    @Override
    public void initData(ArrayList<Card> pCards) {

        this.setData(pCards);
    }

    @Override
    public void deleteAll() {

        mPresenter.deleteAll();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initViews() {

        if (fromFragment.equals("")) {


            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(linearLayoutManager);
        } else if (fromFragment.equals("y")) {

            LadderLayoutManager llm = new LadderLayoutManager(0.7f).setChildDecorateHelper(new VerticalSampleChildDecorateHelper(getResources().getDimension(R.dimen.item_max_elevation)));
            llm.setMaxItemLayoutCount(5);
            llm.setChildPeekSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
            mRecyclerView.setLayoutManager(llm);
            if (tp == 0) {
                new LadderSimpleSnapHelper().attachToRecyclerView(mRecyclerView);
            }
            mRecyclerView.setAdapter(mAdapter);
            tp = 1;

            mRecyclerView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {

                @Override
                public void onSwipeLeft() {

                    super.onSwipeLeft();
                    setDefaultRecyclerView();
                }

                @Override
                public void onSwipeRight() {

                    super.onSwipeRight();
                    setDefaultRecyclerView();
                }
            });
        }
    }

    public void fromFragmentData(String data) {

        fromFragment = data;
        initViews();

        Objects.requireNonNull(fragment.getView()).setVisibility(View.GONE);
        setRecyclerViewToTop();
    }

    public int getPos() {

        return pos;
    }

    public Card returnCard() {

         return mAdapter.getCardAtPos(pos);
    }

    private void setDefaultRecyclerView() {

        Objects.requireNonNull(fragment.getView()).setVisibility(View.GONE);
        setRecyclerViewToTop();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter.notifyDataSetChanged();
    }

    private void setRecyclerViewToTop() {

        constraintSet.clone(mLayout);
        constraintSet.connect(R.id.recycler_view, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        constraintSet.connect(R.id.recycler_view, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
        constraintSet.applyTo(mLayout);
    }

    @OnLongClick(R.id.image_add)
    @Override
    public boolean onLongClick(View v) {

        if (mViewStub != null) {

            mBack = findViewById(R.id.image_back_view_stub);
        }
        mAdd.setVisibility(View.INVISIBLE);
        mViewStub.inflate();
        return true;
    }
}
