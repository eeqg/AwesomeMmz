package com.example.wp.resource.utils

import android.content.Context
import android.widget.Toast

/**
 * @description
 * @author wp
 * @date   2021/4/23 9:51
 */
fun String.toast(context: Context){
    Toast.makeText(context.applicationContext, this, Toast.LENGTH_SHORT).show()
}