package com.example.wp.awesomemmz;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.bilibili.boxing.BoxingCrop;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingMediaLoader;
import com.example.wp.awesomemmz.common.BoxingGlideLoader;
import com.example.wp.awesomemmz.common.BoxingUcrop;
import com.example.wp.awesomemmz.common.PPViewGlideLoader;
import com.example.wp.awesomemmz.common.PictureLayoutImageLoader;
import com.example.wp.awesomemmz.map.service.LocationService;
import com.example.wp.resource.base.BaseApp;
import com.example.wp.resource.utils.LogUtils;
import com.wp.picture.picker.PictureLayout;
import com.wp.picture.preview.PPView;

import java.util.List;

/**
 * Created by wp on 2019/4/9.
 */
public class APP extends BaseApp {

    public LocationService locationService;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        int pid = Process.myPid();
        LogUtils.d("-----App onCreate()--pid = " + pid);
        LogUtils.d("-----App onCreate()--pidName0 = " + getProcessName());
        LogUtils.d("-----App onCreate()--pidName = " + getProcessName(pid));

        if ("com.example.wp.awesomemmz".equals(getProcessName(pid))) {
            initPictureLayout();
            initBoxing();
            initPicturePreview();

            initMap();
        }
    }

    private String getProcessName(int pid) {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcesses) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }

        return null;
    }

    private void initPictureLayout() {
        PictureLayout.setImageLoader(new PictureLayoutImageLoader());
    }

    private void initBoxing() {
        IBoxingMediaLoader loader = new BoxingGlideLoader();
        BoxingMediaLoader.getInstance().init(loader);
        BoxingCrop.getInstance().init(new BoxingUcrop());
    }

    private void initPicturePreview() {
        PPView.setImageLoader(new PPViewGlideLoader());
    }

    private void initMap() {
        locationService = new LocationService(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    @Override
    public void logout(Context context) {

    }

    @Override
    public void cleanTop(Context context) {

    }

    @Override
    public void requestLogin(Context context, int requestCode) {

    }

    @Override
    public void viewGoodsInfo(Context context, String goodsId) {

    }
}
