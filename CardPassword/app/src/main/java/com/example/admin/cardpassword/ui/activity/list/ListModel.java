package com.example.admin.cardpassword.ui.activity.list;

import com.example.admin.cardpassword.App;
import com.example.admin.cardpassword.data.AppDataBase;
import com.example.admin.cardpassword.data.dao.CardDao;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.utils.ThreadExecutors;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ListModel implements ListContract.Model {

    private CardDao mCardDao;
    private ThreadExecutors mExecutors;
    private ListContract.Presenter.RequestListener mListener;
    private Disposable mDisposable;

    ListModel() {

        mExecutors = new ThreadExecutors();
        AppDataBase dataBase = App.getmInstance().getDataBase();
        mCardDao = dataBase.mCardDao();
    }

    @Override
    public void getCards() {

        mExecutors.dbExecutor().execute(() -> {

            try {

                mDisposable = mCardDao.getAll()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(pCards -> mListener.onSuccess((ArrayList<Card>) pCards));
            } catch (Exception pE) {

                mListener.onFailed(pE);
            }
        });
    }

    @Override
    public void deleteCard(Card pCard) {

        mExecutors.dbExecutor().execute(() -> mCardDao.delete(pCard));
    }

    @Override
    public void deleteAll() {

        mExecutors.dbExecutor().execute(() -> mCardDao.deleteAll());
    }

    void addRequestListener(ListContract.Presenter.RequestListener pListener) {

        mListener = pListener;
    }
}
