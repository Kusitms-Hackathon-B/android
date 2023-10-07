package com.example.jjikmuk.ui.mypage

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.jjikmuk.R
import com.example.jjikmuk.databinding.FragmentMypageBinding
import com.example.jjikmuk.util.BaseFragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class MypageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {
    private val sdf = SimpleDateFormat("yyyy. MM")
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val currentDate = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    private lateinit var adapter: CalendarAdapter
    private val calendarList2 = ArrayList<CalendarDateModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBarChart()

        setUpAdapter()
        setUpClickListener()
        setUpCalendar()

    }

    private fun setUpClickListener() {
        binding.ivCalendarNext.setOnClickListener {
            cal.add(Calendar.MONTH, 1)
            setUpCalendar()
        }
        binding.ivCalendarPrevious.setOnClickListener {
            cal.add(Calendar.MONTH, -1)
            if (cal == currentDate)
                setUpCalendar()
            else
                setUpCalendar()
        }
    }

    /**
     * Setting up adapter for recyclerview
     */
    private fun setUpAdapter() {
//        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.single_calendar_margin)
//        binding.recyclerView.addItemDecoration(HorizontalItemDecoration(spacingInPixels))
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)
        adapter = CalendarAdapter { calendarDateModel: CalendarDateModel, position: Int ->
            calendarList2.forEachIndexed { index, calendarModel ->
                calendarModel.isSelected = index == position
            }
            adapter.setData(calendarList2)
        }
        binding.recyclerView.adapter = adapter
    }

    /**
     * Function to setup calendar for every month
     */
    private fun setUpCalendar() {
        val calendarList = ArrayList<CalendarDateModel>()
        binding.tvDateMonth.text = sdf.format(cal.time)
        val monthCalendar = cal.clone() as Calendar
        val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        dates.clear()
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        while (dates.size < maxDaysInMonth) {
            dates.add(monthCalendar.time)
            calendarList.add(CalendarDateModel(monthCalendar.time))
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        calendarList2.clear()
        calendarList2.addAll(calendarList)
        adapter.setData(calendarList)
    }


    private fun setupBarChart() {
        val entries1 = ArrayList<BarEntry>()
        val entries2 = ArrayList<BarEntry>()
        val entries3 = ArrayList<BarEntry>()
        
        entries1.add(BarEntry(0f, 10f))
        entries1.add(BarEntry(1f, 30f))
        entries1.add(BarEntry(2f, 15f))
        entries1.add(BarEntry(3f, 15f))
        entries1.add(BarEntry(4f, 7f))
        entries1.add(BarEntry(5f, 65f))
        entries1.add(BarEntry(6f, 35f))

        entries2.add(BarEntry(0f, 10f))
        entries2.add(BarEntry(1f, 25f))
        entries2.add(BarEntry(2f, 5f))
        entries2.add(BarEntry(3f, 55f))
        entries2.add(BarEntry(4f, 25f))
        entries2.add(BarEntry(5f, 80f))
        entries2.add(BarEntry(6f, 27f))

        entries3.add(BarEntry(0f, 44f))
        entries3.add(BarEntry(1f, 20f))
        entries3.add(BarEntry(2f, 32f))
        entries3.add(BarEntry(3f, 84f))
        entries3.add(BarEntry(4f, 12f))
        entries3.add(BarEntry(5f, 64f))
        entries3.add(BarEntry(6f, 33f))

        val labels = ArrayList<String>()
        labels.add("월")
        labels.add("화")
        labels.add("수")
        labels.add("목")
        labels.add("금")
        labels.add("토")
        labels.add("일")

        val set1 = BarDataSet(entries1, "탄수화물")
        set1.color = ContextCompat.getColor(requireContext(),R.color.cal_good)
        val set2 = BarDataSet(entries2, "단백질")
        set2.color = ContextCompat.getColor(requireContext(),R.color.chart_yellow)
        val set3 = BarDataSet(entries3, "지방")
        set3.color = ContextCompat.getColor(requireContext(),R.color.chart_blue)
        val barData = BarData(set1, set2, set3)

        binding.chart.run {
            data = barData   
        }

        barData.barWidth = 0.14f
        barData.groupBars(0f, 0.4f, 0.06f)

        binding.chart.setDrawGridBackground(false)
        binding.chart.description.isEnabled = false
        binding.chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.chart.axisRight.isEnabled = false
        binding.chart.isDoubleTapToZoomEnabled = false
        binding.chart.animateY(1000)

        binding.chart.axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)

        // Set chart padding
        binding.chart.extraTopOffset = 10f
        binding.chart.extraBottomOffset = 10f
        binding.chart.extraLeftOffset = 5f
        binding.chart.extraRightOffset = 5f

        // Customize x-axis labels
        binding.chart.xAxis.run {
            valueFormatter = IndexAxisValueFormatter(labels)
            position = XAxis.XAxisPosition.BOTTOM
//            isGranularityEnabled = true
            setCenterAxisLabels(true)
            granularity = 0.5f
            axisMinimum = 0f
            axisMaximum = 101.0f
        }

        binding.chart.axisLeft.run {
            axisMinimum = 0f
            setDrawGridLines(false)
        }
        binding.chart.setTouchEnabled(false)
        binding.chart.fitScreen()
        binding.chart.setVisibleXRangeMaximum(7f)
        binding.chart.invalidate()

    }

}