package com.example.wp.resource.common.ext

import android.graphics.Bitmap

/**
 * bitmap 缩放
 */
fun Bitmap.scale(newWidth: Int, newHeight: Int, recycle: Boolean = false): Bitmap {
    var ret = Bitmap.createScaledBitmap(this, newWidth, newHeight, true)
    if (recycle && !this.isRecycled) this.recycle()
    return ret
}

