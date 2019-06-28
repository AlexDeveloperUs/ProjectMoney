package com.flexsoft.cardpassword.ui.activity.list;

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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.flexsoft.cardpassword.R;
import com.flexsoft.cardpassword.data.models.Card;
import com.flexsoft.cardpassword.ui.activity.submit.SubmitCardActivity;
import com.flexsoft.cardpassword.ui.adapters.CardListAdapter;
import com.flexsoft.cardpassword.ui.fragments.fragment1.ControlButtonsFragment;
import com.flexsoft.cardpassword.ui.fragments.fragment2.CardFlipFragment;
import com.flexsoft.cardpassword.utils.LadderLayoutManager;
import com.flexsoft.cardpassword.utils.LadderSimpleSnapHelper;
import com.flexsoft.cardpassword.utils.OnSwipeTouchListener;
import com.flexsoft.cardpassword.utils.VerticalSampleChildDecorateHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.Optional;

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
    private int mPos;
    private int mCheckManager = 0;
    private int mCheckFragment = 0;
    private String mFromFragment;
    private Fragment mFragment;
    private FragmentManager mFragmentManager;
    private Fragment mFrag;

    @BindView(R.id.fragment_card)
    FrameLayout mCardLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.container)
    ConstraintLayout mLayout;
    @BindView(R.id.image_cards_back)
    ImageView mCardsBack;
    @BindView(R.id.text_without_cards)
    TextView mTextWithoutCards;
    @BindView(R.id.image_add)
    ImageView mAdd;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        ButterKnife.bind(this);

        loadCards();

        mAdapter = new CardListAdapter(this, mCardList, this, this);
        constraintSet = new ConstraintSet();
        mFragmentManager = getSupportFragmentManager();

        mFromFragment = "";

        mCardLayout.setOnTouchListener(new OnSwipeTouchListener(this) {

            public void onSwipeLeft() {

                setDefaultManager();
            }

            public void onSwipeRight() {

                setDefaultManager();
            }
        });

        initViews();
    }

    @Override
    public void onItemClick(View pView, int pI) {

        getData(mAdapter.getCardAtPos(pI));

        mPos = pI;

        if (mCheckFragment == 1) {

            Objects.requireNonNull(mFragment.getView()).setVisibility(View.VISIBLE);
        }
        mFragment = mFragmentManager.findFragmentById(R.id.fragment_card);

        mFragment = CardFlipFragment.newInstance(mCardName, mNumber, mCvc, mValidity, mName, mType, mPin);
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_card, mFragment)
                .commit();

        constraintSet.clone(mLayout);
        constraintSet.connect(R.id.recycler_view, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        constraintSet.connect(R.id.recycler_view, ConstraintSet.BOTTOM, R.id.horizontal_guideline_list_cards, ConstraintSet.BOTTOM, 0);
        constraintSet.applyTo(mLayout);

        mFromFragment = "y";
        initViews();

        mCheckFragment = 1;
    }

    @Override
    protected void onResume() {

        super.onResume();
        loadCards();
    }

    @Optional
    @OnClick({R.id.image_add})
    public void onClick(View v) {

        Intent intent;
        if (v.getId() == R.id.image_add) {

            intent = new Intent(ListActivity.this, SubmitCardActivity.class);
            startActivityForResult(intent, CREATE_CARD_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        assert data != null;
        mAdapter.notifyDataSetChanged();
        mPresenter.loadCards();
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

        Intent intent = new Intent(ListActivity.this, SubmitCardActivity.class);
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

    @SuppressLint("ClickableViewAccessibility")
    public void initViews() {

        if (mFromFragment.equals("")) {


            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(linearLayoutManager);
        } else if (mFromFragment.equals("y")) {

            LadderLayoutManager llm = new LadderLayoutManager(0.6f).setChildDecorateHelper(new VerticalSampleChildDecorateHelper(getResources().getDimension(R.dimen.item_max_elevation)));
            llm.setMaxItemLayoutCount(5);
            llm.setChildPeekSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
            mRecyclerView.setLayoutManager(llm);
            if (mCheckManager == 0) {
                new LadderSimpleSnapHelper().attachToRecyclerView(mRecyclerView);
            }
            mRecyclerView.setAdapter(mAdapter);
            mCheckManager = 1;

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

        mFromFragment = data;
        initViews();

        Objects.requireNonNull(mFragment.getView()).setVisibility(View.GONE);
        setRecyclerViewToTop();
    }

    public Card returnCard() {

        return mAdapter.getCardAtPos(mPos);
    }

    private void setDefaultRecyclerView() {

        Objects.requireNonNull(mFragment.getView()).setVisibility(View.GONE);
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

        mFrag = new ControlButtonsFragment();
        mAdd.setVisibility(View.GONE);
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_buttons, mFrag)
                .commit();

        return true;
    }

    public void removeFromFragment() {

        Objects.requireNonNull(mFrag.getView()).setVisibility(View.GONE);
        mAdd.setVisibility(View.VISIBLE);
    }

    public void setForEmptyScreen() {

        mTextWithoutCards.setVisibility(View.VISIBLE);
        mCardsBack.setVisibility(View.VISIBLE);
    }

    public void setForNonEmptyScreen() {

        mTextWithoutCards.setVisibility(View.GONE);
        mCardsBack.setVisibility(View.GONE);
    }

    public void setDefaultManager() {

        Objects.requireNonNull(mFragment.getView()).setVisibility(View.GONE);

        mFromFragment = "";

        setRecyclerViewToTop();
    }
}
