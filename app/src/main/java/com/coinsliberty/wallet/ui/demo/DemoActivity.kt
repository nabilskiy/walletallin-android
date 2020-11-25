package com.coinsliberty.wallet.ui.demo

import android.graphics.Color
import android.graphics.Typeface
import android.media.effect.Effect
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.coinsliberty.wallet.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


class DemoActivity : AppCompatActivity(){

    private val charts = arrayOfNulls<LineChart>(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_demo)
        title = "LineChartActivityColored"

        charts[0] = findViewById(R.id.chart1)

        for (i in charts.indices) {
            val data = getData(36, 100f)

            // add some transparency to the color with "& 0x90FFFFFF"
            setupChart(charts[i], data, colors[i % colors.size])
        }
    }

    private val colors = intArrayOf(Color.rgb(137, 230, 81))

    private fun setupChart(chart: LineChart?, data: LineData, color: Int) {
        (data.getDataSetByIndex(0) as LineDataSet).circleHoleColor = color

        // no description text
        chart!!.description.isEnabled = false

        // chart.setDrawHorizontalGrid(false);
        //
        // enable / disable grid background
        chart.setDrawGridBackground(false)

        // enable touch gestures
        chart.setTouchEnabled(true)

        // enable scaling and dragging
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false)
       // chart.setBackgroundResource(R.color.walletLightBlue)

        // set custom chart offsets (automatic offset calculation is hereby disabled)
        chart.setViewPortOffsets(10f, 0f, 10f, 0f)

        // add data
        chart.data = data

        // get the legend (only possible after setting data)
        val l = chart.legend
        l.isEnabled = false
        chart.axisLeft.isEnabled = false
        chart.axisLeft.spaceTop = 40f
        chart.axisLeft.spaceBottom = 40f
        chart.axisRight.isEnabled = false
        chart.xAxis.isEnabled = false

        // animate calls invalidate()...
        chart.animateX(2500)
    }

    private fun getData(count: Int, range: Float): LineData {
        //val values: ArrayList<Map.Entry<Any, Any>> = ArrayList()
        val values = ArrayList<Entry>()

        for (i in 0 until count) {
            val data = (Math.random() * range).toFloat() + 3
            values.add(Entry(i.toFloat(), data))
        }

        // create a dataset and give it a type
        val set1 = LineDataSet(values, "DataSet 1")

        set1.lineWidth = 1.75f
        //set1.setDrawCircleHole(true)
  //      set1.cubicIntensity = 1F
//        set1.circleRadius = 5f
 //       set1.circleHoleRadius = 2.5f
        set1.color = Color.WHITE
//        set1.setCircleColor(Color.WHITE)
        set1.setDrawValues(false)
        set1.setDrawFilled(true)
        set1.setColors(R.color.colorPrimary)
        set1.fillAlpha = 110;


        // create a data object with the data sets
        return LineData(set1)
    }

}




//
//class DemoActivity : AppCompatActivity() {
//    val barEntries = ArrayList<BarEntry>()
//    val list = ArrayList<Entry>()
//
//    private var chart: LineChart? = null
//    private var seekBarX: SeekBar? = null
//    private  var seekBarY:SeekBar? = null
//    private var tvX: TextView? = null
//    private  var tvY:TextView? = null
//
//    @SuppressLint("ResourceAsColor")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_demo)
//
////        barEntries.add(BarEntry(34F, 53F))
////        barEntries.add(BarEntry(44F, 63F))
////        barEntries.add(BarEntry(54F, 73F))
////
////        val barDataSet = BarDataSet(barEntries, "Growth")
////            barDataSet.setColor(R.color.colorPrimary)
////
////        val barData = BarData(barDataSet)
////            barData.barWidth = 0.9F
////
//        val descr = Description()
//            descr.text = "Growth for Now!!!"
//        list.add(Entry(34F, 43F))
//        list.add(Entry(44F, 53F))
//        list.add(Entry(54F, 63F))
//        list.add(Entry(64F, 73F))
//        list.add(Entry(74F, 83F))
//
//        val dataSet = LineDataSet(list, "CharList")
//
//            dataSet.color = R.color.colorPrimary
//            dataSet.lineWidth = 0.4F
//            dataSet.fillColor = ColorTemplate.getHoloBlue()
//
//        val lineData = LineData(dataSet)
//
//            lineData.setValueTextColor(Color.WHITE)
//            lineData.setValueTextSize(9f)
//
//
////        barChart.animateY(3000)
////       // barChart.data = barData
////        barChart.setTouchEnabled(true)
////        barChart.setDragEnabled(true);
////        barChart.setScaleEnabled(true);
////        barChart.description = descr
////        barChart.invalidate()
//
//        tvX = tvXMax
//        tvY = findViewById(R.id.tvYMax);
//
//        seekBarX = seekBar1
////        seekBarX.setOnSeekBarChangeListener(this);
//
//        seekBarY = findViewById(R.id.seekBar2);
////        seekBarY.setOnSeekBarChangeListener(this);
//
//        chart = chart1
//  //      chart.setOnChartValueSelectedListener(this);
//
//        // no description text
//        chart1.description = descr
//
//        // enable touch gestures
//        chart1.setTouchEnabled(true);
//
//        chart1.dragDecelerationFrictionCoef = 0.9f;
//
//        // enable scaling and dragging
//        chart1.isDragEnabled = true;
//        chart1.setScaleEnabled(true);
//        chart1.setDrawGridBackground(false);
//        chart1.isHighlightPerDragEnabled = true;
//
//        // if disabled, scaling can be done on x- and y-axis separately
//        chart1.setPinchZoom(true);
//
//        // set an alternative background color
//        chart1.setBackgroundColor(R.color.colorPrimary);
//
//        // add data
//        seekBar1.progress = 20;
//        seekBar2.progress = 30;
//
//
//        val llXAxis = LimitLine(9f, "Index 10")
//            llXAxis.lineWidth = 4f
//            llXAxis.enableDashedLine(10f, 10f, 0f)
//            llXAxis.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
//            llXAxis.textSize = 10f
//
//        chart1.animateX(1500);
//        chart1.data = lineData
//        chart1.invalidate()
//    }
//
//}