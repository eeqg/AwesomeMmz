package com.example.wp.awesomemmz.skill;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.index.ProductAdapter;
import com.example.wp.awesomemmz.index.SuspensionDecoration;
import com.example.wp.awesomemmz.index.bean.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wp on 2019/4/4.
 */
public class SampleSheetDialogFragment extends BottomSheetDialogFragment {
	
	View rootView;
	protected RecyclerView productView;
	protected List<Product.Classify> classifies = new ArrayList<>();
	
	public static SampleSheetDialogFragment newInstance() {
		return new SampleSheetDialogFragment();
	}
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_spec, container, false);
		return rootView;
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		productView = (RecyclerView) rootView.findViewById(R.id.product_view);
		productView.setLayoutManager(new LinearLayoutManager(getContext()));
		productView.addItemDecoration(new SuspensionDecoration(getContext(), classifies));
		
		classifies.add(new Product.Classify("颜色", Arrays.asList(new Product.Classify.Des("红色"),
				new Product.Classify.Des("白色"),
				new Product.Classify.Des("蓝色"),
				new Product.Classify.Des("橘黄色"),
				new Product.Classify.Des("格调灰"),
				new Product.Classify.Des("深色"),
				new Product.Classify.Des("咖啡色"))));
		classifies.add(new Product.Classify("尺寸", Arrays.asList(new Product.Classify.Des("180"),
				new Product.Classify.Des("175"),
				new Product.Classify.Des("170"),
				new Product.Classify.Des("165"),
				new Product.Classify.Des("160"),
				new Product.Classify.Des("155"),
				new Product.Classify.Des("150"))));
		classifies.add(new Product.Classify("款式",
				Arrays.asList(new Product.Classify.Des("男款"), new Product.Classify.Des("女款"),
						new Product.Classify.Des("中年款"),
						new Product.Classify.Des("潮流款"),
						new Product.Classify.Des("儿童款"))));
		classifies.add(new Product.Classify("腰围", Arrays.asList(new Product.Classify.Des("26"),
				new Product.Classify.Des("27"),
				new Product.Classify.Des("28"),
				new Product.Classify.Des("29"),
				new Product.Classify.Des("30"),
				new Product.Classify.Des("31"),
				new Product.Classify.Des("32"),
				new Product.Classify.Des("33"),
				new Product.Classify.Des("34"),
				new Product.Classify.Des("35"))));
		classifies.add(new Product.Classify("肩宽", Arrays.asList(new Product.Classify.Des("26"),
				new Product.Classify.Des("27"),
				new Product.Classify.Des("28"),
				new Product.Classify.Des("29"),
				new Product.Classify.Des("30"),
				new Product.Classify.Des("31"),
				new Product.Classify.Des("32"),
				new Product.Classify.Des("33"),
				new Product.Classify.Des("34"),
				new Product.Classify.Des("35"))));
		classifies.add(new Product.Classify("臂长", Arrays.asList(new Product.Classify.Des("26"),
				new Product.Classify.Des("27"),
				new Product.Classify.Des("28"),
				new Product.Classify.Des("29"),
				new Product.Classify.Des("30"),
				new Product.Classify.Des("31"),
				new Product.Classify.Des("32"),
				new Product.Classify.Des("33"),
				new Product.Classify.Des("34"),
				new Product.Classify.Des("35"))));
		productView.setAdapter(new ProductAdapter(getContext(), classifies));
	}
}
