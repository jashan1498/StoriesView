package com.company.jashan.mymoments.model;

import com.company.jashan.mymoments.Custom.ProgressView;

public class Content {
    String format;
    int duration;
    ProgressView progressView;


    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ProgressView getProgressView() {
        return progressView;
    }

    public void setProgressView(ProgressView progressView) {
        this.progressView = progressView;
    }
}
