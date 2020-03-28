package com.example.wp.awesomemmz.skill

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import com.example.wp.awesomemmz.R
import com.example.wp.resource.base.BaseActivity
import kotlinx.android.synthetic.main.activity_sheet_behavior.*

class SheetBehaviorActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sheet_behavior)

        val sheetBehavior = BottomSheetBehavior.from(scrollView)
    }
}
