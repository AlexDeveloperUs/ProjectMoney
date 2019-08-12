package com.flexsoft.cardpassword.utils;

import android.support.v4.view.ViewCompat;
import android.view.View;

public class VerticalSampleChildDecorateHelper implements LadderLayoutManager.ChildDecorateHelper {

    private float mElevation;

    public VerticalSampleChildDecorateHelper(float maxElevation) {

        mElevation = maxElevation;
    }

    @Override
    public void decorateChild(View child, float posOffsetPercent, float layoutPercent, boolean isBottom) {

        VerticalSampleItemLayout v = (VerticalSampleItemLayout) child;
        if (isBottom) {

            ViewCompat.setAlpha(v, posOffsetPercent);
        } else {

            ViewCompat.setAlpha(v, 1);
        }
    }
}
