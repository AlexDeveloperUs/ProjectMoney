package com.example.admin.cardpassword.ui.activity.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.ui.activity.create.CreateActivity;
import com.example.admin.cardpassword.ui.activity.settings.SettingsActivity;
import com.example.admin.cardpassword.ui.adapters.CardListAdapter;
import com.example.admin.cardpassword.ui.fragments.fragment1.Fragment1;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class ListActivity extends AppCompatActivity implements ListContract.View, CardListAdapter.OnClickListener, View.OnClickListener {

    private static final int CREATE_CARD_REQUEST = 1;
    private static final int EDIT_CARD_REQUEST = 2;
    private CardListAdapter mAdapter;
    private List<Card> mCardList = new ArrayList<>();
    private ListContract.Presenter mPresenter = new ListPresenter(this);
    private String mNumber = "";
    private String mCvc = "";
    private String mValidity = "";
    private String mName = "";
    private String mSurname = "";
    private String mType = "";
    private String mPin = "";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.bottom_app_bar)
    BottomAppBar mBottomAppBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        ButterKnife.bind(this);

        initViews();
        loadCards();
    }

    @Override
    public void onItemClick(View pView, int pI) {

        getData(mAdapter.getCardAtPos(pI));
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {

            fragment = Fragment1.newInstance(pI, mNumber, mCvc, mValidity, mName, mSurname, mType, mPin);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        loadCards();
    }

    @Optional
    @OnClick({R.id.fab, R.id.image_view_settings})
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {

            case R.id.fab:
                intent = new Intent(ListActivity.this, CreateActivity.class);
                startActivityForResult(intent, CREATE_CARD_REQUEST);
                break;
            case R.id.image_view_settings:
                intent = new Intent(ListActivity.this, SettingsActivity.class);
                intent.putExtra("key", 1);
                startActivity(intent);
                finish();
                break;
        }
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

        mAdapter = new CardListAdapter(this, mCardList, this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }
}
