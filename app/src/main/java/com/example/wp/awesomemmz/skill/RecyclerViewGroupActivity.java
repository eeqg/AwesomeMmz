package com.example.wp.awesomemmz.skill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.wp.awesomemmz.R;
import com.example.wp.resource.base.BaseActivity;
import com.example.wp.resource.utils.CommonUtil;
import com.example.wp.resource.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerViewGroupActivity extends BaseActivity {

    RecyclerView recyclerViewDecoration;
    private Map<String, Integer> indexMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_group);

        String citys = CommonUtil.readAsset(this, "citys.json");
        ArrayList<CityItemBean> cityList = new Gson().fromJson(citys, new TypeToken<ArrayList<CityItemBean>>() {
        }.getType());
        for (int index = 0; index < cityList.size(); index++) {
            CityItemBean cityItemBean = cityList.get(index);
            if (!indexMap.containsKey(cityItemBean.formatInitial())) {
                indexMap.put(cityItemBean.formatInitial(), index);
            }
        }
        CityListAdapter cityListAdapter = new CityListAdapter(cityList);

        recyclerViewDecoration = findViewById(R.id.recyclerViewDecoration);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewDecoration.setLayoutManager(layoutManager);
        recyclerViewDecoration.addItemDecoration(new CityItemDecoration());
        recyclerViewDecoration.setAdapter(cityListAdapter);

        final TextView tvLetter = findViewById(R.id.tvLetter);
        LetterIndexView letterIndexView = findViewById(R.id.letterIndexView);
        letterIndexView.setOnLetterTouchListener(new LetterIndexView.OnLetterTouchListener() {
            @Override
            public void onTouched(int position, String letter) {
                LogUtils.d(String.format("-----%s, %s", position, letter));
                tvLetter.setVisibility(View.VISIBLE);
                tvLetter.setText(letter);
                if(TextUtils.equals("#", letter)){
                    layoutManager.scrollToPositionWithOffset(0, 0);
                    return;
                }
                if (indexMap.containsKey(letter)) {
                    int index = indexMap.get(letter);
                    // recyclerViewDecoration.scrollToPosition(index);
                    layoutManager.scrollToPositionWithOffset(index, 0);
                }
            }

            @Override
            public void onCancel() {
                tvLetter.setVisibility(View.GONE);
            }
        });

    }
}
