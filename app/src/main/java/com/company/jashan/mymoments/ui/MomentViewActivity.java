package com.company.jashan.mymoments.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.company.jashan.mymoments.Custom.MomentsView;
import com.company.jashan.mymoments.R;

public class MomentViewActivity extends AppCompatActivity {


    public static Bitmap drawableToBitmap(Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment);

        MomentsView view = findViewById(R.id.moments_view);

        Drawable drawable1 = getDrawable(R.drawable.image1);
        Drawable drawable2 = getDrawable(R.drawable.image2);
        Drawable drawable3 = getDrawable(R.drawable.image3);

        Bitmap bitmap1 = drawableToBitmap(drawable1);
        Bitmap bitmap2 = drawableToBitmap(drawable2);
        Bitmap bitmap3 = drawableToBitmap(drawable3);

        view.setOnProgressListener(new MomentsView.OnProgressListener() {
            @Override
            public void onProgressFinished() {
                finish();
            }
        });

        view.addImage(bitmap1, 4200);
        view.addVideo(R.raw.testvideo, 2200);
        view.addImage(bitmap2, 1200);
        view.addImage(bitmap3, 2200);
        view.play();


    }
}