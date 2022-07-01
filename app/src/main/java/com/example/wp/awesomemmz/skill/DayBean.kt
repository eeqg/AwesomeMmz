package com.example.wp.awesomemmz.skill

import java.time.DayOfWeek
import java.time.LocalDate

/**
 * @description
 * @author wp
 * @date   2022/7/1 16:31
 */
data class DayBean(val date: LocalDate?, val week: DayOfWeek?, val currentMonth: Boolean) {
}