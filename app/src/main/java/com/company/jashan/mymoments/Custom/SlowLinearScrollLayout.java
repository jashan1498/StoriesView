package com.company.jashan.mymoments.Custom;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class SlowLinearScrollLayout extends LinearLayoutManager {
    public SlowLinearScrollLayout(Context context) {
        super(context);
    }

    public SlowLinearScrollLayout(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollHorizontallyBy((int) Math.round(dx / 1), recycler, state);
    }


}
