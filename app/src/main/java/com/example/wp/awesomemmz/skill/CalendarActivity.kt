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
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.wp.awesomemmz.R
import kotlinx.android.synthetic.main.activity_calendar.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoField

class CalendarActivity : AppCompatActivity() {
    private lateinit var listAdapter: ListAdapter
    private var startDate: LocalDate? = null
    private var endDate: LocalDate? = null
    
    private var multiSelect = true
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        
        listAdapter = ListAdapter(this).apply {
            setOnSelectedListener { onSelectDate(it) }
        }
        recyclerView.run {
            layoutManager = LinearLayoutManager(this@CalendarActivity)
            addItemDecoration(FloatingItemDecoration(this@CalendarActivity,
                FloatingItemDecoration.Config("#000000", 16f, 35)))
            adapter = listAdapter
            
            setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                val adapter: ListAdapter = (v as RecyclerView).adapter as ListAdapter
                val layoutManager = (v as RecyclerView).layoutManager as? LinearLayoutManager
                layoutManager?.run {
                    val firstVisiblePosition = findFirstVisibleItemPosition()
                    tvDateTitle.text = adapter.getShowText(firstVisiblePosition)
                }
            }
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
            if (firstDateIndex + 1 > 1) { //UI第一列为星期天
                val emptyList = (1 until firstDateIndex + 1).map {
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
    
    private fun onSelectDate(date: LocalDate) {
        if (multiSelect) {
            if (startDate != null && endDate == null && date.isAfter(startDate)) {
                endDate = date
            } else {
                startDate = date
                endDate = null
            }
        } else {
            this.startDate = date
        }
        listAdapter.setSelectedDate(startDate, endDate)
    }
    
    inner class ListAdapter(val context: Context) :
        RecyclerView.Adapter<ListAdapter.ItemViewHolder>(), IFloatingAdapter {
        
        private var selectedListener: ((LocalDate) -> Unit)? = null
        private var dataList: List<MonthBean>? = null
        
        private var startDate: LocalDate? = null
        private var endDate: LocalDate? = null
        
        fun setDataList(list: List<MonthBean>?) {
            this.dataList = list
            notifyDataSetChanged()
        }
        
        fun setOnSelectedListener(l: ((LocalDate) -> Unit)?) {
            selectedListener = l
        }
        
        fun setSelectedDate(start: LocalDate?, end: LocalDate?) {
            startDate = start
            endDate = end
            if (dataList.isNullOrEmpty()) return
            //notifyItemMoved(0, dataList!!.size)
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
            (holder.recyclerView.adapter as DayListAdapter).let {
                it.setDataList(getItem(position).dayList)
                it.setOnSelectedListener { selectedListener?.invoke(it) }
                it.setSelectedDate(startDate, endDate)
            }
        }
        
        override fun getItemCount(): Int {
            return dataList?.size ?: 0
        }
        
        inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
        }
        
        inner class DayListAdapter(val context: Context) :
            RecyclerView.Adapter<DayListAdapter.ItemViewHolder>() {
            
            private var selectedListener: ((LocalDate) -> Unit)? = null
            private var dataList: List<DayBean>? = null
            
            private var startDate: LocalDate? = null
            private var endDate: LocalDate? = null
            
            fun setDataList(list: List<DayBean>?) {
                this.dataList = list
                notifyDataSetChanged()
            }
            
            fun setOnSelectedListener(l: ((LocalDate) -> Unit)?) {
                selectedListener = l
            }
            
            fun setSelectedDate(start: LocalDate?, end: LocalDate?) {
                startDate = start
                endDate = end
                if (dataList.isNullOrEmpty()) return
                //notifyItemMoved(0, dataList!!.size)
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
                val itemBean = getItem(position)
                if (itemBean.date == null) {
                    holder.tvDay.visibility = View.GONE
                    holder.tvExtra.visibility = View.GONE
                    holder.tvExtra2.visibility = View.GONE
                    holder.viewRoot.isSelected = false
                    holder.viewRoot.background = null
                } else {
                    holder.tvDay.visibility = View.VISIBLE
                    holder.tvExtra.visibility = View.VISIBLE
                    holder.tvExtra2.visibility = View.VISIBLE
                    holder.tvDay.text = itemBean.date.dayOfMonth.toString()
                    //holder.viewContainer.isSelected = judgeSelectDate(itemBean.date) == 1
                    //if (judgeSelectDate(itemBean.date) == 2) {
                    //    holder.viewRoot.setBackgroundColor(Color.parseColor("#FFF3EC"))
                    //}
                    
                    if (getItem(position).isPastDay()) {
                        holder.tvDay.isEnabled = false
                        holder.tvExtra.isEnabled = false
                        holder.tvExtra2.isEnabled = false
                        holder.viewRoot.isSelected = false
                        holder.viewRoot.background = null
                    } else {
                        holder.viewRoot.setOnClickListener {
                            if (getItem(position).isPastDay()) return@setOnClickListener
                            selectedListener?.invoke(itemBean.date)
                        }
                        
                        if (endDate == null) {
                            holder.viewContainer.isSelected = judgeSelectDate(itemBean.date) == 0
                            holder.viewRoot.background = null
                        } else {
                            when (judgeSelectDate(itemBean.date)) {
                                0 -> {
                                    holder.viewContainer.isSelected = true
                                    if (itemBean.date.dayOfWeek == DayOfWeek.SUNDAY
                                        || itemBean.date.dayOfWeek == DayOfWeek.SATURDAY
                                        || itemBean.date.dayOfMonth == itemBean.date.lengthOfMonth()
                                    ) {
                                        holder.viewRoot.background = null
                                    } else {
                                        holder.viewRoot.setBackgroundResource(R.drawable.bg_select_left)
                                    }
                                }
                                1 -> {
                                    holder.viewContainer.isSelected = true
                                    if (itemBean.date.dayOfWeek == DayOfWeek.SUNDAY
                                        || itemBean.date.dayOfWeek == DayOfWeek.SATURDAY
                                        || itemBean.date.dayOfMonth == 1
                                    ) {
                                        holder.viewRoot.background = null
                                    } else {
                                        holder.viewRoot.setBackgroundResource(R.drawable.bg_select_right)
                                    }
                                }
                                2 -> {
                                    holder.viewContainer.isSelected = false
                                    if ((itemBean.date.dayOfWeek == DayOfWeek.SUNDAY && itemBean.date.dayOfMonth == itemBean.date.lengthOfMonth())
                                        || (itemBean.date.dayOfWeek == DayOfWeek.SATURDAY && itemBean.date.dayOfMonth == 1)
                                    ) {
                                        holder.viewRoot.setBackgroundResource(R.drawable.bg_select_middle2)
                                    } else {
                                        if (itemBean.date.dayOfWeek == DayOfWeek.SUNDAY
                                            || itemBean.date.dayOfMonth == 1
                                        ) {
                                            holder.viewRoot.setBackgroundResource(R.drawable.bg_select_left)
                                        } else if (itemBean.date.dayOfWeek == DayOfWeek.SATURDAY
                                            || itemBean.date.dayOfMonth == itemBean.date.lengthOfMonth()
                                        ) {
                                            holder.viewRoot.setBackgroundResource(R.drawable.bg_select_right)
                                        } else {
                                            holder.viewRoot.setBackgroundResource(R.drawable.bg_select_middle)
                                        }
                                    }
                                }
                                else -> {
                                    holder.viewContainer.isSelected = false
                                    holder.viewRoot.background = null
                                }
                            }
                        }
                    }
                }
            }
            
            override fun getItemCount(): Int {
                return dataList?.size ?: 0
            }
            
            /**
             * 0: start; 1:end, 2:区间
             */
            private fun judgeSelectDate(date: LocalDate): Int {
                if (startDate != null && date.isEqual(startDate)) return 0
                if (endDate != null && date.isEqual(endDate)) return 1
                if (startDate != null && date.isAfter(startDate)
                    && endDate != null && date.isBefore(endDate)
                ) return 2
                return -1
            }
            
            inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                var viewRoot: ViewGroup = itemView.findViewById(R.id.viewRoot)
                var viewContainer: RelativeLayout = itemView.findViewById(R.id.viewContainer)
                var tvDay: TextView = itemView.findViewById(R.id.tvDay)
                var tvExtra: TextView = itemView.findViewById(R.id.tvExtra)
                var tvExtra2: TextView = itemView.findViewById(R.id.tvExtra2)
            }
        }
    }
}