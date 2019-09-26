package com.example.wp.awesomemmz.skill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.wp.awesomemmz.R;
import com.example.wp.resource.base.BaseActivity;
import com.example.wp.resource.utils.CommonUtil;
import com.example.wp.resource.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class RecyclerViewGroupActivity extends BaseActivity {

    RecyclerView recyclerViewDecoration;
    RecyclerView recyclerViewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_group);

        String citys = CommonUtil.readAsset(this, "citys.json");
        ArrayList<CityItemBean> cityList = new Gson().fromJson(citys, new TypeToken<ArrayList<CityItemBean>>() {
        }.getType());
        CityListAdapter cityListAdapter = new CityListAdapter(cityList);

        recyclerViewDecoration = findViewById(R.id.recyclerViewDecoration);
        recyclerViewDecoration.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDecoration.addItemDecoration(new CityItemDecoration());
        recyclerViewDecoration.setAdapter(cityListAdapter);


        recyclerViewLayout = findViewById(R.id.recyclerViewLayout);
        recyclerViewLayout.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLayout.setAdapter(cityListAdapter);
    }
}
