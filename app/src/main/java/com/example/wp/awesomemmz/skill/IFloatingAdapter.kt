package com.example.wp.awesomemmz.skill

import java.text.FieldPosition

/**
 * @description
 * @author wp
 * @date   2022/7/4 10:03
 */
interface IFloatingAdapter {
    fun isItemHeader(position: Int): Boolean
    
    fun getShowText(position: Int): String
}