package com.example.admin.cardpassword.utils;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE;

enum ButtonsState {

    GONE,
    LEFT_VISIBLE,
    RIGHT_VISIBLE
}

public class SwipeController extends ItemTouchHelper.Callback {

    private boolean swipeBack = false;

    private ButtonsState buttonShowedState = ButtonsState.GONE;

    private RectF buttonInstance = null;

    private RecyclerView.ViewHolder currentItemViewHolder = null;

    private SwipeControllerActions buttonsActions;

    private static final float buttonWidth = 400;

    public SwipeController(SwipeControllerActions buttonsActions) {

        this.buttonsActions = buttonsActions;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView pRecyclerView, @NonNull RecyclerView.ViewHolder pViewHolder) {

        return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView pRecyclerView, @NonNull RecyclerView.ViewHolder pViewHolder, @NonNull RecyclerView.ViewHolder pTarget) {

        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder pViewHolder, int pI) {

    }

    @Override
    public int convertToAbsoluteDirection(int pFlags, int pLayoutDirection) {

        if (swipeBack) {

            swipeBack = buttonShowedState != ButtonsState.GONE;
            return 0;
        }
        return super.convertToAbsoluteDirection(pFlags, pLayoutDirection);
    }

    @Override
    public void onChildDraw(@NonNull Canvas pC, @NonNull RecyclerView pRecyclerView, @NonNull RecyclerView.ViewHolder pViewHolder,
                            float pDX, float pDY, int pActionState, boolean pIsCurrentlyActive) {

        if (pActionState == ACTION_STATE_SWIPE) {

            if (buttonShowedState != ButtonsState.GONE) {

                if (buttonShowedState == ButtonsState.LEFT_VISIBLE) pDX = Math.max(pDX, buttonWidth);
                if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) pDX = Math.min(pDX, -buttonWidth);
                super.onChildDraw(pC, pRecyclerView, pViewHolder, pDX, pDY, pActionState, pIsCurrentlyActive);
            } else {

                setTouchListener(pC, pRecyclerView, pViewHolder, pDX, pDY, pActionState, pIsCurrentlyActive);
            }
        }

        if (buttonShowedState == ButtonsState.GONE) {

            super.onChildDraw(pC, pRecyclerView, pViewHolder, pDX, pDY, pActionState, pIsCurrentlyActive);
        }
        currentItemViewHolder = pViewHolder;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchListener(final Canvas pC, final RecyclerView pRecyclerView,
                                  final RecyclerView.ViewHolder pViewHolder, final float pDX,
                                  final float pDY, final int pActionState, final boolean pIsCurrentlyActive) {

        pRecyclerView.setOnTouchListener((v, event) -> {

            swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;
            if (swipeBack) {

                if (pDX < -buttonWidth) buttonShowedState = ButtonsState.RIGHT_VISIBLE;
                else if (pDX > buttonWidth) buttonShowedState = ButtonsState.LEFT_VISIBLE;

                if (buttonShowedState != ButtonsState.GONE) {

                    setTouchDownListener(pC, pRecyclerView, pViewHolder, pDX, pDY, pActionState, pIsCurrentlyActive);
                    setItemsClickable(pRecyclerView, false);
                }
            }
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchDownListener(final Canvas pC, final RecyclerView pRecyclerView,
                                      final RecyclerView.ViewHolder pViewHolder, final float pDX,
                                      final float pDY, final int pActionState, final boolean pIsCurrentlyActive) {

        pRecyclerView.setOnTouchListener((pV, pEvent) -> {

            if (pEvent.getAction() == MotionEvent.ACTION_DOWN) {

                setTouchUpListener(pC, pRecyclerView, pViewHolder, pDX, pDY, pActionState, pIsCurrentlyActive);
            }
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchUpListener(final Canvas pC, final RecyclerView pRecyclerView,
                                    final RecyclerView.ViewHolder pViewHolder, final float pDX, final float pDY,
                                    final int pActionState, final boolean pIsCurrentlyActive) {

        pRecyclerView.setOnTouchListener((pV, pEvent) -> {

            if (pEvent.getAction() == MotionEvent.ACTION_UP) {

                SwipeController.super.onChildDraw(pC, pRecyclerView, pViewHolder, pDX, pDY, pActionState, pIsCurrentlyActive);
                pRecyclerView.setOnTouchListener((v1, event1) -> false);
                setItemsClickable(pRecyclerView, true);
                swipeBack = false;

                if (buttonsActions != null && buttonInstance != null && buttonInstance.contains(pEvent.getX(), pEvent.getY())) {

                    if (buttonShowedState == ButtonsState.LEFT_VISIBLE) {

                        buttonsActions.onLeftClicked(pViewHolder.getAdapterPosition());
                    } else if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {

                        buttonsActions.onRightClicked(pViewHolder.getAdapterPosition());
                    }
                }
                buttonShowedState = ButtonsState.GONE;
                currentItemViewHolder = null;
            }
            return false;
        });
    }

    private void setItemsClickable(RecyclerView pRecyclerView, boolean pIsClickable) {

        for (int i = 0; i < pRecyclerView.getChildCount(); ++i) {

            pRecyclerView.getChildAt(i).setClickable(pIsClickable);
        }
    }

    private void drawButtons(Canvas pC, RecyclerView.ViewHolder pViewHolder) {

        float buttonWidthWithoutPadding = buttonWidth - 20;
        float corners = 125;

        View itemView = pViewHolder.itemView;
        Paint p = new Paint();

        RectF leftButton = new RectF(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + buttonWidthWithoutPadding, itemView.getBottom());
        p.setColor(Color.RED);
        pC.drawRoundRect(leftButton, corners, corners, p);
        drawText("Удалить", pC, leftButton, p);

        RectF rightButton = new RectF(itemView.getRight() - buttonWidthWithoutPadding, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        p.setColor(Color.BLUE);
        pC.drawRoundRect(rightButton, corners, corners, p);
        drawText("Изменить", pC, rightButton, p);

        buttonInstance = null;
        if (buttonShowedState == ButtonsState.LEFT_VISIBLE) {

            buttonInstance = leftButton;
        } else if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {

            buttonInstance = rightButton;
        }
    }

    private void drawText(String pText, Canvas pC, RectF pButton, Paint pPaint) {

        float textSize = 60;
        pPaint.setColor(Color.WHITE);
        pPaint.setAntiAlias(true);
        pPaint.setTextSize(textSize);

        float textWidth = pPaint.measureText(pText);
        pC.drawText(pText, pButton.centerX() - (textWidth / 2), pButton.centerY() + (textSize / 2), pPaint);
    }

    public void onDraw(Canvas pC) {

        if (currentItemViewHolder != null) {
            drawButtons(pC, currentItemViewHolder);
        }
    }
}

