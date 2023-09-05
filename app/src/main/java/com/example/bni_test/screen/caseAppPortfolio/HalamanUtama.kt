package com.example.bni_test.screen.caseAppPortfolio

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bni_test.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import org.json.JSONArray


private var dataJson =
    JSONArray("[{\"type\":\"donutChart\",\"data\":[{\"label\":\"Tarik Tunai\",\"percentage\":\"55\",\"data\":[{\"trx_date\":\"21/01/2023\",\"nominal\":1000000},{\"trx_date\":\"20/01/2023\",\"nominal\":500000},{\"trx_date\":\"19/01/2023\",\"nominal\":1000000}]},{\"label\":\"QRIS Payment\",\"percentage\":\"31\",\"data\":[{\"trx_date\":\"21/01/2023\",\"nominal\":159000},{\"trx_date\":\"20/01/2023\",\"nominal\":35000},{\"trx_date\":\"19/01/2023\",\"nominal\":1500}]},{\"label\":\"Topup Gopay\",\"percentage\":\"7.7\",\"data\":[{\"trx_date\":\"21/01/2023\",\"nominal\":200000},{\"trx_date\":\"20/01/2023\",\"nominal\":195000},{\"trx_date\":\"19/01/2023\",\"nominal\":5000000}]},{\"label\":\"Lainnya\",\"percentage\":\"6.3\",\"data\":[{\"trx_date\":\"21/01/2023\",\"nominal\":1000000},{\"trx_date\":\"20/01/2023\",\"nominal\":500000},{\"trx_date\":\"19/01/2023\",\"nominal\":1000000}]}]},{\"type\":\"lineChart\",\"data\":{\"month\":[3,7,8,10,5,10,1,3,5,10,7,7]}}]")


class HalamanUtama : AppCompatActivity() {
    private lateinit var pieChart: PieChart
    private lateinit var lineChart: LineChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.halaman_utama_portofolio)
        findViewById<ComposeView>(R.id.my_composable).setContent {
            GenerateButtonTransaksi()
        }
        pieChart = findViewById(R.id.pieChart)
        lineChart = findViewById(R.id.lineChart)

//        ,
//        PieEntry(calcelOrder.toFloat(), "Order Cancelled")
        setDonutChart()
        setLineChart()

    }

    private fun setLineChart() {
        val jsonObject = dataJson.getJSONObject(1)
        val jsonData = jsonObject.getJSONObject("data")

        val jsonArray = jsonData.getJSONArray("month")
//

//        val legend: Legend = lineChart.legend
//        legend.isEnabled = true
//        legend.textSize = 15f
//
//        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
//        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
//        legend.orientation = Legend.LegendOrientation.HORIZONTAL
//        legend.setDrawInside(false)
//        legend.formSize = 15f
//        legend.xEntrySpace = 10f
//        legend.formToTextSpace = 10f

        val namaBulan = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des"
        )
        val dataVal = ArrayList<Entry>()
        for (i in 0 until jsonArray.length()) {
            dataVal.add(Entry(i.toFloat(), jsonArray.getInt(i).toFloat()))
        }
        val dataSet = LineDataSet(dataVal, "Jumlah Transaksi")
        dataSet.color = ColorTemplate.COLORFUL_COLORS[0]
        dataSet.valueTextSize = 14f
        dataSet.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                // Menggunakan DecimalFormat untuk memformat nilai sebagai bilangan bulat
                val format = java.text.DecimalFormat("###")
                return format.format(value)
            }
        }

        val lineData = LineData(dataSet)

//        val xAxis: XAxis = lineChart.xAxis
//        val bulannya = AxisDateFormatter(namaBulan)
//        xAxis.valueFormatter = bulannya
//        xAxis.position = XAxis.XAxisPosition.BOTTOM
//        xAxis.axisMaximum = 5f
//        xAxis.axisMinimum = -1f
//        xAxis.enableGridDashedLine(10f, 10f, 0f)
//
//
//        val leftAxis: YAxis = lineChart.axisLeft
//        leftAxis.removeAllLimitLines()
//        leftAxis.enableGridDashedLine(10f, 10f, 0f)
//        leftAxis.setDrawZeroLine(false)
//        leftAxis.setDrawLimitLinesBehindData(false)


        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = AxisDateFormatter(namaBulan)
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true

        // Konfigurasi YAxis (sumbu Y)
        val yAxis = lineChart.axisLeft
        yAxis.axisMinimum = 0f


        lineChart.axisRight.isEnabled = true

        lineChart.animateXY(800, 800)
        lineChart.data = lineData
        lineChart.setTouchEnabled(false)
        lineChart.setScaleEnabled(false)
        lineChart.setPinchZoom(false)
        lineChart.isDragEnabled = false
        lineChart.isDoubleTapToZoomEnabled = false
        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = false
        lineChart.invalidate()


    }

    class AxisDateFormatter(private val mValues: Array<String>) : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.e("ER, ", "asdw $index")
            return if (value >= 0 && value.toInt() < mValues.size) {
                mValues[index]
            } else {
                ""
            }
        }
    }

    private fun setDonutChart() {
        val jsonObject = dataJson.getJSONObject(0)
        val jsonArray = jsonObject.getJSONArray("data")
        val pieEntries = ArrayList<PieEntry>()
        val dataLabels = ArrayList<LegendEntry>()

        for (i in 0 until jsonArray.length()) {
            val tempjson = jsonArray.getJSONObject(i)
            pieEntries.add(
                PieEntry(
                    tempjson.getString("percentage").toFloat(), tempjson.getString("label")
                )
            )

            val legendEntry = LegendEntry()
            legendEntry.label = tempjson.getString("label")
            legendEntry.formColor = ColorTemplate.JOYFUL_COLORS[i]
            dataLabels.add(legendEntry)
        }
        val dataSet = PieDataSet(pieEntries, "")
        dataSet.colors = ColorTemplate.JOYFUL_COLORS.toList()

        val pieData = PieData(dataSet)
        pieData.setValueTextSize(12f)
//        val formatter = DecimalFormat("###")
//        pieData.setValueFormatter(object : ValueFormatter() {
//            override fun getFormattedValue(value: Float): String {
//                return formatter.format(value)
//            }
//        })

//        pieData.setValueFormatter { value, _ ->
//            val format = DecimalFormat("##.#")
//            "${format.format(value)}%"
//        }

        // Konfigurasi chart pie
        pieChart.data = pieData
        pieChart.description.isEnabled = false
        pieChart.setDrawEntryLabels(true)
        pieChart.setUsePercentValues(true)
        pieChart.animateY(1000)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.invalidate()
        pieChart.centerText = "Persetase Transaksi"
        pieChart.setCenterTextOffset(0f, 0f)

        val legend = pieChart.legend
        legend.isEnabled = true
        legend.formSize = 12f
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.form = Legend.LegendForm.SQUARE
        legend.setCustom(dataLabels.toTypedArray()) // Mengatur label slice berdasarkan nama dari data JSONArray

    }

}


@Composable
fun GenerateButtonTransaksi() {
    val context = LocalContext.current
    val jsonObject = dataJson.getJSONObject(0)
    val jsonArray = jsonObject.getJSONArray("data")

    Column(
        modifier = Modifier.background(color = colorResource(id = R.color.bg)),
        verticalArrangement = Arrangement.Center, // Menengahkan secara vertikal
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Daftar transaksi")
        // Membuat tombol untuk setiap nama dalam daftar
        for (i in 0 until jsonArray.length()) {
            val tempjson = jsonArray.getJSONObject(i)
            ButtonComponent(
                name = tempjson.getString("label")
            ) {
                val pindah = Intent(context, DetilTransaksi::class.java)
                pindah.putExtra(DetilTransaksi.EXTRA_DATAAA, tempjson.toString())
                context.startActivity(pindah)
            }
        }
    }

}

@Composable
fun ButtonComponent(name: String, onScanClicked: () -> Unit) {
    // Membuat tombol dengan nama sebagai teks
    Button(
        onClick = { onScanClicked() }, modifier = Modifier.padding(4.dp)
    ) {
        Text(text = name)
    }
}

@Preview(showBackground = true)
@Composable
fun testbutton() {
    GenerateButtonTransaksi()
}


