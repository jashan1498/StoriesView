package com.company.jashan.mymoments.Custom;

import android.support.v7.widget.LinearSnapHelper;

public class SlowSnapHelper extends LinearSnapHelper {

    @Override
    public boolean onFling(int velocityX, int velocityY) {
        return super.onFling(velocityX * 3, velocityY);
    }

    @Override
    public int[] calculateScrollDistance(int velocityX, int velocityY) {
        return super.calculateScrollDistance(velocityX / 2, velocityY);
    }

}
