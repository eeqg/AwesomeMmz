package com.example.wp.awesomemmz.skill;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wp.awesomemmz.R;

import java.util.ArrayList;

/**
 * Created by wp on 2019/9/26.
 */
public class CityListAdapter extends Adapter<CityListAdapter.ItemViewHolder> {

    private ArrayList<CityItemBean> dataList;

    public CityListAdapter(ArrayList<CityItemBean> dataList) {
        this.dataList = dataList;
    }

    public boolean isItemHeader(int position) {
        if (position == 0) {
            return true;
        } else {
            return !TextUtils.equals(getItem(position).formatInitial(), getItem(position - 1).formatInitial());
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.tvName.setText(getItem(position).name);
    }

    public CityItemBean getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvName;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
