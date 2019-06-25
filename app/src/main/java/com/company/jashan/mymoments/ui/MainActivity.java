package com.company.jashan.mymoments.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.company.jashan.mymoments.Adapter.StatusViewAdapter;
import com.company.jashan.mymoments.Custom.SlowLinearScrollLayout;
import com.company.jashan.mymoments.Custom.SlowSnapHelper;
import com.company.jashan.mymoments.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> dummyList = new ArrayList<String>();
    StatusViewAdapter statusViewAdapter;
    boolean run;
    RecyclerView statusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView statusView = findViewById(R.id.status_list_view);
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
