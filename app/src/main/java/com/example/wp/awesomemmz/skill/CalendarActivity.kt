package com.example.wp.awesomemmz.skill

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.wp.awesomemmz.R
import kotlinx.android.synthetic.main.activity_calendar.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoField

class CalendarActivity : AppCompatActivity() {
    private lateinit var listAdapter: ListAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        
        listAdapter = ListAdapter(this)
        recyclerView.run {
            layoutManager = LinearLayoutManager(this@CalendarActivity)
            addItemDecoration(FloatingItemDecoration(this@CalendarActivity,
                FloatingItemDecoration.Config("#000000", 16f, 58)))
            adapter = listAdapter
        }
        
        initData()
    }
    
    private fun initData() {
        val startMonth = YearMonth.now()
        val endMonth = YearMonth.now().plusMonths(12)
        var currentMonth = startMonth
        val monthList = arrayListOf<MonthBean>()
        while (currentMonth <= endMonth) {
            val year = currentMonth.year
            val month = currentMonth.month
            
            val allDayList = arrayListOf<DayBean>()
            
            //计算第一天是星期几
            val firstDateIndex = getDayOfWeek(currentMonth.atDay(1)).value
            if (firstDateIndex > 1) {
                val emptyList = (1 until firstDateIndex).map {
                    DayBean(null, null, true)
                }
                allDayList.addAll(emptyList)
            }
            
            val dayList = (1..currentMonth.lengthOfMonth()).map {
                val date = LocalDate.of(year, month, it)
                DayBean(date, getDayOfWeek(date), true)
            }
            allDayList.addAll(dayList)
            
            monthList.add(MonthBean(currentMonth.toString(), allDayList))
            
            if (currentMonth != endMonth) currentMonth = currentMonth.plusMonths(1) else break
        }
        listAdapter.setDataList(monthList)
    }
    
    /**
     * 获取第一天为星期几
     */
    private fun getDayOfWeek(date: LocalDate): DayOfWeek {
        return DayOfWeek.of(date.get(ChronoField.DAY_OF_WEEK))
    }
//
//    /**
//     * 获取当月有几天
//     */
//    private fun getMonthMaxDay(): Int {
//        val a: Calendar = Calendar.getInstance()
//        a.set(Calendar.YEAR, year)
//        a.set(Calendar.MONTH, month)
//        return a.getActualMaximum(Calendar.DAY_OF_MONTH)
//    }
    
    inner class ListAdapter(val context: Context) :
        RecyclerView.Adapter<ListAdapter.ItemViewHolder>(), IFloatingAdapter {
        
        private var dataList: List<MonthBean>? = null
        
        fun setDataList(list: List<MonthBean>?) {
            this.dataList = list
            notifyDataSetChanged()
        }
    
        override fun isItemHeader(position: Int): Boolean {
            return true
        }
        
        override fun getShowText(position: Int): String {
            return getItem(position).title
        }
    
        private fun getItem(position: Int): MonthBean {
            return dataList!![position]
        }
        
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.item_month_list, p0, false)
            return ItemViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            if (holder.recyclerView.adapter == null) {
                holder.recyclerView.layoutManager = GridLayoutManager(context, 7)
                holder.recyclerView.adapter = DayListAdapter(context)
            }
            (holder.recyclerView.adapter as DayListAdapter).setDataList(getItem(position).dayList)
        }
        
        override fun getItemCount(): Int {
            return dataList?.size ?: 0
        }
        
        inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
        }
        
        inner class DayListAdapter(val context: Context) :
            RecyclerView.Adapter<DayListAdapter.ItemViewHolder>() {
            
            private var dataList: List<DayBean>? = null
            
            fun setDataList(list: List<DayBean>?) {
                this.dataList = list
                notifyDataSetChanged()
            }
            
            private fun getItem(position: Int): DayBean {
                return dataList!![position]
            }
            
            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
                val view = LayoutInflater.from(context).inflate(R.layout.item_day_list, p0, false)
                return ItemViewHolder(view)
            }
            
            override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
                val dayInt = getItem(position).date?.dayOfMonth
                if (dayInt == null){
                    holder.tvDay.visibility = View.GONE
                    holder.tvExtra.visibility = View.GONE
                    holder.tvExtra2.visibility = View.GONE
                }else{
                    holder.tvDay.visibility = View.VISIBLE
                    holder.tvExtra.visibility = View.VISIBLE
                    holder.tvExtra2.visibility = View.VISIBLE
                    holder.tvDay.text = dayInt.toString()
                }
            }
            
            override fun getItemCount(): Int {
                return dataList?.size ?: 0
            }
            
            inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                var tvDay: TextView = itemView.findViewById(R.id.tvDay)
                var tvExtra: TextView = itemView.findViewById(R.id.tvExtra)
                var tvExtra2: TextView = itemView.findViewById(R.id.tvExtra2)
            }
        }
    }
}