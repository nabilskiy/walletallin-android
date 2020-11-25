package com.coinsliberty.wallet.ui.demo

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.coinsliberty.wallet.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_demo.*
import kotlin.collections.MutableMap.MutableEntry


class DemoActivity : AppCompatActivity() {
    val barEntries = ArrayList<BarEntry>()
    val list = ArrayList<Entry>()

    private var chart: LineChart? = null
    private var seekBarX: SeekBar? = null
    private  var seekBarY:SeekBar? = null
    private var tvX: TextView? = null
    private  var tvY:TextView? = null

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

//        barEntries.add(BarEntry(34F, 53F))
//        barEntries.add(BarEntry(44F, 63F))
//        barEntries.add(BarEntry(54F, 73F))
//
//        val barDataSet = BarDataSet(barEntries, "Growth")
//            barDataSet.setColor(R.color.colorPrimary)
//
//        val barData = BarData(barDataSet)
//            barData.barWidth = 0.9F
//
        val descr = Description()
            descr.text = "Growth for Now!!!"
        list.add(Entry(34F, 43F))
        list.add(Entry(44F, 53F))
        list.add(Entry(54F, 63F))
        list.add(Entry(64F, 73F))
        list.add(Entry(74F, 83F))

        val dataSet = LineDataSet(list, "CharList")

            dataSet.color = R.color.colorPrimary
            dataSet.lineWidth = 0.4F
            dataSet.fillColor = ColorTemplate.getHoloBlue()

        val lineData = LineData(dataSet)

            lineData.setValueTextColor(Color.WHITE)
            lineData.setValueTextSize(9f)


//        barChart.animateY(3000)
//       // barChart.data = barData
//        barChart.setTouchEnabled(true)
//        barChart.setDragEnabled(true);
//        barChart.setScaleEnabled(true);
//        barChart.description = descr
//        barChart.invalidate()

        tvX = tvXMax
        tvY = findViewById(R.id.tvYMax);

        seekBarX = seekBar1
//        seekBarX.setOnSeekBarChangeListener(this);

        seekBarY = findViewById(R.id.seekBar2);
//        seekBarY.setOnSeekBarChangeListener(this);

        chart = chart1
  //      chart.setOnChartValueSelectedListener(this);

        // no description text
        chart1.description = descr

        // enable touch gestures
        chart1.setTouchEnabled(true);

        chart1.dragDecelerationFrictionCoef = 0.9f;

        // enable scaling and dragging
        chart1.isDragEnabled = true;
        chart1.setScaleEnabled(true);
        chart1.setDrawGridBackground(false);
        chart1.isHighlightPerDragEnabled = true;

        // if disabled, scaling can be done on x- and y-axis separately
        chart1.setPinchZoom(true);

        // set an alternative background color
        chart1.setBackgroundColor(R.color.colorPrimary);

        // add data
        seekBar1.progress = 20;
        seekBar2.progress = 30;


        val llXAxis = LimitLine(9f, "Index 10")
            llXAxis.lineWidth = 4f
            llXAxis.enableDashedLine(10f, 10f, 0f)
            llXAxis.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
            llXAxis.textSize = 10f

        chart1.animateX(1500);
        chart1.data = lineData
        chart1.invalidate()
    }

}