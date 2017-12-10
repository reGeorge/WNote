package com.regeorge.wnote.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.regeorge.wnote.R;
import com.regeorge.wnote.adapter.RecyclerViewAdapter;
import com.regeorge.wnote.view.AdImageViewVersion1;

public class RvActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    RecyclerViewAdapter mRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);
        initView();
    }

    private void initView() {
        String[] datas = new String[15];
        for (int i=0;i<15;i++){
            datas[i]=i+"这是文字这是文字这是文字这是文字这是文字这是文字这是文字这是文字这是文字这是文字这是文字";
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);

        mRecyclerViewAdapter = new RecyclerViewAdapter(datas);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int fPos = mLinearLayoutManager.findFirstVisibleItemPosition();
                int lPos = mLinearLayoutManager.findLastVisibleItemPosition();
                for (int i = fPos; i <= lPos; i++) {
                    View view = mLinearLayoutManager.findViewByPosition(i);
                    AdImageViewVersion1 adImageView = (AdImageViewVersion1) view.findViewById(R.id.image1);
                    if (adImageView.getVisibility() == View.VISIBLE) {
                        Log.e("mTAG", "onScrolled: H ");
                        float H = mLinearLayoutManager.getHeight();
                        float h = view.getBottom();
                        float bili = h/H;
                        adImageView.setDx(bili);
                    }
                }
            }
        });
    }
}
