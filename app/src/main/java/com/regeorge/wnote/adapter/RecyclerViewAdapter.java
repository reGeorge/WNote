package com.regeorge.wnote.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.regeorge.wnote.R;
import com.regeorge.wnote.view.AdImageViewVersion1;


/**
 * Created by reGeorge on 2017/12/9.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    String[] datas;
    public RecyclerViewAdapter(String[] datas) {
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(datas[position]);
        if (position > 0 && position % 6 == 0) {
            holder.mAdImageView.setVisibility(View.VISIBLE);
        } else {
            holder.mAdImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return datas.length;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public AdImageViewVersion1 mAdImageView;
        public ViewHolder(View view){
            super(view);
            mTextView = (TextView) view.findViewById(R.id.text1);
            mAdImageView = (AdImageViewVersion1) view.findViewById(R.id.image1);
        }
    }

}
