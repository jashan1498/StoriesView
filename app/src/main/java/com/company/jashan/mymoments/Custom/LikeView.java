package com.company.jashan.mymoments.Custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.company.jashan.mymoments.R;
import com.facebook.keyframes.KeyframesDirectionallyScalingDrawable;
import com.facebook.keyframes.KeyframesDrawable;
import com.facebook.keyframes.KeyframesDrawableBuilder;
import com.facebook.keyframes.deserializers.KFImageDeserializer;
import com.facebook.keyframes.model.KFImage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.widget.LinearLayout.HORIZONTAL;

public class LikeView extends FrameLayout {


    ArrayList<KeyframesDrawable> drawableList = new ArrayList<>();

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
        LayoutParams params = new LayoutParams(400, 400);
        setLayoutParams(params);

        LinearLayout parentLayout = new LinearLayout(getContext());
        parentLayout.setOrientation(HORIZONTAL);
        parentLayout.setPadding(0, 20, 0, 20);

//        Drawable circularDrawable =
//                getResources().getDrawable(R.drawable.circular_edit_text);
//
//        ImageView bg = new ImageView(getContext());
//        LayoutParams bgParams = new LayoutParams(160 * 5, 440);
//        bg.setLayoutParams(bgParams);
//        bg.setBackground(circularDrawable);


        LayoutParams parentParams =
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(200, 200);
//        imageParams.setMargins(0, 10, 0, 10);

        parentLayout.setLayoutParams(parentParams);

        for (int x = 0; x < 5; x++) {
            final ImageView view = new ImageView(getContext());
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.setScaleX(1f);
                    view.setScaleY(1f);
                }
            });
            view.setScaleX(0.5f);
            view.setScaleY(0.5f);
            view.setLayoutParams(imageParams);
            if (inflateImages(view) != null) {
                drawableList.add(inflateImages(view));
            }
            parentLayout.addView(view);
        }
        addView(parentLayout);
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
