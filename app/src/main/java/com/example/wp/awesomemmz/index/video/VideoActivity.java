package com.example.wp.awesomemmz.index.video;

import android.content.pm.ActivityInfo;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.common.GlideImageLoader;
import com.example.wp.resource.base.BaseActivity;
import com.example.wp.resource.utils.LogUtils;
import com.wp.picture.video.SimpleVideoView;
import com.wp.picture.widget.CommonViewPager;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class VideoActivity extends BaseActivity {

    private static final int MSG_UPDATE_TIME = 1;
    private static final int MSG_UPDATE_CONTROLLER = 2;

    public static final int STATE_ERROR = -1;          // 播放错误
    public static final int STATE_IDLE = 0;            // 播放未开始
    public static final int STATE_PREPARING = 1;       // 播放准备中
    public static final int STATE_PREPARED = 2;        // 播放准备就绪
    public static final int STATE_PLAYING = 3;         // 正在播放
    public static final int STATE_PAUSED = 4;          // 暂停播放
    // 正在缓冲(播放器正在播放时，缓冲区数据不足，进行缓冲，缓冲区数据足够后恢复播放)
    public static final int STATE_BUFFERING_PLAYING = 5;
    // 正在缓冲(播放器正在播放时，缓冲区数据不足，进行缓冲，此时暂停播放器，继续缓冲，缓冲区数据足够后恢复暂停)
    public static final int STATE_BUFFERING_PAUSED = 6;
    public static final int STATE_COMPLETED = 7;       // 播放完成

    public static final int TYPE_SCREEN_NORMAL = 0;
    public static final int TYPE_SCREEN_FULL = 1;
    public static final int TYPE_SCREEN_TINY = 2;

    //private String videoUrl = "https://flv2.bn.netease.com/videolib1/1811/26/OqJAZ893T/HD/OqJAZ893T-mobile.mp4";
    private String videoUrl2 = "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4";
    private String videoUrl3 = "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-10_10-20-26.mp4";
    private String thumb3 = "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-10_10-09-58.jpg";
    private String videoUrl4 = "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-03_13-02-41.mp4";
    private String thumb4 = "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-03_12-52-08.jpg";
    private String videoUrl5 = "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/04/2017-04-28_18-20-56.mp4";
    private String thumb5 = "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/04/2017-04-28_18-18-22.jpg";
    private TextureView textureView;
    private MediaPlayer mediaPlayer;
    private ImageView ivPlayState;
    private ImageView ivThumb;
    private SeekBar seekBar;
    private TextView tvTime;
    private ImageView ivPlayOrPause;
    private ImageView ivFastForward;
    private ImageView ivFastRewind;
    private ImageView ivFullscreen;
    private ProgressBar progressBar;
    private View clController;
    private View clControlContainer;
    private View clVideoContainer;
    private View btnTiny;
    // private LinearLayout llRootView;
    private FrameLayout flVideoRoot;

    private int mCurrentState = STATE_IDLE;
    private int mVideoScreenType = TYPE_SCREEN_NORMAL;

    private ViewGroup contentRoot;
    private SurfaceTexture mSurfaceTexture;
    private SimpleVideoView simpleVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // //textureView需开启硬件加速,,manifest中已设置
        // Window w = getWindow();
        // w.addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        setContentView(R.layout.activity_video);

        setupTitleBar("Video");

        observeVideoView();
        observeTextureVideo();
        observeSimpleVideo();
        observeGsyVideo();
    }

    private void observeGsyVideo(){
        String[] urls = getResources().getStringArray(R.array.url);
        List<String> images = Arrays.asList(urls);

        CommonViewPager viewPager = findViewById(R.id.videoPager);
        viewPager
                .createItemView(new CommonViewPager.ViewCreator<FrameLayout, String>() {
                    @Override
                    public View onCreateView(int position) {
                        return LayoutInflater.from(mActivity).inflate(R.layout.view_home_banner, null);
                    }

                    @Override
                    public void onBindView(FrameLayout view, String data, int position) {
                        if (position == 0) {

                        } else {
                            ImageView ivBanner = view.findViewById(R.id.ivBanner);
                            GlideImageLoader.getInstance().load(ivBanner, data);
                        }
                    }
                })
                .setOnItemSelectedListener(new CommonViewPager.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(Object data, int position) {
                        if (position != 0) {
//                            if (simpleVideoView.isPlaying()) {
//                                simpleVideoView.pausePlay();
//                            }
                        }
                    }
                })
                .execute(images);
    }

    private void observeVideoView() {
        final VideoView videoView = findViewById(R.id.videoView);
        final MediaController videoController = new MediaController(this);
        videoView.setMediaController(videoController);
        videoView.setVideoPath(videoUrl2);
        // videoView.start();

        final View ivPlay = findViewById(R.id.ivPlay);
        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (videoView.isPlaying()) {
                //     ivPlay.setVisibility(View.VISIBLE);
                // } else {
                ivPlay.setVisibility(View.GONE);
                videoView.start();
                // }
            }
        });
    }

    private void observeSimpleVideo() {
        simpleVideo = findViewById(R.id.simpleVideoView);
        SimpleVideoView.VideoInfo videoInfo = new SimpleVideoView.VideoInfo(
                videoUrl4
                , "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-10_10-09-58.jpg",
                "title");
        simpleVideo.setImageLoader(GlideImageLoader.getInstance()).setup(videoInfo);
    }

    private void observeTextureVideo() {
        contentRoot = findViewById(android.R.id.content);
        flVideoRoot = findViewById(R.id.flVideoRoot);

        textureView = findViewById(R.id.textureView);
        ivPlayState = findViewById(R.id.ivPlayState);
        ivPlayOrPause = findViewById(R.id.ivPlayOrPause);
        ivThumb = findViewById(R.id.ivThumb);
        seekBar = findViewById(R.id.seekBar);
        tvTime = findViewById(R.id.tvTime);
        progressBar = findViewById(R.id.progressBar);
        ivFastForward = findViewById(R.id.ivFastForward);
        ivFastRewind = findViewById(R.id.ivFastRewind);
        ivFullscreen = findViewById(R.id.ivFullscreen);
        clController = findViewById(R.id.clController);
        clControlContainer = findViewById(R.id.clControlContainer);
        clVideoContainer = findViewById(R.id.clVideoContainer);
        btnTiny = findViewById(R.id.btnTiny);

        seekBar.setClickable(false);
        seekBar.setEnabled(false);
        seekBar.setFocusable(false);

        GlideImageLoader.getInstance().load(ivThumb, thumb5);

        textureView.setSurfaceTextureListener(surfaceTextureListener);

        ivPlayState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlay();
            }
        });
        ivPlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        pausePlay();
                    } else {
                        startPlay();
                    }
                }
            }
        });
        ivFastForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int duration = mediaPlayer.getDuration();
                int position = mediaPlayer.getCurrentPosition() + 15 * 1000;
                position = position < duration ? position : duration;
                mediaPlayer.seekTo(position);
            }
        });
        ivFastRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mediaPlayer.getCurrentPosition() - 15 * 1000;
                position = position > 0 ? position : 0;
                mediaPlayer.seekTo(position);
            }
        });
        ivFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVideoScreenType != TYPE_SCREEN_NORMAL) {
                    setVideoScreenType(TYPE_SCREEN_NORMAL);
                } else {
                    setVideoScreenType(TYPE_SCREEN_FULL);
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                LogUtils.d("-----onStopTrackingTouch: progress= " + seekBar.getProgress());
                if (mCurrentState != STATE_IDLE && mCurrentState != STATE_ERROR) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }
        });

        clControlContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                LogUtils.d("-----event.getAction() : " + event.getAction());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        showController();
                        return true;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        hideController();
                        break;
                }
                return false;
            }
        });

        btnTiny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVideoScreenType != TYPE_SCREEN_NORMAL) {
                    setVideoScreenType(TYPE_SCREEN_NORMAL);
                } else {
                    setVideoScreenType(TYPE_SCREEN_TINY);
                }
            }
        });
    }

    private void showController() {
        if (mHandler.hasMessages(MSG_UPDATE_CONTROLLER)) {
            LogUtils.d("-----has msg..");
            mHandler.removeMessages(MSG_UPDATE_CONTROLLER);
        }
        clController.setVisibility(View.VISIBLE);
    }

    private void hideController() {
        LogUtils.d("-----hideController()");
        if (mHandler.hasMessages(MSG_UPDATE_CONTROLLER)) {
            LogUtils.d("-----has msg..");
            mHandler.removeMessages(MSG_UPDATE_CONTROLLER);
        }
        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_CONTROLLER, 4000);
    }

    private void startPlay() {
        if (mCurrentState == STATE_IDLE) {
            mediaPlayer.prepareAsync();
            setState(STATE_PREPARING);
        } else {
            mediaPlayer.start();
            setState(STATE_PLAYING);
        }
    }

    private void pausePlay() {
        mediaPlayer.pause();
        setState(STATE_PAUSED);
    }

    private TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {

        //初始化好SurfaceTexture后调用
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
            LogUtils.d("-----onSurfaceTextureAvailable()");
            // start(new Surface(surface));
            //切换到全屏后重新调用onSurfaceTextureAvailable
            if (mSurfaceTexture == null) {
                mSurfaceTexture = surfaceTexture;
                start(new Surface(mSurfaceTexture));
            } else {
                textureView.setSurfaceTexture(mSurfaceTexture);
            }
        }

        //尺寸改变后调用
        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        //SurfaceTexture即将被销毁时调用
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        //通过SurfaceTexture.updateteximage()更新SurfaceTexture时调用
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    private void start(Surface surface) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(this, Uri.parse(videoUrl4));
            mediaPlayer.setOnPreparedListener(preparedListener);
            mediaPlayer.setOnCompletionListener(completeListener);
            mediaPlayer.setOnErrorListener(errorListener);
            mediaPlayer.setOnInfoListener(infoListener);
            mediaPlayer.setOnBufferingUpdateListener(bufferingUpdateListener);
            //设置是否保持屏幕常亮
            mediaPlayer.setScreenOnWhilePlaying(true);
            //设置视频画面显示位置
            mediaPlayer.setSurface(surface);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //准备就绪
    private MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            LogUtils.d("------onPrepared--" + mp.getDuration());
            setState(STATE_PREPARED);
        }
    };

    //错误监听
    private MediaPlayer.OnErrorListener errorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            LogUtils.d("-----onError()--what: " + what);
            setState(STATE_ERROR);
            return false;
        }
    };

    //
    private MediaPlayer.OnInfoListener infoListener = new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                // 播放器渲染第一帧
                LogUtils.d("onInfo-----MEDIA_INFO_VIDEO_RENDERING_START：STATE_PLAYING");
            } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                // MediaPlayer暂时不播放，以缓冲更多的数据
                LogUtils.d("onInfo-----MEDIA_INFO_BUFFERING_START");
                progressBar.setVisibility(View.VISIBLE);
            } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                // 填充缓冲区后，MediaPlayer恢复播放/暂停
                LogUtils.d("onInfo-----MEDIA_INFO_BUFFERING_END");
                progressBar.setVisibility(View.GONE);
            } else {
                LogUtils.d("onInfo-----what = " + what);
            }
            return true;
        }
    };

    //缓冲进度监听
    private MediaPlayer.OnBufferingUpdateListener bufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            LogUtils.d("-----onBufferingUpdate--percent = " + percent);
        }
    };

    //播放完成
    private MediaPlayer.OnCompletionListener completeListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            LogUtils.d("-----onCompletion");
            setState(STATE_COMPLETED);
        }
    };

    private void setVideoScreenType(int type) {
        if (type == mVideoScreenType) {
            return;
        }
        mVideoScreenType = type;
        switch (mVideoScreenType) {
            case TYPE_SCREEN_FULL:
                videoFullscreen();
                break;
            case TYPE_SCREEN_TINY:
                videoTinyScreen();
                break;
            case TYPE_SCREEN_NORMAL:
                videoNormalScreen();
                break;
        }
    }

    private void videoFullscreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        flVideoRoot.removeView(clVideoContainer);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        contentRoot.addView(clVideoContainer, layoutParams);
    }

    private void videoTinyScreen() {
        flVideoRoot.removeView(clVideoContainer);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(320, 180);
        layoutParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        contentRoot.addView(clVideoContainer, layoutParams);
    }

    private void videoNormalScreen() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        contentRoot.removeView(clVideoContainer);
        flVideoRoot.addView(clVideoContainer, layoutParams);
    }

    private void setState(int state) {
        mCurrentState = state;
        LogUtils.d("-----mCurrentState : " + mCurrentState);
        switch (state) {
            case STATE_PREPARING:
                ivPlayState.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                break;
            case STATE_PREPARED:
                progressBar.setVisibility(View.GONE);
                ivThumb.setVisibility(View.GONE);
                seekBar.setClickable(true);
                seekBar.setEnabled(true);
                seekBar.setFocusable(true);
                seekBar.setMax(mediaPlayer.getDuration());
                seekBar.setProgress(0);
                showController();
                ivFastRewind.setClickable(true);
                ivFastForward.setClickable(true);
                startPlay();
                break;
            case STATE_PLAYING:
                ivThumb.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                ivPlayState.setVisibility(View.GONE);
                ivFastRewind.setClickable(true);
                ivFastForward.setClickable(true);
                ivPlayState.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                ivPlayOrPause.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                mHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, 200);
                break;
            case STATE_PAUSED:
                ivPlayState.setVisibility(View.VISIBLE);
                ivFastRewind.setClickable(true);
                ivFastForward.setClickable(true);
                ivPlayState.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                ivPlayOrPause.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                mHandler.removeMessages(MSG_UPDATE_TIME);
                break;
            case STATE_BUFFERING_PAUSED:

                break;
            case STATE_COMPLETED:
                ivFastRewind.setClickable(false);
                ivFastForward.setClickable(false);
                ivPlayState.setVisibility(View.GONE);
                ivThumb.setVisibility(View.VISIBLE);
                mHandler.removeMessages(MSG_UPDATE_TIME);
                break;
            case STATE_ERROR:
                ivFastRewind.setClickable(false);
                ivFastForward.setClickable(false);
                ivThumb.setVisibility(View.VISIBLE);
                mHandler.removeMessages(MSG_UPDATE_TIME);
                seekBar.setProgress(0);
                break;
        }
    }

    private void updateTime() {
        LogUtils.d("-----updateTime >>>");
        if (mediaPlayer != null) {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            tvTime.setText(String.format("%s/%s",
                    formatTime(mediaPlayer.getCurrentPosition()), formatTime(mediaPlayer.getDuration())));
        } else {
            seekBar.setProgress(0);
            tvTime.setText("00:00/00:00");
        }
    }

    private String formatTime(int ms) {
        final int minuteP = 60 * 1000;
        int minute = 0, second = 0;
        minute = ms / minuteP;
        second = (ms - minute * minuteP) / 1000;
        return String.format(Locale.CHINA, "%02d:%02d", minute, second);
    }

//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case MSG_UPDATE_TIME:
//                    if (mediaPlayer != null) {
//                        updateTime();
//                        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, 200);
//                    }
//                    break;
//                case MSG_UPDATE_CONTROLLER:
//                    clController.setVisibility(View.GONE);
//                    mHandler.removeMessages(MSG_UPDATE_CONTROLLER);
//                    break;
//            }
//        }
//    };

    public Handler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private WeakReference<VideoActivity> extra;

        public MyHandler(VideoActivity videoActivity) {
            extra = new WeakReference<>(videoActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (extra.get() == null) return;
            switch (msg.what) {
                case MSG_UPDATE_TIME:
                    if (extra.get().mediaPlayer != null) {
                        extra.get().updateTime();
                        extra.get().mHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, 200);
                    }
                    break;
                case MSG_UPDATE_CONTROLLER:
                    extra.get().clController.setVisibility(View.GONE);
                    extra.get().mHandler.removeMessages(MSG_UPDATE_CONTROLLER);
                    break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mHandler.removeMessages(MSG_UPDATE_TIME);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止内存泄漏
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        mHandler = null;
        if (mediaPlayer != null) {
            //mHandler.removeMessages(MSG_UPDATE_TIME);
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (simpleVideo != null && simpleVideo.isFullscreenModel()) {
            simpleVideo.enterNormalScreen();
            return;
        }
        super.onBackPressed();
    }
}
