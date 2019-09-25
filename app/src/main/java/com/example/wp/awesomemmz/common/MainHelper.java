package com.example.wp.awesomemmz.common;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wp on 2019/2/13.
 */
public class MainHelper {
    private static MainHelper instance;

    public static MainHelper getInstance() {
        if (instance == null)
            instance = new MainHelper();
        return instance;
    }

    private MainHelper() {
    }

    public List<String> getTestData(int count) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(i + "");
        }
        return list;
    }

    /**
     * 检查是否拥有指定的所有权限
     *
     * @return 缺少的权限
     */
    public ArrayList<String> checkPermission(Context context, String[] permissions) {
        ArrayList<String> permissionsList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                //没有权限
                permissionsList.add(permission);
            }
        }
        return permissionsList;
    }
}
