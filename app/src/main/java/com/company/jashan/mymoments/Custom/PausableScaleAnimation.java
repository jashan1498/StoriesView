package com.company.jashan.mymoments.Custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;

class PausableScaleAnimation extends ScaleAnimation {
    long mElapsedTime = 0;
    boolean pause = false;

    PausableScaleAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean getTransformation(long currentTime, Transformation outTransformation, float scale) {

        if (pause) {

        }

        return super.getTransformation(currentTime, outTransformation, scale);
    }
}
