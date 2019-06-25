package com.company.jashan.mymoments.model;

import android.net.Uri;

import static com.company.jashan.mymoments.Constants.VIDEO_CONTENT;

public class VideoContent extends Content {
    private Uri uri;

    public VideoContent(Uri uri, int duration) {
        this.duration = duration;
        this.format = VIDEO_CONTENT;
        this.uri = uri;
    }

    public VideoContent() {
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
