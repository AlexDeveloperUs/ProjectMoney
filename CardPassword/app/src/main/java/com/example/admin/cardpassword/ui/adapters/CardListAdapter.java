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
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.ui.activity.list.ListActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.thanel.swipeactionview.SwipeActionView;
import me.thanel.swipeactionview.SwipeGestureListener;

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

        View view = mInflater.inflate(R.layout.recycler_element_visa, pViewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder pViewHolder, int pI) {

        pViewHolder.bind(mCardList.get(pI), pI);
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
        @BindView(R.id.swipe_view)
        SwipeActionView getmSwipeAction;

        ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(Card pCard, int pos) {

            SwipeGestureListener swipeGestureListener = new SwipeGestureListener() {
                @Override
                public boolean onSwipedLeft(@NonNull SwipeActionView swipeActionView) {

                    mListActivity.editCard(getCardAtPos(pos));
                    notifyItemChanged(pos);
                    return true;
                }

                @Override
                public boolean onSwipedRight(@NonNull SwipeActionView swipeActionView) {

                    mListActivity.deleteSingleItem(getCardAtPos(pos));
                    notifyItemRemoved(pos);
                    return true;
                }
            };

            getmSwipeAction.setSwipeGestureListener(swipeGestureListener);

            String num = Long.toString(pCard.getCardNumber());
            String cvC = Short.toString(pCard.getCVC());
            String validation = pCard.getValidity();
            cardNumber.setText(num);
            cvc.setText(cvC);
            validity.setText(validation);
            cardHolderName.setText(pCard.getCardHolderName());
            cardHolderSurname.setText(pCard.getCardHolderSurname());

            float density = mListActivity.cont().getResources().getDisplayMetrics().density;
            if (pCard.getCardType().toLowerCase().equals("visa")) {

                mImageView.setBackgroundResource(R.drawable.visa);
                mImageView.getLayoutParams().height = (int) (density * 40);
                mImageView.getLayoutParams().width = (int) (density * 70);
            } else if (pCard.getCardType().toLowerCase().equals("mastercard")) {

                mImageView.setBackgroundResource(R.drawable.master_card);
                mImageView.getLayoutParams().height = (int) (density * 50);
                mImageView.getLayoutParams().width = (int) (density * 70);
            }
        }

        @Override
        public void onClick(View v) {

            mOnClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public interface OnClickListener {

        void onItemClick(View pView, int pI);
    }
}
