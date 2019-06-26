package com.flexsoft.cardpassword.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.flexsoft.cardpassword.R;

public class VerticalSampleItemLayout extends FrameLayout {

    private View addV;
    private ConstraintLayout contentV;

    public VerticalSampleItemLayout(@NonNull Context context) {

        super(context);
    }

    public VerticalSampleItemLayout(@NonNull Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
    }

    public VerticalSampleItemLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {

        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();
        contentV = findViewById(R.id.content);
        addV = findViewById(R.id.card_element);
        if (contentV == null || addV == null) {

            throw new IllegalArgumentException("Not find view with resourceId R.id.content||R.id.add by VerticalSampleItemLayout");
        }
    }

    public void setElevation(float elevation) {

        ViewCompat.setElevation(contentV, elevation);
        ViewCompat.setElevation(addV, elevation);
    }

    public void setStrokeColor(int color) {


        Drawable back = getResources().getDrawable(color);
//        back.setColorFilter(color, PorterDuff.Mode.OVERLAY);
        addV.setBackground(back);
    }
}