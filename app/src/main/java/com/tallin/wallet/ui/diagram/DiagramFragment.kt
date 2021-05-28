package com.tallin.wallet.ui.diagram

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.get
import com.tallin.moneybee.utils.stub.StubNavigator
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.utils.stub.StubViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_diagram.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class DiagramFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_diagram
    override val viewModel: StubViewModel by viewModel()
    override val navigator: StubNavigator = get()
    private val charts = arrayOfNulls<LineChart>(1)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        periodHandler()
        ivDiagramBack.setOnClickListener {
            activity?.onBackPressed()
        }

        drawDiagram()
    }


    fun drawDiagram(){
        charts[0] = chart1

        for (i in charts.indices) {
            val data = getData(36, 100f)

            // add some transparency to the color with "& 0x90FFFFFF"
            setupChart(charts[i], data, colors[i % colors.size])
        }
    }

    private val colors = intArrayOf(Color.rgb(137, 230, 81))

    private fun setupChart(chart: LineChart?, data: LineData, color: Int) {
//        (data.getDataSetByIndex(0) as LineDataSet).circleHoleColor = color

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





    fun periodHandler(){
        llDiagramPeriod.get(0).isActivated = true

        for (i in 0 until llDiagramPeriod.childCount){
            llDiagramPeriod.get(i).setOnClickListener {
                    for(j in 0 until llDiagramPeriod.childCount){
                        val period = llDiagramPeriod.get(j) as TextView
                        period.isActivated = false
                    }
                it.isActivated = true
            }
        }
    }

}
