package com.example.admin.cardpassword.ui.adapters;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.cardpassword.R;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.ui.activity.list.ListActivity;
import com.example.admin.cardpassword.utils.VerticalSampleItemLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    private int[] colors = {0xff03a9f4, 0xff259b24, 0xffff5722, 0xffe51c23, 0xff673ab7};

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

        pViewHolder.bind(mCardList.get(pI), pI);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pViewHolder.mView.setStrokeColor(colors[pI % colors.length]);
        }
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

        @BindView(R.id.text_card_number_element)
        TextView cardNumber;
        @BindView(R.id.text_card_cvc_card_element)
        TextView cvc;
        @BindView(R.id.text_card_validity_element)
        TextView validity;
        @BindView(R.id.text_card_holder_name_element)
        TextView cardHolderName;
        @BindView(R.id.text_card_holder_surname_element)
        TextView cardHolderSurname;
        @BindView(R.id.image_card_element)
        ImageView mImageView;
        @BindView(R.id.content)
        ConstraintLayout mSwipeActionView;
        @BindView(R.id.card_element)
        View mCard;
        @BindView(R.id.container)
        VerticalSampleItemLayout mView;

        ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @SuppressLint("SetTextI18n")
        void bind(Card pCard, int pos) {

//            cardNumber.setText(appendMinus(Long.toString(pCard.getCardNumber())));
            cardNumber.setText(Long.toString(pCard.getCardNumber()));
            cvc.setText(String.valueOf(pCard.getCVC()));
            validity.setText(pCard.getValidity());

            if (pCard.getCardHolderName().equals("") && pCard.getCardHolderSurname().equals("")) {

                cardHolderName.setText("Card");
                cardHolderSurname.setText("Holder");
            } else {

                cardHolderName.setText(pCard.getCardHolderName());
                cardHolderSurname.setText(pCard.getCardHolderSurname());
            }

            if (pCard.getCardType().toLowerCase().equals("visa")) {

                mImageView.setBackgroundResource(R.drawable.visa_rounded);
                setParams();
            } else if (pCard.getCardType().toLowerCase().equals("mastercard")) {

                mImageView.setBackgroundResource(R.drawable.ms_with_border);
                setParams();
            } else if (pCard.getCardType().toLowerCase().equals("belcard")) {

                mImageView.setBackgroundResource(R.drawable.belcard);
                setParams();
            } else if (pCard.getCardType().toLowerCase().equals("maestro")) {

                mImageView.setBackgroundResource(R.drawable.maestro);
                setParams();
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

    private String appendMinus(String pS) {

        String firstSubString = pS.substring(0, 4);
        String secondSubString = pS.substring(4, 8);
        String thirdSubString = pS.substring(8, 12);
        String fourthSubString = pS.substring(12, 16);

        return firstSubString + "-" + secondSubString + "-" + thirdSubString + "-" + fourthSubString;
    }
}
