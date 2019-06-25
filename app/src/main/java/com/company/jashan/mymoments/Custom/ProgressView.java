package com.company.jashan.mymoments.Custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.company.jashan.mymoments.R;

public class ProgressView extends FrameLayout {

    View maxProgress;
    View moment_progress;
    FrameLayout parentView;
    ValueAnimator animator;
    long currentPlayTime = 0;
    private int duration = 0;
    private int maxWidth = 0;
    private ProgressListener progressListener;

    public ProgressView(Context context, int duration) {
        super(context);
        this.duration = duration;
        init(context);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    void setProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.moment_progress_view, this, false);
        maxProgress = view.findViewById(R.id.max_progress);
        moment_progress = view.findViewById(R.id.moment_progress);
        parentView = view.findViewById(R.id.progress_view_parent);
        addView(view);
        setClickable(true);
    }

    int getMaxWidth() {
        if (parentView != null) {
            this.maxWidth = parentView.getWidth();
        }
        return maxWidth;
    }

    void setDuration(int duration) {
        this.duration = duration;
    }

    void startAnimation() {
        invalidate();
        if (duration > 0) {
            animator = ValueAnimator.ofFloat(0f, 100f);
            animator.setDuration(duration);
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int width = Math.round((float) animation.getAnimatedValue() * getMaxWidth() / 100);
                    int progress = (int) Math.round(Math.floor((float) animation.getAnimatedValue()));
                    moment_progress.setLayoutParams(new FrameLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));
                    if (progressListener != null) {
                        progressListener.onProgress(progress);
                    }
                    invalidate();
                }
            });
            animator.start();
        }
    }

    public void stopAnimation() {
        if (animator != null) {
            animator.cancel();
        }
    }

    public void setProgress(int progress) {
        int width = progress * getMaxWidth() / 100;
        moment_progress.setLayoutParams(new FrameLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));
        invalidate();
    }

    public void pauseAnimation() {
        if (animator != null) {
            animator.pause();
        }
    }

    public void resumeAnimation() {
        if (animator != null) {
            animator.resume();
        }
    }

    public interface ProgressListener {
        void onProgress(int progress);
    }
}
