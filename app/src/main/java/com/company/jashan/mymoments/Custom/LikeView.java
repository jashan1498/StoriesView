package com.company.jashan.mymoments.Custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.keyframes.KeyframesDrawable;
import com.facebook.keyframes.KeyframesDrawableBuilder;
import com.facebook.keyframes.deserializers.KFImageDeserializer;
import com.facebook.keyframes.model.KFImage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LikeView extends LinearLayout {


    ArrayList<KeyframesDrawable> drawableList =
            new ArrayList<KeyframesDrawable>() ;

    public LikeView(Context context) {
        super(context);
        init();
    }

    public LikeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LikeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        setBackgroundColor(getResources().getColor(android.R.color.white));
        LayoutParams params =
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 400);
        LayoutParams imageParams = new LayoutParams(120, 120);
        params.setMargins(10, 10, 10, 10);

        setLayoutParams(params);

        for (int x = 0; x < 5; x++) {
            ImageView view = new ImageView(getContext());
            view.setLayoutParams(imageParams);
            if (inflateImages(view) != null) {
                drawableList.add(inflateImages(view));

            }
            addView(view);
        }
    }

    private KeyframesDrawable inflateImages(ImageView view) {
        try {
            InputStream stream = getResources().getAssets().open("sample_file");
            KFImage image = KFImageDeserializer.deserialize(stream);
            KeyframesDrawable drawable =
                    new KeyframesDrawableBuilder().withImage(image).build();
            view.setImageDrawable(drawable);
            return drawable;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void play() {
        for (KeyframesDrawable drawable : drawableList) {
            drawable.startAnimation();
        }
    }
}
