package com.flexsoft.cardpassword.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flexsoft.cardpassword.R;
import com.flexsoft.cardpassword.data.models.Card;
import com.flexsoft.cardpassword.ui.activity.list.ListActivity;
import com.flexsoft.cardpassword.utils.VerticalSampleItemLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    private List<Card> mCardList;
    private final LayoutInflater mInflater;
    private ListActivity mListActivity;
    private OnClickListener mOnClickListener;

    public CardListAdapter(Context pContext, List<Card> pCardList, OnClickListener pOnClickListener, ListActivity pListActivity) {

        mListActivity = pListActivity;
        mCardList = pCardList;
        mOnClickListener = pOnClickListener;
        mInflater = LayoutInflater.from(pContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup pViewGroup, int pI) {

        View view = mInflater.inflate(R.layout.recycler_element, pViewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder pViewHolder, int pI) {

        pViewHolder.bind(mCardList.get(pI));
    }

    public Card getCardAtPos(int pPosition) {

        return mCardList.get(pPosition);
    }

    public void addItems(List<Card> pCards) {

        mCardList = pCards;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        if (mCardList != null) {

            return mCardList.size();
        } else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_card_name_recycler)
        TextView mCardName;
        @BindView(R.id.text_card_number_recycler)
        TextView cardNumber;
        @BindView(R.id.text_cvv_code_recycler)
        TextView cvc;
        @BindView(R.id.text_expired_date_recycler)
        TextView validity;
        @BindView(R.id.text_card_holder_recycler)
        TextView cardHolderName;
        @BindView(R.id.text_card_holder_surname_element)
        TextView cardHolderSurname;
        @BindView(R.id.text_pin_recycler)
        TextView pin;
        @BindView(R.id.image_card_element_recycler)
        ImageView mImageView;
        @BindView(R.id.card_element)
        View mCard;
        @BindView(R.id.container)
        VerticalSampleItemLayout mView;
        @BindView(R.id.card_constraint_recycler)
        ConstraintLayout mBack;

        private Context mContext;

        ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            mContext = itemView.getContext();
        }

        @SuppressLint("SetTextI18n")
        void bind(Card pCard) {

            Drawable background;

            cardNumber.setText(appendVoid(Long.toString(pCard.getCardNumber())));
            validity.setText(pCard.getValidity());

            if (pCard.getCardName().equals("")) {

                mCardName.setText("Card name");
            } else mCardName.setText(pCard.getCardName());

            String cvcData = String.valueOf(pCard.getCVC());

            if (cvcData.length() == 3) {

                cvc.setText("*** ");
            } else if (cvcData.length() == 4) {

                cvc.setText("**** ");
            }

            if (pCard.getCardHolderName().equals("")) {

                cardHolderName.setText(R.string.hint_card_holder);
            } else {

                cardHolderName.setText(pCard.getCardHolderName());
                cardHolderSurname.setText(pCard.getCardHolderSurname());
            }

            if (pCard.getCardType().toLowerCase().equals("visa")) {

                mImageView.setBackgroundResource(R.drawable.ic_visa);
                setParams();
                background = mContext.getResources().getDrawable(R.drawable.visa_gradient);
                mBack.setBackground(background);
            } else if (pCard.getCardType().toLowerCase().equals("mastercard")) {

                mImageView.setBackgroundResource(R.drawable.ic_mastercard);
                setParams();
                background = mContext.getResources().getDrawable(R.drawable.red_gradient);
                mBack.setBackground(background);
            } else if (pCard.getCardType().toLowerCase().equals("belcard")) {

                mImageView.setBackgroundResource(R.drawable.belcard);
                setParams();
                background = mContext.getResources().getDrawable(R.drawable.belcard_gradient);
                mBack.setBackground(background);
            } else if (pCard.getCardType().toLowerCase().equals("maestro")) {

                mImageView.setBackgroundResource(R.drawable.ic_maestro);
                setParams();
                background = mContext.getResources().getDrawable(R.drawable.blue_gradient);
                mBack.setBackground(background);
            }
        }

        private void setParams() {

            float density = mListActivity.cont().getResources().getDisplayMetrics().density;

            mImageView.getLayoutParams().height = (int) (density * 50);
            mImageView.getLayoutParams().width = (int) (density * 50);
        }

        @Override
        public void onClick(View v) {

            mOnClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public interface OnClickListener {

        void onItemClick(View pView, int pI);
    }

    private String appendVoid(String pS) {

        String firstSubString = pS.substring(0, 4);
        String secondSubString = pS.substring(4, 8);
        String thirdSubString = pS.substring(8, 12);

        return firstSubString + " " + secondSubString + " " + thirdSubString + " " + "****";
    }
}

class asd<K, V> {

    private K mKey;
    private V mValue;

    asd(K pKey, V pValue) {
        mKey = pKey;
        mValue = pValue;
    }

    public K getKey() {
        return mKey;
    }

    public void setKey(K pKey) {
        mKey = pKey;
    }

    public V getValue() {
        return mValue;
    }

    public void setValue(V pValue) {
        mValue = pValue;
    }
}

class ch {
    public static void main(String[] args) {
        asd<String, Integer> tuple = new asd<>("name", 12);

        System.out.println(tuple.getKey());

        System.out.println(tuple.getValue());

        ThreeTuple<String, String, Integer> newTuple = new ThreeTuple<>("First", "Second", 1);

        System.out.println(newTuple.getFirst());
        System.out.println(newTuple.getThird());
        System.out.println(newTuple.getSecond());
    }
}

class ThreeTuple<A, B, C> extends asd<A, B> {
    private C mC;

    ThreeTuple(A pA, B pB, C pC) {
        super(pA, pB);
        mC = pC;
    }

    public A getFirst() {
        return super.getKey();
    }

    public void setFirst(A pFirst) {
        super.setKey(pFirst);
    }

    public B getSecond() {
        return super.getValue();
    }

    public void setSecond(B pSecond) {
        super.setValue(pSecond);
    }

    public C getThird() {
        return mC;
    }

    public void setThird(C pThird) {
        mC = pThird;
    }
}
