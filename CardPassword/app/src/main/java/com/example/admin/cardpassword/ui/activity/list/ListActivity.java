package com.example.admin.cardpassword.ui.activity.list;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.admin.cardpassword.data.dao.CardDao;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.ui.activity.create.CreateActivity;
import com.example.admin.cardpassword.ui.adapters.CardListAdapter;
import com.example.admin.cardpassword.utils.RecyclerViewSwipeHelper;
import com.example.admin.cardpassword.utils.SwipeController;
import com.example.admin.cardpassword.utils.SwipeControllerActions;
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
    private SwipeController mController = null;

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

        mm();
    }

    public void touchHelper() {

        mController = new SwipeController(new SwipeControllerActions() {

            @Override
            public void onRightClicked(int position) {

                Intent intent = new Intent(ListActivity.this, CreateActivity.class);
                existenceCardCheck();
                startActivityForResult(intent, EDIT_CARD_REQUEST);
                mAdapter.notifyDataSetChanged();
                super.onRightClicked(position);
            }

            @Override
            public void onLeftClicked(int position) {

                mAdapter.deleteItem(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mController);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            @Override
            public void onDraw(@NonNull Canvas pC, @NonNull RecyclerView pParent, @NonNull RecyclerView.State pState) {
//onDraw(pC, pParent, pState) -> mController.onDraw(pC)
                mController.onDraw(pC);
            }
        });
    }

    private void existenceCardCheck() {

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

    public void deleteSingleItem() {

//        mExecutors.dbExecutor().execute(() -> AppDataBase.getDatabase(getApplicationContext()).mCardDao()
//        .delete());
    }

    public void mm() {

        RecyclerViewSwipeHelper swipeHelper = new RecyclerViewSwipeHelper(this, mRecyclerView) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new RecyclerViewSwipeHelper.UnderlayButton("Delete",
                        android.R.drawable.ic_menu_delete,
                        Color.parseColor("#FF3C30"),
                        new RecyclerViewSwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: onDelete
                                int a = 0;
                                a=10;
                            }
                        }
                ));

                underlayButtons.add(new RecyclerViewSwipeHelper.UnderlayButton("Edit",
                        android.R.drawable.ic_menu_edit,
                        Color.parseColor("#FF9502"),
                        new RecyclerViewSwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: OnTransfer
                                int a = 0;
                                a=10;
                            }
                        }
                ));
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeHelper);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }
}
