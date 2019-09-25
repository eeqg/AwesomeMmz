package com.example.wp.awesomemmz.map;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.wp.awesomemmz.APP;
import com.example.wp.awesomemmz.R;
import com.example.wp.awesomemmz.map.service.LocationService;
import com.example.wp.resource.utils.LogUtils;

public class MapActivity extends AppCompatActivity implements SensorEventListener {

    MapView mapView;
    private BaiduMap baiduMap;
    private LocationService locationService;
    private SensorManager mSensorManager;
    private boolean isFirstLoc = true;
    private double curLatitude, curLongitude;
    private float currentAccracy;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private MyLocationData locationData;
    private MyLocationConfiguration.LocationMode mCurrentMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // 获取传感器管理服务
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // 为系统的方向传感器注册监听器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);

        mapView = findViewById(R.id.mapView);
        baiduMap = mapView.getMap();
        // 开启定位图层
        baiduMap.setMyLocationEnabled(true);

        //mode
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        // 传入null，则为默认图标
        baiduMap.setMyLocationConfiguration(new MyLocationConfiguration(mCurrentMode, true, null));

        //定位
        locationService = ((APP) getApplication()).locationService;
        locationService.registerListener(mListener);
        // locationService.start();

        RadioGroup rgType = findViewById(R.id.rgType);
        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbStyleNormal) {//普通图
                    baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                }
                if (checkedId == R.id.rbSatellite) {//卫星图
                    baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                }
            }
        });
    }

    BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mapView == null) {
                return;
            }
            StringBuffer sb = new StringBuffer(256);
            sb.append("\nlatitude : ");// 纬度
            sb.append(location.getLatitude());
            sb.append("\nlongtitude : ");// 经度
            sb.append(location.getLongitude());
            sb.append("\nradius : ");// 半径
            sb.append(location.getRadius());
            sb.append("\nCountryCode : ");// 国家码
            sb.append(location.getCountryCode());
            sb.append("\nProvince : ");// 获取省份
            sb.append(location.getProvince());
            sb.append("\nCountry : ");// 国家名称
            sb.append(location.getCountry());
            sb.append("\ncitycode : ");// 城市编码
            sb.append(location.getCityCode());
            sb.append("\ncity : ");// 城市
            sb.append(location.getCity());
            sb.append("\nDistrict : ");// 区
            sb.append(location.getDistrict());
            sb.append("\nTown : ");// 获取镇信息
            sb.append(location.getTown());
            sb.append("\nStreet : ");// 街道
            sb.append(location.getStreet());
            sb.append("\naddr : ");// 地址信息
            sb.append(location.getAddrStr());
            LogUtils.d("-----location: " + sb.toString());

            curLatitude = location.getLatitude();
            curLongitude = location.getLongitude();
            currentAccracy = location.getRadius();
            //设置并显示中心点
            locationData = new MyLocationData.Builder()
                    .accuracy(currentAccracy)// 设置定位数据的精度信息，单位：米
                    .direction(mCurrentDirection)// 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(curLatitude)
                    .longitude(curLongitude)
                    .build();
            baiduMap.setMyLocationData(locationData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(curLatitude, curLongitude);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    };

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        LogUtils.d("-----lastX : " + lastX);
        LogUtils.d("-----x : " + x);
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locationData = new MyLocationData.Builder()
                    .accuracy(currentAccracy)// 设置定位数据的精度信息，单位：米
                    .direction(mCurrentDirection)// 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(curLatitude)
                    .longitude(curLongitude)
                    .build();
            baiduMap.setMyLocationData(locationData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时必须调用mMapView. onResume ()
        mapView.onResume();
        locationService.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时必须调用mMapView. onPause ()
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationService.stop();
    }

    @Override
    protected void onDestroy() {

        locationService.unregisterListener(mListener);
        locationService.stop();

        mSensorManager.unregisterListener(this);

        // 在activity执行onDestroy时必须调用mMapView.onDestroy()
        mapView.onDestroy();
        mapView = null;

        super.onDestroy();
    }
}
