package com.company.jashan.mymoments.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.company.jashan.mymoments.Custom.LikeView;
import com.company.jashan.mymoments.R;

public class Reaction_activity extends AppCompatActivity {

    PopupWindow likepopUpWindow;
    Button button;
    LikeView likeView;
    private boolean isViewShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction_activity);


        button = findViewById(R.id.button2);
        likeView = new LikeView(this);
        likepopUpWindow = new PopupWindow(this);
        likepopUpWindow.setBackgroundDrawable(null);

        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (isViewShowing) {
                    likepopUpWindow.dismiss();
                    isViewShowing = false;
                } else {
                    likepopUpWindow.setContentView(likeView);
                    likepopUpWindow.showAsDropDown(v, 0, -2 * v.getHeight(),
                            Gravity.TOP);
                    likeView.play();
                    isViewShowing = true;
                }
                return true;
            }
        });


    }


}
