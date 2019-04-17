package com.example.admin.cardpassword.ui.activity.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.data.AppDataBase;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.ui.activity.create.CreateActivity;
import com.example.admin.cardpassword.ui.adapters.CardListAdapter;
import com.example.admin.cardpassword.ui.fragments.fragment1.Fragment1;
import com.example.admin.cardpassword.utils.RecyclerViewSwipeHelper;
import com.example.admin.cardpassword.utils.ThreadExecutors;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

        swipeHelper();
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
            fragment = Fragment1.newInstance(pI, mNumber, mCvc, mValidity, mName, mSurname, mType, mPin); //Fragment1.newInstance(pI);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onLongClickListener(View pView, int pI) {

    }

    public void deleteAll() {

        mExecutors.dbExecutor().execute(() -> AppDataBase.getDatabase(getApplicationContext()).mCardDao().deleteAll());
    }

    @Override
    protected void onResume() {

        super.onResume();
        show();
    }

    @OnClick(R.id.btn_floating_action_add)
    public void onClick(View v) {

        Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
        startActivityForResult(intent, CREATE_CARD_REQUEST);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        mAdapter.clearItems();
        mAdapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        assert data != null;
        mAdapter.notifyDataSetChanged();
    }

    private void deleteSingleItem(Card pCard) {

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

    public void swipeHelper() {

        RecyclerViewSwipeHelper swipeHelper = new RecyclerViewSwipeHelper(ListActivity.this, mRecyclerView) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new RecyclerViewSwipeHelper.UnderlayButton(
                        android.R.drawable.ic_menu_delete,
                        Color.parseColor("#FF3C30"),
                        pos -> {

                            deleteSingleItem(mAdapter.getCardAtPos(pos));
                            mAdapter.notifyItemRemoved(pos);
                        }
                ));

                underlayButtons.add(new RecyclerViewSwipeHelper.UnderlayButton(
                        android.R.drawable.ic_menu_edit,
                        Color.parseColor("#649b11"),
                        pos -> {

                            editCard(mAdapter.getCardAtPos(pos));
                            mAdapter.notifyItemChanged(pos);
                        }
                ));
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeHelper);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void editCard(Card pCard) {

        String cardNumber = pCard.getCardNumber() + "";
        String cardCvc = pCard.getCVC() + "";
        String cardValidity = pCard.getValidity();
        String cardHoldersName = pCard.getCardHolderName();
        String cardHoldersSurname = pCard.getCardHolderSurname();
        String cardType = pCard.getCardType();
        String cardPin = pCard.getPin() + "";
        String rec = "2";

        Intent intent = new Intent(ListActivity.this, CreateActivity.class);
        intent.putExtra("number", cardNumber);
        intent.putExtra("cvc", cardCvc);
        intent.putExtra("validity", cardValidity);
        intent.putExtra("name", cardHoldersName);
        intent.putExtra("surname", cardHoldersSurname);
        intent.putExtra("type", cardType);
        intent.putExtra("pin", cardPin);
        intent.putExtra("REQUEST_CODE", rec);

        startActivityForResult(intent, EDIT_CARD_REQUEST);
    }

    public Context cont() {

        return getApplicationContext();
    }
}
