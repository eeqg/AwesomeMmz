package com.example.wp.awesomemmz.skill;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.index.IndexAdapter;
import com.example.wp.awesomemmz.index.bean.ClassInfoBean;
import com.example.wp.awesomemmz.skill.aidls.AidlsActivity;
import com.example.wp.awesomemmz.skill.custom.CustomViewActivity;
import com.example.wp.awesomemmz.skill.font.TextFontActivity;
import com.example.wp.resource.common.RxTimerHelper;
import com.example.wp.resource.utils.LaunchUtil;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SkillFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SkillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkillFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View rootView;

    private OnFragmentInteractionListener mListener;

    public SkillFragment() {
        // Required empty public constructor
    }

    public static SkillFragment newInstance(String param1, String param2) {
        SkillFragment fragment = new SkillFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_skill, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final IndexAdapter indexAdapter = new IndexAdapter(getContext());
        recyclerView.setAdapter(indexAdapter);

        indexAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String className = indexAdapter.getItem(position).classPath;
                if (TextUtils.isEmpty(className)) {
                    return;
                }
                try {
                    Class activityClass = Class.forName(className);
                    LaunchUtil.launchActivity(getContext(), activityClass);
                    // startActivity(new Intent(getContext(), activityClass));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        RxTimerHelper.get(this).timer(2000, new RxTimerHelper.IRxNext() {
            @Override
            public void doNext(long number) {
                indexAdapter.addAll(getData());
                indexAdapter.notifyDataSetChanged();
            }
        });
    }

    private ArrayList<ClassInfoBean> getData() {
        ArrayList<ClassInfoBean> data = new ArrayList<>();
        data.add(new ClassInfoBean("BottomSheetBehavior实现底部弹窗", BottomSheetBehaviorActivity.class.getName()));
        data.add(new ClassInfoBean("SheetBehavior", SheetBehaviorActivity.class.getName()));
        data.add(new ClassInfoBean("CoordinatorLayout组合使用实现地图背景", CoordinateActivity.class.getName()));
        data.add(new ClassInfoBean("CoordinatorLayout.Behavior", CoordinatorBehaviorActivity.class.getName()));
        data.add(new ClassInfoBean("NestedScrolling使用", NestedScrollingActivity.class.getName()));
        data.add(new ClassInfoBean("textView显示富文本(图文混排)", RichTextActivity.class.getName()));
        data.add(new ClassInfoBean("RxBus", RxBusActivity.class.getName()));
        data.add(new ClassInfoBean("Transition", TransitionActivity.class.getName()));
        data.add(new ClassInfoBean("Animation", AnimationsActivity.class.getName()));
        data.add(new ClassInfoBean("customView", CustomViewActivity.class.getName()));
        data.add(new ClassInfoBean("TextFont", TextFontActivity.class.getName()));
        data.add(new ClassInfoBean("RecyclerView-分组", RecyclerViewGroupActivity.class.getName()));
        data.add(new ClassInfoBean("RecyclerView-显示切换", RecyclerSwitchActivity.class.getName()));
        data.add(new ClassInfoBean("AspectJ test", AspectTestActivity.class.getName()));
        data.add(new ClassInfoBean("scheme跳转页面", SchemeTestActivity.class.getName()));
        data.add(new ClassInfoBean("AIDL", AidlsActivity.class.getName()));
        data.add(new ClassInfoBean("...", ""));
        data.add(new ClassInfoBean("...", ""));
        data.add(new ClassInfoBean("...", ""));
        data.add(new ClassInfoBean("...", ""));
        data.add(new ClassInfoBean("...", ""));
        data.add(new ClassInfoBean("...", ""));
        data.add(new ClassInfoBean("...", ""));
        data.add(new ClassInfoBean("...", ""));

        return data;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
        // else {
        // 	throw new RuntimeException(context.toString()
        // 			+ " must implement OnFragmentInteractionListener");
        // }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
