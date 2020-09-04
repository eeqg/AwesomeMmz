package com.example.wp.awesomemmz.javaz.dummy

import com.example.wp.awesomemmz.index.bean.ClassInfoBean
import com.example.wp.awesomemmz.javaz.reflects.ReflectActivity
import java.util.*

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<ClassInfoBean> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()

    init {
        // Add some sample items.
        createDummyData()
    }

    private fun createDummyData() {
        ITEMS.add(ClassInfoBean("Reflect", ReflectActivity::class.java.name))
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }
}