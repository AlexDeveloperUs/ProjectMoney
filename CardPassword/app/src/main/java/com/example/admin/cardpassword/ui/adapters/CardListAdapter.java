package com.example.admin.cardpassword.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.data.dao.CardDao;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.ui.activity.list.ListActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    private List<Card> mCardList;
    private final LayoutInflater mInflater;
    private ListActivity mListActivity;
    private OnClickListener mOnClickListener;
    private Card mCard;
    private CardDao mCardDao;
    private int mPosition;

    public CardListAdapter(Context pContext, List<Card> pCardList, OnClickListener pOnClickListener, ListActivity pListActivity) {

        mListActivity = pListActivity;
        mCardList = pCardList;
        mOnClickListener = pOnClickListener;
        mInflater = LayoutInflater.from(pContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup pViewGroup, int pI) {

        View view = mInflater.inflate(R.layout.recycler_element_visa, pViewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder pViewHolder, int pI) {

        if (mCardList != null) {
            pViewHolder.bind(mCardList.get(pI));
            mPosition = pViewHolder.getAdapterPosition();
        }
    }

    void setCardList(List<Card> pCards) {

        mCardList = pCards;
        notifyDataSetChanged();
    }

    public void clearItems() {

        mCardList.clear();
        notifyDataSetChanged();
    }

    public void deleteItem() {

        mCardList.remove(mPosition);
        notifyItemRemoved(mPosition);
    }

    public void addItem() {

        mCardList.add(mCardDao.getById(mPosition));
        notifyDataSetChanged();
    }

    public void editItem() {


    }

    @Override
    public int getItemCount() {

        if (mCardList != null) {

            return mCardList.size();
        } else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        @BindView(R.id.text_card_number_element)
        TextView cardNumber;
        @BindView(R.id.text_card_cvc_element)
        TextView cvc;
        @BindView(R.id.text_card_validity_element)
        TextView validity;
        @BindView(R.id.text_card_holder_name_element)
        TextView cardHolderName;
        @BindView(R.id.text_card_holder_surname_element)
        TextView cardHolderSurname;
        @BindView(R.id.image_element_visa)
        ImageView mImageView;

        ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        void bind(Card pCard) {

            String num = Long.toString(pCard.getCardNumber());
            String cvC = Short.toString(pCard.getCVC());
            String valitadion = Short.toString(pCard.getValidity());
            cardNumber.setText(num);
            cvc.setText(cvC);
            validity.setText(valitadion);
            cardHolderName.setText(pCard.getCardHolderName());
            cardHolderSurname.setText(pCard.getCardHolderSurname());

            if (pCard.getCardType().toLowerCase().equals("visa")) {

                mImageView.setBackgroundResource(R.drawable.visa);
            } else if (pCard.getCardType().toLowerCase().equals("mastercard")) {

                mImageView.setBackgroundResource(R.drawable.master_card);
            }
        }

        @Override
        public void onClick(View v) {

            mOnClickListener.onItemClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {

            mOnClickListener.onLongClickListener(v, getAdapterPosition());
            return false;
        }
    }

    public interface OnClickListener {

        void onItemClick(View pView, int pI);

        void onLongClickListener(View pView, int pI);
    }
}
