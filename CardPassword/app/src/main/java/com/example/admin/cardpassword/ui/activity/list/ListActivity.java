package com.example.admin.cardpassword.ui.activity.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.ThreadExecutors;
import com.example.admin.cardpassword.data.DatabaseClient;
import com.example.admin.cardpassword.data.models.CardViewModel;
import com.example.admin.cardpassword.ui.adapters.CardListAdapter;
import com.example.admin.cardpassword.App;
import com.example.admin.cardpassword.data.AppDataBase;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.data.dao.CardDao;
import com.example.admin.cardpassword.ui.activity.create.CreateActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity implements ListContract.View, CardListAdapter.OnClickListener, View.OnClickListener {

    private static final int CREATE_CARD_REQUEST = 1;
    private static final int EDIT_CARD_REQUEST = 2;
    private CardListAdapter mAdapter;
    private List<Card> mCardList = new ArrayList<>();
    private CardViewModel mViewModel;
    private Card mCard;
    private CardDao mDao;
    private ListPresenter mPresenter;
    private ThreadExecutors mExecutor;

    LinearLayoutManager mLinearLayoutManager;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_floating_action_add)
    FloatingActionButton mButtonAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        ButterKnife.bind(this);

        initRecyclerView();

        mViewModel = ViewModelProviders.of(this).get(CardViewModel.class);

        mButtonAdd.setOnClickListener(this);
    }

    public void initRecyclerView() {

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new CardListAdapter(this, mCardList, this, this);
        registerForContextMenu(mRecyclerView);

        AppDataBase dataBase = App.getmInstance().getDataBase();
        CardDao cardDao = dataBase.mCardDao();
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

    }

    @Override
    public void onLongClickListener(View pView, int pI) {

    }

    private void deleteAll() {

//        class DeleteCardsAsyncTask extends AsyncTask<Void, Void, Void> {
//
//            private CardDao mCardDao;
//
//            DeleteCardsAsyncTask(CardDao pCardDao) {
//
//                mCardDao = pCardDao;
//            }
//
//            @Override
//            protected Void doInBackground(Void... pVoids) {
//
//                mCardDao.deleteAll();
//                return null;
//            }
//        }
//
//        DeleteCardsAsyncTask deleteCardsAsyncTask = new DeleteCardsAsyncTask(mDao);
//        deleteCardsAsyncTask.execute();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_item_delete_card:
                mAdapter.deleteItem();
                return true;
            case R.id.menu_item_edit_card:
                Intent intent = new Intent(this, CreateActivity.class);
                startActivityForResult(intent, EDIT_CARD_REQUEST);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        getCards();
        mAdapter = new CardListAdapter(this, mCardList, this, this);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        deleteAll();

//        Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
//        startActivityForResult(intent, CREATE_CARD_REQUEST);
    }

    private void getCards() {

//        class GetCards extends AsyncTask<Void, Void, List<Card>> {
//
//
//            @Override
//            protected List<Card> doInBackground(Void... pVoids) {
//
//                List<Card> cardList = DatabaseClient.getmInstance(getApplicationContext())
//                        .getAppDataBase()
//                        .mCardDao()
//                        .getAll();
//
//                return cardList;
//            }
//
//            @Override
//            protected void onPostExecute(List<Card> pCards) {
//
//                super.onPostExecute(pCards);
//                CardListAdapter adapter = new CardListAdapter(ListActivity.this, pCards, ListActivity.this, ListActivity.this);
//                mRecyclerView.setAdapter(adapter);
//            }
//        }
//
//        GetCards getCards = new GetCards();
//        getCards.execute();

        mExecutor.getDbThreadExecutor().execute(() -> {

            LiveData<List<Card>> cards = DatabaseClient.getmInstance(getApplicationContext())
                    .getAppDataBase()
                    .mCardDao()
                    .getAll();
            runOnUiThread(() -> {

                CardListAdapter adapter = new CardListAdapter(this, (List<Card>) cards, this, this);
                mRecyclerView.setAdapter(adapter);
            });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        assert data != null;
        mAdapter.addItem();
    }
}
