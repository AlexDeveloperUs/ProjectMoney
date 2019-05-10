package com.example.admin.cardpassword.ui.activity.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.ui.activity.create.CreateActivity;
import com.example.admin.cardpassword.ui.activity.settings.SettingsActivity;
import com.example.admin.cardpassword.ui.adapters.CardListAdapter;
import com.example.admin.cardpassword.ui.fragments.fragment1.Frag;
import com.example.admin.cardpassword.ui.fragments.fragment1.Fragment1;
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
import butterknife.Optional;

public class ListActivity extends AppCompatActivity implements ListContract.View, CardListAdapter.OnClickListener, View.OnClickListener {

    private static final int CREATE_CARD_REQUEST = 1;
    private static final int EDIT_CARD_REQUEST = 2;
    private List<Card> mCardList = new ArrayList<>();
    private CardListAdapter mAdapter;
    private ListContract.Presenter mPresenter = new ListPresenter(this);
    private String mNumber = "";
    private String mCvc = "";
    private String mValidity = "";
    private String mName = "";
    private String mSurname = "";
    private String mType = "";
    private String mPin = "";

    ConstraintSet constraintSet;

    @BindView(R.id.fragment_card)
    FrameLayout mCardLayout;

    public static boolean check;
    int tmp = 0;
    int tp = 0;
    int tt = 0;
    String fromFragment;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.container)
    ConstraintLayout mLayout;
    //    @BindView(R.id.bottom_app_bar)
//    BottomAppBar mBottomAppBar;
    Fragment fragment;
    FragmentManager fragmentManager;
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

        check = true;

        fromFragment = "";


        mCardLayout.setOnTouchListener(new OnSwipeTouchListener(this) {

            public void onSwipeTop() {

                show();
                Objects.requireNonNull(fragment.getView()).setVisibility(View.GONE);

                check = true;
                fromFragment = "";

                constraintSet.clone(mLayout);
                constraintSet.connect(R.id.recycler_view, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
                constraintSet.applyTo(mLayout);

                tmp = 0;

//                initViews();
            }

            public void onSwipeBottom() {

                Objects.requireNonNull(fragment.getView()).setVisibility(View.GONE);

                check = true;

                constraintSet.clone(mLayout);
                constraintSet.connect(R.id.recycler_view, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
                constraintSet.applyTo(mLayout);

                tmp = 0;

//                initViews();
            }
        });

        initViews();
    }

    @Override
    public void onItemClick(View pView, int pI) {

        getData(mAdapter.getCardAtPos(pI));

        if (tt == 1) {

            Objects.requireNonNull(fragment.getView()).setVisibility(View.VISIBLE);
        }
        fragment = fragmentManager.findFragmentById(R.id.fragment_card);


            fragment = Frag.newInstance(mNumber, mCvc, mValidity, mName, mSurname, mType, mPin);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_card, fragment)
                    .commit();

        constraintSet.clone(mLayout);
        constraintSet.connect(R.id.recycler_view, ConstraintSet.TOP, R.id.loh, ConstraintSet.TOP, 0);
        constraintSet.applyTo(mLayout);

        check = false;
        fromFragment = "y";
        tmp = 1;
        initViews();

        tt = 1;
    }

    @Override
    protected void onResume() {

        super.onResume();
        loadCards();
    }

//    @Optional
    @OnClick({})
    public void onClick(View v) {

        if (tmp == 1) {

            Objects.requireNonNull(fragment.getView()).setVisibility(View.GONE);

            check = true;

            constraintSet.clone(mLayout);
            constraintSet.connect(R.id.recycler_view, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
            constraintSet.applyTo(mLayout);

            tmp = 0;

//            initViews();
        } else if (tmp == 0) {

            if (tt == 1) {

                Objects.requireNonNull(fragment.getView()).setVisibility(View.VISIBLE);
            }

            fragment = fragmentManager.findFragmentById(R.id.fragment_card);
            if (fragment == null) {

                fragment = new Frag();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_card, fragment)
                        .commit();
            }
                constraintSet.clone(mLayout);
                constraintSet.connect(R.id.recycler_view, ConstraintSet.TOP, R.id.loh, ConstraintSet.TOP, 0);
                constraintSet.applyTo(mLayout);

                check = false;
                tmp = 1;
//                initViews();

            tt = 1;
        }

//
//        Intent intent;
//        switch (v.getId()) {
//
//            case R.id.fab:
//                intent = new Intent(ListActivity.this, CreateActivity.class);
//                startActivityForResult(intent, CREATE_CARD_REQUEST);
//                break;
//            case R.id.image_view_settings:
//                intent = new Intent(ListActivity.this, SettingsActivity.class);
//                intent.putExtra("key", 1);
//                startActivity(intent);
//                finish();
//                break;
//            case R.id.image_view_cards:
//                break;
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        assert data != null;
        mAdapter.notifyDataSetChanged();
    }

    public void getData(Card pCard) {

        mNumber = String.valueOf(pCard.getCardNumber());
        mCvc = String.valueOf(pCard.getCVC());
        mValidity = pCard.getValidity();
        mName = pCard.getCardHolderName();
        mSurname = pCard.getCardHolderSurname();
        mType = pCard.getCardType();
        mPin = String.valueOf(pCard.getPin());
    }

    public void editCard(Card pCard) {

        String request = "2";

        Intent intent = new Intent(ListActivity.this, CreateActivity.class);
        intent.putExtra("card", pCard);
        intent.putExtra("REQUEST_CODE", request);

        startActivityForResult(intent, EDIT_CARD_REQUEST);
    }

    public Context cont() {

        return getApplicationContext();
    }

    public void deleteCard(Card pCard) {

        mPresenter.deleteCard(pCard);
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

    public void initViews() {

        if (fromFragment.equals("")) {


            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mAdapter.notifyDataSetChanged();
        } else if (fromFragment.equals("y")) {

            LadderLayoutManager llm = new LadderLayoutManager(0.7f).setChildDecorateHelper(new VerticalSampleChildDecorateHelper(getResources().getDimension(R.dimen.item_max_elevation)));
            llm.setMaxItemLayoutCount(10);
            llm.setChildPeekSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 90, getResources().getDisplayMetrics()));
            mRecyclerView.setLayoutManager(llm);
            if (tp == 0) {
                new LadderSimpleSnapHelper().attachToRecyclerView(mRecyclerView);
            }
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            tp = 1;

            mRecyclerView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {

                @Override
                public void onSwipeTop() {

                    super.onSwipeTop();
                    Objects.requireNonNull(fragment.getView()).setVisibility(View.GONE);
                    constraintSet.clone(mLayout);
                    constraintSet.connect(R.id.recycler_view, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
                    constraintSet.applyTo(mLayout);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    public void show() {

        Toast.makeText(getApplicationContext(), "Lol", Toast.LENGTH_LONG).show();
    }

    public void fromFragmentData(String data) {

        fromFragment = data;
        initViews();

        Objects.requireNonNull(fragment.getView()).setVisibility(View.GONE);
        constraintSet.clone(mLayout);
        constraintSet.connect(R.id.recycler_view, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        constraintSet.applyTo(mLayout);
    }
}
