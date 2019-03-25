package com.example.admin.cardpassword.ui.activity.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.admin.cardpassword.App;
import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.ui.adapters.CardListAdapter;
import com.example.admin.cardpassword.data.AppDataBase;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.data.dao.CardDao;
import com.example.admin.cardpassword.ui.activity.create.CreateActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity implements ListContract.View, CardListAdapter.OnClickListener, View.OnClickListener {

    public static final int CREATE_CARD_REQUEST = 1;
    public static final int EDIT_CARD_REQUEST = 2;
    private CardListAdapter mAdapter;
    private List<Card> mCardList = new ArrayList<>();
    Card mCard;
    AppDataBase mDataBase;
    private ListPresenter mPresenter;

    LinearLayoutManager mLinearLayoutManager;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_floating_action_add)
    Button mButtonAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        ButterKnife.bind(this);

        mButtonAdd.setOnClickListener(this);
        initRecyclerView();
    }

    public void initRecyclerView() {

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new CardListAdapter(this, mCardList, this);
        mRecyclerView.setAdapter(mAdapter);


        mDataBase = App.getmInstance().getDataBase();
        CardDao cardDao = mDataBase.mCardDao();

        Card card = new Card();

        registerForContextMenu(mRecyclerView);
    }

    @Override
    public void blur() {

    }

    @Override
    public void showCardList() {

        mDataBase.mCardDao().getAll();
    }

    @Override
    public void onItemClick(View pView, int pI) {


    }

    public void registerForContextMenu(View pView) {

        pView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onLongClickListener(View pView, int pI) {


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
        mAdapter = new CardListAdapter(this, mCardList, this);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, CreateActivity.class);
        startActivityForResult(intent, CREATE_CARD_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }
}
