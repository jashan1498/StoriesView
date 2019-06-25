package com.company.jashan.mymoments.model;

import android.graphics.Bitmap;

import static com.company.jashan.mymoments.Constants.IMAGE_CONTENT;

public class ImageContent extends Content {
    Bitmap content;

    public ImageContent(Bitmap bitmap, int duration) {
        this.duration = duration;
        this.format = IMAGE_CONTENT;
        this.content = bitmap;
    }

    public ImageContent() {
    }

    public Bitmap getContent() {
        return content;
    }

    public void setContent(Bitmap content) {
        this.content = content;
    }
}