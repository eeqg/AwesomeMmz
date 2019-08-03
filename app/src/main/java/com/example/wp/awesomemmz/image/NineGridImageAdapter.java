package com.example.wp.awesomemmz.image;

import android.content.Context;

import com.example.wp.resource.widget.ninegrid.ImageInfo;
import com.example.wp.resource.widget.ninegrid.NineGridViewAdapter;

import java.util.List;

/**
 * Created by wp on 2019/8/3.
 */
public class NineGridImageAdapter extends NineGridViewAdapter {
	public NineGridImageAdapter(Context context, List<ImageInfo> imageInfo) {
		super(context, imageInfo);
	}
}
