package com.company.jashan.mymoments.Custom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.company.jashan.mymoments.R;
import com.company.jashan.mymoments.model.Content;
import com.company.jashan.mymoments.model.ImageContent;
import com.company.jashan.mymoments.model.VideoContent;

import java.util.ArrayList;

public class MomentsView extends FrameLayout {
    ArrayList<Content> contentList = new ArrayList<>();
    int currentIndex;
    LinearLayout childLayout;
    LinearLayout parentLayout;
    VideoView videoView;
    ImageView bgImageView;
    OnProgressListener onProgressListener;
    GestureDetector gestureDetector;
    EditText replyEditText;
    LinearLayout replyLayout;
    MomentsViewGestureListener gestureListener;
    KeyboardHeightProvider keyboardHeightProvider;
    View replyEditView;
    int keyboardHeight = 0;
    InputMethodManager imm;

    public MomentsView(Context context) {
        super(context);
    }

    public MomentsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MomentsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public MomentsView(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
        gestureListener.setOnProgressListener(onProgressListener);
    }

    private void init(AttributeSet attrs) {
        TypedArray array = getResources().obtainAttributes(attrs,
                R.styleable.moments_view);
        gestureListener = new MomentsViewGestureListener(onProgressListener);
        currentIndex = 0;
        keyboardHeightProvider =
                new KeyboardHeightProvider((Activity) getContext());
        imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        setViews();
        array.recycle();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setViews() {
        childLayout = new LinearLayout(getContext());
        childLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        childLayout.setOrientation(LinearLayout.HORIZONTAL);

        parentLayout = new LinearLayout(getContext());
        parentLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        parentLayout.setBackgroundColor(getContext().getResources().getColor(android.R.color.black));
        videoView = new VideoView(getContext());
        bgImageView = new ImageView(getContext());

        //////////////////////// bottom view /////////////////////////////
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        FrameLayout.LayoutParams replyLayoutParams =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        replyLayout = new LinearLayout(getContext());
        ImageView arrowUpImage = new ImageView(getContext());

        arrowUpImage.setBackgroundResource(R.drawable.arrow_up);

        arrowUpImage.setLayoutParams(params);
        replyLayoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        replyLayout.setLayoutParams(replyLayoutParams);

        replyEditView =
                LayoutInflater.from(getContext()).inflate(R.layout.reply_layout, this, false);

        replyLayout.setOrientation(LinearLayout.VERTICAL);
        replyLayout.addView(arrowUpImage);
        replyLayout.addView(replyEditView);
        replyEditText = replyEditView.findViewById(R.id.edit_text);

        replyLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowReplyView();
            }
        });
        /////////////////////////////////////////////////////////////
        LinearLayout.LayoutParams imageParams =
                new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        imageParams.weight = 1;
        imageParams.gravity = Gravity.CENTER;

        videoView.setLayoutParams(imageParams);
        bgImageView.setLayoutParams(imageParams);

        gestureListener.setData(contentList, parentLayout, this);
        gestureDetector = new GestureDetector(getContext(), gestureListener);
        parentLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                if (imm.isActive()) imm.toggleSoftInput(InputMethodManager
// .HIDE_NOT_ALWAYS, 0);
                return gestureDetector.onTouchEvent(event);
            }
        });

        parentLayout.addView(videoView);
        parentLayout.addView(bgImageView);
        addView(parentLayout);
        addView(childLayout);
        addView(replyLayout);
        hideViews();
    }

    private void ShowReplyView() {
        keyboardHeightProvider.setKeyboardHeightObserver(new KeyboardHeightObserver() {
            @Override
            public void onKeyboardHeightChanged(int height, int orientation) {
                keyboardHeight = height;

                if (height > 150) {
                    showReplyEditView(height);
                } else {
                    hideReplyEditView();
                }
            }
        });

        if (replyEditText.requestFocus()) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            keyboardHeightProvider.start();
        }

    }

    private void hideReplyEditView() {
        TranslateAnimation translateAnimation =
                new TranslateAnimation(Animation.ABSOLUTE, 0,
                        Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
                        Animation.ABSOLUTE, 0);
        translateAnimation.setDuration(500);
        replyLayout.setAnimation(translateAnimation);
        replyEditView.setVisibility(GONE);

        translateAnimation.setFillAfter(true);
        translateAnimation.start();
    }

    private void showReplyEditView(int height) {
        TranslateAnimation translateAnimation =
                new TranslateAnimation(Animation.ABSOLUTE, 0,
                        Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
                        Animation.ABSOLUTE, -height - 100);
        translateAnimation.setDuration(400);
        replyLayout.setAnimation(translateAnimation);
        translateAnimation.setFillAfter(true);

        replyEditView.setVisibility(VISIBLE);
        translateAnimation.start();

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_UP:
                if (gestureListener.isPaused) {
                    contentList.get(currentIndex).getProgressView().resumeAnimation();
                    videoView.start();
                }
        }
        return super.onInterceptTouchEvent(event);
    }

    private void hideViews() {
        videoView.setVisibility(View.GONE);
        bgImageView.setVisibility(View.GONE);
    }

    public void play() {
        if (currentIndex < contentList.size()) {
            final Content content = contentList.get(currentIndex);
            if (content instanceof ImageContent) {
                showImageView();
                Bitmap bitmap =
                        ((ImageContent) contentList.get(currentIndex)).getContent();
                bgImageView.setBackground(new BitmapDrawable(getResources(),
                        bitmap));
                startContentAnimation(content);
                content.getProgressView().setProgressListener(new ProgressView.ProgressListener() {
                    @Override
                    public void onProgress(int progress) {
                        if (progress == 100) {
                            ifFinished(content);
                            currentIndex = currentIndex + 1;
                            play();
                        }
                    }
                });
            } else if (content instanceof VideoContent) {
                Uri uri =
                        ((VideoContent) contentList.get(currentIndex)).getUri();
                showVideoView();
                videoView.setVideoURI(uri);
                videoView.start();
                startContentAnimation(content);
                content.getProgressView().setProgressListener(new ProgressView.ProgressListener() {
                    @Override
                    public void onProgress(int progress) {
                        if (progress == 100) {
                            ifFinished(content);
                            videoView.stopPlayback();
                            currentIndex = currentIndex + 1;
                            play();
                        }
                    }
                });
            }
        }
    }

    private void ifFinished(Content content) {
        if (contentList.get(contentList.size() - 1).equals(content))
            onProgressListener.onProgressFinished();
    }

    private void startContentAnimation(Content content) {
        boolean isBefore = true;
        for (Content dummyContent : contentList) {
            if (dummyContent == content) {
                isBefore = false;
                content.getProgressView().startAnimation();
                gestureListener.setProgressViewListener(content.getProgressView());
            } else {
                dummyContent.getProgressView().stopAnimation();
                if (isBefore) {
                    dummyContent.getProgressView().setProgress(100);
                } else {
                    dummyContent.getProgressView().setProgress(0);
                }
            }
        }
    }

    void showVideoView() {
        videoView.setVisibility(View.VISIBLE);
        bgImageView.setVisibility(View.GONE);

    }

    void showImageView() {
        bgImageView.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.GONE);
    }

    public void addImage(Bitmap bitmap, int duration) {
        ImageContent content = new ImageContent();
        content.setDuration(duration);
        content.setContent(bitmap);
        content.setProgressView(createProgressView(duration));
        contentList.add(content);
    }

    private ProgressView createProgressView(int duration) {
        ProgressView progressView = new ProgressView(getContext(), duration);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        params.topMargin =
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 15, getContext().getResources().getDisplayMetrics()));
        progressView.setLayoutParams(params);
        if (contentList.size() >= 1) {
            addSpace();
            childLayout.addView(progressView);
        } else {
            childLayout.addView(progressView);
        }
        return progressView;
    }

    public void addVideo(int res, int duration) {
        VideoContent content = new VideoContent();
        Uri uri =
                Uri.parse("android.resource://" + getContext().getPackageName() + "/" + res);
        content.setUri(uri);
        content.setDuration(duration);
        content.setProgressView(createProgressView(duration));
        contentList.add(content);
    }

    private void addSpace() {
        View emptyView = new View(getContext());
        LayoutParams params = new LayoutParams(10,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin =
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 15, getContext().getResources().getDisplayMetrics()));
        emptyView.setLayoutParams(params);

        childLayout.addView(emptyView);
    }

    public interface OnProgressListener {
        void onProgressFinished();
    }
}
