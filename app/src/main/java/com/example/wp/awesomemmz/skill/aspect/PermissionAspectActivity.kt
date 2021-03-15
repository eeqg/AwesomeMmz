package com.example.wp.awesomemmz.skill.aspect

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity


class PermissionAspectActivity : AppCompatActivity() {

    companion object {
        private const val ARG_PERMISSIONS = "permissions"
        private var mCallback: Callback? = null

        fun startActivity(context: Context, permissions: Array<String>, callback: Callback) {
            mCallback = callback
            val intent = Intent(context, PermissionAspectActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra(ARG_PERMISSIONS, permissions)
            }
            context.startActivity(intent)
            if (context is Activity) {
                context.overridePendingTransition(0, 0)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val permissions = intent.getStringArrayExtra(ARG_PERMISSIONS)
        if (checkPermission(permissions)){
            mCallback?.onGranted()
            finish()
            overridePendingTransition(0, 0)
        }
    }

    private fun checkPermission(permissions: Array<String>): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkSelfPermissions(permissions)
        } else true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkSelfPermissions(permissions: Array<String>): Boolean {
        var flag = true
        val losePermissions: MutableList<String> = mutableListOf()
        for (p in permissions) {
            if (checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) {
                flag = false
                losePermissions.add(p)
            }
        }
        if (losePermissions.isNotEmpty()) {
            requestPermissions(losePermissions.toTypedArray(), 9999)
        }
        return flag
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var hasAllGranted = true
        for (i in grantResults.indices) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false
//                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
//                    showPermissionDeniedDialog()
//                } else {
//                    //权限申请被拒绝 ，但用户未选择'不再提示'选项
//                }
                break
            }
        }
        if (hasAllGranted) {
            mCallback?.onGranted()
        } else {
            mCallback?.onDenied()
        }
        finish()
        overridePendingTransition(0, 0)
    }

    interface Callback {
        fun onGranted()

        fun onDenied()
    }
}