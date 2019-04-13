package com.example.admin.cardpassword.utils;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class SwipeController extends ItemTouchHelper.Callback {


    @Override
    public int getMovementFlags(@NonNull RecyclerView pRecyclerView, @NonNull RecyclerView.ViewHolder pViewHolder) {

        return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView pRecyclerView, @NonNull RecyclerView.ViewHolder pViewHolder, @NonNull RecyclerView.ViewHolder pViewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder pViewHolder, int pI) {

    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {

        if (swipeBack) {

            swipeBack = false;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (actionState == ACTION_STATE_SWAP) {

            setTouchListener
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
