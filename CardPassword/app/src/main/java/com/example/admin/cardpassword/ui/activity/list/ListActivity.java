package com.example.admin.cardpassword.ui.activity.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.data.AppDataBase;
import com.example.admin.cardpassword.data.DatabaseClient;
import com.example.admin.cardpassword.data.dao.CardDao;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.ui.activity.create.CreateActivity;
import com.example.admin.cardpassword.ui.adapters.CardListAdapter;
import com.example.admin.cardpassword.utils.SwipeController;
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
    private Card mCard;
    private CardDao mDao;
    private ListPresenter mPresenter;
    private AppDataBase mDataBase;
    private ThreadExecutors mExecutors = new ThreadExecutors();
    private ListPresenter.RequestListener mRequestListener;
    private Disposable mDisposable;

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
        registerForContextMenu(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        touchHelper();
    }

    public void touchHelper() {

        SwipeController swipeController = new SwipeController();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
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

    public void deleteAll() {

        mExecutors.dbExecutor().execute(() -> AppDataBase.getDatabase(getApplicationContext()).mCardDao().deleteAll());
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
    }
}
