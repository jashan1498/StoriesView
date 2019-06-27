package com.company.jashan.mymoments.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.company.jashan.mymoments.Adapter.StatusViewAdapter;
import com.company.jashan.mymoments.Custom.SlowLinearScrollLayout;
import com.company.jashan.mymoments.Custom.SlowSnapHelper;
import com.company.jashan.mymoments.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> dummyList = new ArrayList<String>();
    StatusViewAdapter statusViewAdapter;
    boolean run;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView statusView = findViewById(R.id.status_list_view);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Reaction_activity.class));
            }
        });
        makeDummyList();

        statusView.setLayoutManager(new SlowLinearScrollLayout(this, SlowLinearScrollLayout.HORIZONTAL, false));
        statusViewAdapter = new StatusViewAdapter(dummyList, statusView, this);
        statusViewAdapter.setOnClickListener(new StatusViewAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, MomentViewActivity.class);
                startActivity(intent);
            }
        });


        statusViewAdapter.setLoadMoreListener(new StatusViewAdapter.LoadMoreListener() {
            @Override
            public void onLoad() {
                run = true;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (run && dummyList.size() < 10) {
                            dummyList.add(dummyList.size() - 1, "hello");
                            statusViewAdapter.setData(dummyList);
                            statusViewAdapter.notifyItemInserted(dummyList.size() - 1);
                            run = false;
                        }
                    }
                }, 1500);

            }
        });

        SlowSnapHelper slowSnapHelper = new SlowSnapHelper();
        slowSnapHelper.attachToRecyclerView(statusView);
        statusView.setAdapter(statusViewAdapter);
    }


    private void makeDummyList() {
        for (int x = 0; x < 3; x++) {
            dummyList.add("dummyItem" + x);
        }
        dummyList.add(null);

    }
}
