package com.example.wp.awesomemmz.index.video;

import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.wp.awesomemmz.R;
import com.example.wp.resource.base.BaseActivity;
import com.example.wp.resource.utils.LogUtils;

import java.io.IOException;
import java.util.Locale;

public class VideoActivity extends BaseActivity {

    private final int MSG_UPDATE_TIME = 1;

    private String videoUrl = "https://flv2.bn.netease.com/videolib1/1811/26/OqJAZ893T/HD/OqJAZ893T-mobile.mp4";
    private MediaPlayer mediaPlayer;
    private ImageView ivPlayState;
    private SeekBar seekBar;
    private TextView tvTime;
    private ImageView ivPlayOrPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //textureView需开启硬件加速
        Window w = getWindow();
        w.addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        setContentView(R.layout.activity_video);

        setupTitleBar("Video");

        observeVideoView();
        observeTextureVideo();
    }

    private void observeVideoView() {
        final VideoView videoView = findViewById(R.id.videoView);
        final MediaController videoController = new MediaController(this);
        videoView.setMediaController(videoController);
        videoView.setVideoPath(videoUrl);
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

    private void observeTextureVideo() {
        TextureView textureView = findViewById(R.id.textureView);
        ivPlayState = findViewById(R.id.ivPlayState);
        ivPlayOrPause = findViewById(R.id.ivPlayOrPause);
        seekBar = findViewById(R.id.seekBar);
        tvTime = findViewById(R.id.tvTime);

        textureView.setSurfaceTextureListener(surfaceTextureListener);

        ivPlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        ivPlayOrPause.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                    } else {
                        mediaPlayer.start();
                        ivPlayOrPause.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                    }
                }
            }
        });
    }

    private TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {

        //初始化好SurfaceTexture后调用
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            LogUtils.d("-----onSurfaceTextureAvailable()");
            start(new Surface(surface));
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
            mediaPlayer.setDataSource(this, Uri.parse(videoUrl));
            mediaPlayer.setOnPreparedListener(preparedListener);
            mediaPlayer.setOnCompletionListener(completeListener);
            //设置是否保持屏幕常亮
            mediaPlayer.setScreenOnWhilePlaying(true);
            //设置视频画面显示位置
            mediaPlayer.setSurface(surface);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            LogUtils.d("------onPrepared--" + mp.getDuration());
            ivPlayState.setVisibility(View.GONE);
            mp.start();
            seekBar.setMax(mp.getDuration());
            seekBar.setProgress(0);
            mHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, 200);
        }
    };

    private MediaPlayer.OnCompletionListener completeListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            LogUtils.d("-----onCompletion");
            ivPlayState.setVisibility(View.GONE);
            mHandler.removeMessages(MSG_UPDATE_TIME);
        }
    };

    private void updateTime() {
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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mediaPlayer != null) {
                updateTime();
                mHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, 200);
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mHandler.removeMessages(MSG_UPDATE_TIME);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mHandler.removeMessages(MSG_UPDATE_TIME);
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
