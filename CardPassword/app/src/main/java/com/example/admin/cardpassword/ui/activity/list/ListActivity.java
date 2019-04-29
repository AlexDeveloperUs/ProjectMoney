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
import com.example.admin.cardpassword.data.AppDataBase;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.ui.activity.create.CreateActivity;
import com.example.admin.cardpassword.ui.activity.settings.SettingsActivity;
import com.example.admin.cardpassword.ui.adapters.CardListAdapter;
import com.example.admin.cardpassword.ui.fragments.fragment1.Fragment1;
import com.example.admin.cardpassword.utils.ThreadExecutors;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ListActivity extends AppCompatActivity implements ListContract.View, CardListAdapter.OnClickListener, View.OnClickListener {

    private static final int CREATE_CARD_REQUEST = 1;
    private static final int EDIT_CARD_REQUEST = 2;
    private CardListAdapter mAdapter;
    private List<Card> mCardList = new ArrayList<>();
    private ThreadExecutors mExecutors = new ThreadExecutors();
    private ListPresenter.RequestListener mRequestListener;
    private Disposable mDisposable;
    private String mNumber = "";
    private String mCvc = "";
    private String mValidity = "";
    private String mName = "";
    private String mSurname = "";
    private String mType = "";
    private String mPin = "";

    LinearLayoutManager mLinearLayoutManager;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.bottom_app_bar)
    BottomAppBar mBottomAppBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        ButterKnife.bind(this);

        initRecyclerView();
    }

    private void show() {

        mExecutors.dbExecutor().execute(() -> {

            try {

                mDisposable = AppDataBase.getDatabase(getApplicationContext()).mCardDao().getAll()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(pCards -> mAdapter.addItems(pCards));
            } catch (Exception pE) {

                mRequestListener.onFailed(pE);
            }
        });
    }

    public void initRecyclerView() {

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new CardListAdapter(this, mCardList, this, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void blur() {

    }

    @Override
    public void showCardList() {

    }

    @Override
    public void checkIdExistence(int pI) {

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
        show();
    }

    @Optional
    @OnClick({R.id.fab, R.id.image_view_cards, R.id.image_view_settings})
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {

            case R.id.fab:
                intent = new Intent(getApplicationContext(), CreateActivity.class);
                startActivityForResult(intent, CREATE_CARD_REQUEST);
                break;
            case R.id.image_view_cards:
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

    public void deleteSingleItem(Card pCard) {

        mExecutors.dbExecutor().execute(() -> AppDataBase.getDatabase(getApplicationContext()).mCardDao()
                .delete(pCard));
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

        String rec = "2";

        Intent intent = new Intent(ListActivity.this, CreateActivity.class);
        intent.putExtra("card", pCard);
        intent.putExtra("REQUEST_CODE", rec);

        startActivityForResult(intent, EDIT_CARD_REQUEST);
    }

    public Context cont() {

        return getApplicationContext();
    }
}
