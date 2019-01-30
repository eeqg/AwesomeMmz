package com.example.wp.awesomemmz.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wp.awesomemmz.R;

import java.util.ArrayList;

/**
 * Created by wp on 2019/1/30.
 */
public class IndexFragment extends Fragment {
	
	private View rootView;
	private ArrayList<String> data = new ArrayList<>();
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_list_common, container, false);
		return rootView;
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initData();
		initView();
	}
	
	private void initData() {
		data.add("TitleBar");
		data.add("SpecView");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
		data.add("");
	}
	
	private void initView() {
		RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		IndexAdapter indexAdapter = new IndexAdapter(getContext());
		recyclerView.setAdapter(indexAdapter);
		
		indexAdapter.addAll(data);
		indexAdapter.notifyDataSetChanged();
	}
}
