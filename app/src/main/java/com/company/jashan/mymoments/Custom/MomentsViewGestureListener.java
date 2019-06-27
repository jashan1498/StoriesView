package com.company.jashan.mymoments.Custom;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.company.jashan.mymoments.model.Content;

import java.util.ArrayList;


public class MomentsViewGestureListener implements GestureDetector.OnGestureListener {

    public boolean isPaused = false;
    private MomentsView.OnProgressListener onProgressListener;
    private ProgressView progressView;
    private ArrayList<Content> contentList;
    private LinearLayout parentLayout;
    private MomentsView view;

    MomentsViewGestureListener(MomentsView.OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }

    void setData(ArrayList<Content> contentList, LinearLayout parentLayout,
            MomentsView view) {
        this.contentList = contentList;
        this.parentLayout = parentLayout;
        this.view = view;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }


    @Override
    public void onShowPress(MotionEvent e) {

    }


    @Override
    public boolean onSingleTapUp(MotionEvent event) {

        int thresholdWidth = Math.round(parentLayout.getWidth() / 2f);

        if (event.getX() > thresholdWidth) {
            // right side
            if (view.currentIndex + 1 <= contentList.size()) {
                ++view.currentIndex;
            }
            view.play();
        } else {
            //  left side
            if (view.currentIndex - 1 >= 0) {
                --view.currentIndex;
            }
            if (view.currentIndex == 0 || view.currentIndex == contentList.size() - 1) {
                for (Content content : contentList) {
                    content.getProgressView().stopAnimation();
                }
            }
            view.play();
        }
        Log.d("fkowekfowkfkwf", "onSingleTapUp: ");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
            float distanceY) {

        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        progressView.pauseAnimation();
        view.videoView.pause();
        isPaused = true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
            float velocityY) {

        if (e2.getY() - e1.getY() > 20) {
            onProgressListener.onProgressFinished();
        }
        return true;
    }

    void setOnProgressListener(MomentsView.OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }

    void setProgressViewListener(ProgressView progressView) {
        this.progressView = progressView;
    }
}

