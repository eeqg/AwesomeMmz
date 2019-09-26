package com.example.wp.awesomemmz.skill;

import android.text.TextUtils;

/**
 * Created by wp on 2019/9/26.
 */
public class CityItemBean {
    // "label": "北京Beijing010",
    // "name": "北京",
    // "pinyin": "Beijing",
    // "zip": "010"
    public String name;
    public String pinyin;

    public String formatInitial() {
        String initial = "0";
        if (!TextUtils.isEmpty(pinyin)) {
            initial = String.valueOf(pinyin.toUpperCase().charAt(0));
        }
        return initial;
    }
}
