package com.example.wp.awesomemmz.star;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.model.BoxingManager;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.config.BoxingCropOption;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing.model.entity.impl.ImageMedia;
import com.bilibili.boxing.utils.BoxingFileHelper;
import com.bilibili.boxing.utils.ImageCompressor;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.bilibili.boxing_impl.ui.BoxingBottomSheetActivity;
import com.bilibili.boxing_impl.view.SpacesItemDecoration;
import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.common.Picker;
import com.example.wp.resource.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BoxingTestActivity extends BaseActivity implements View.OnClickListener {
	private static final int REQUEST_CODE = 1024;
	private static final int COMPRESS_REQUEST_CODE = 2048;
	
	private RecyclerView mRecyclerView;
	private MediaResultAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_boxing_test);
		setupTitleBar("boxing-test");
		
		findViewById(R.id.single_image_btn).setOnClickListener(this);
		findViewById(R.id.single_image_btn_crop_btn).setOnClickListener(this);
		findViewById(R.id.multi_image_btn).setOnClickListener(this);
		findViewById(R.id.video_btn).setOnClickListener(this);
		findViewById(R.id.outside_bs_btn).setOnClickListener(this);
		
		mRecyclerView = (RecyclerView) findViewById(R.id.media_recycle_view);
		mAdapter = new MediaResultAdapter();
		mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.addItemDecoration(new SpacesItemDecoration(8));
		mRecyclerView.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.single_image_btn:
				Picker.pickSingle(this, COMPRESS_REQUEST_CODE);
				break;
			case R.id.single_image_btn_crop_btn:
				Picker.pickerCrop(this, REQUEST_CODE, 1, 1);
				break;
			case R.id.multi_image_btn:
				Picker.pickerMulti(this, REQUEST_CODE, 5);
				break;
			case R.id.video_btn:
				BoxingConfig videoConfig = new BoxingConfig(BoxingConfig.Mode.VIDEO).withVideoDurationRes(R.mipmap.ic_boxing_play);
				Boxing.of(videoConfig).withIntent(this, BoxingActivity.class).start(this, REQUEST_CODE);
				break;
			case R.id.outside_bs_btn:
				BoxingConfig bsConfig = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG);
				Boxing.of(bsConfig).withIntent(this, BoxingBottomSheetActivity.class).start(this, REQUEST_CODE);
				break;
			
			default:
				break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (mRecyclerView == null || mAdapter == null) {
				return;
			}
			mRecyclerView.setVisibility(View.VISIBLE);
			final ArrayList<BaseMedia> medias = Boxing.getResult(data);
			if (requestCode == REQUEST_CODE) {
				mAdapter.setList(medias);
			} else if (requestCode == COMPRESS_REQUEST_CODE) {
				final List<BaseMedia> imageMedias = new ArrayList<>(1);
				BaseMedia baseMedia = medias.get(0);
				if (!(baseMedia instanceof ImageMedia)) {
					return;
				}
				Log.d("-----", "path = " + baseMedia.getPath());
				Log.d("-----", "type = " + ((ImageMedia) baseMedia).getImageType());
				final ImageMedia imageMedia = (ImageMedia) baseMedia;
				// the compress task may need time
				if (imageMedia.compress(new ImageCompressor(this))) {
					imageMedia.removeExif();
					imageMedias.add(imageMedia);
					mAdapter.setList(imageMedias);
				}
				
			}
		}
	}
	
	private class MediaResultAdapter extends RecyclerView.Adapter {
		private ArrayList<BaseMedia> mList;
		
		MediaResultAdapter() {
			mList = new ArrayList<>();
		}
		
		void setList(List<BaseMedia> list) {
			if (list == null) {
				return;
			}
			mList.clear();
			mList.addAll(list);
			notifyDataSetChanged();
		}
		
		List<BaseMedia> getMedias() {
			if (mList == null || mList.size() <= 0 || !(mList.get(0) instanceof ImageMedia)) {
				return null;
			}
			return mList;
		}
		
		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_boxing_simple_media_item, parent, false);
			int height = parent.getMeasuredHeight() / 4;
			view.setMinimumHeight(height);
			return new MediaViewHolder(view);
		}
		
		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
			if (holder instanceof MediaViewHolder) {
				MediaViewHolder mediaViewHolder = (MediaViewHolder) holder;
				mediaViewHolder.mImageView.setImageResource(BoxingManager.getInstance().getBoxingConfig().getMediaPlaceHolderRes());
				BaseMedia media = mList.get(position);
				String path;
				if (media instanceof ImageMedia) {
					path = ((ImageMedia) media).getThumbnailPath();
				} else {
					path = media.getPath();
				}
				BoxingMediaLoader.getInstance().displayThumbnail(mediaViewHolder.mImageView, path, 150, 150);
			}
		}
		
		@Override
		public int getItemCount() {
			return mList == null ? 0 : mList.size();
		}
		
	}
	
	private class MediaViewHolder extends RecyclerView.ViewHolder {
		private ImageView mImageView;
		
		MediaViewHolder(View itemView) {
			super(itemView);
			mImageView = (ImageView) itemView.findViewById(R.id.media_item);
		}
	}
}
