package com.example.bni_test.screen.caseAppPortfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bni_test.R
import com.example.bni_test.ui.theme.BnitestTheme
import org.json.JSONObject
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class DetilTransaksi : ComponentActivity() {
    companion object {
        const val EXTRA_DATAAA = "datanya"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            BnitestTheme() {

                TampilanDetilTransaksi(datastring = intent.getStringExtra(EXTRA_DATAAA).toString())
            }

        }


    }

}

@Composable
fun TampilanDetilTransaksi(datastring: String) {
    val jsonObject = JSONObject(datastring)

    Column(
        modifier = Modifier.background(color = colorResource(id = R.color.bg)).padding(top = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = jsonObject.getString("label"), style = TextStyle(fontSize = 20.sp))
        val jsonArray = jsonObject.getJSONArray("data")

        for (i in 0 until jsonArray.length()) {
            val tempjson = jsonArray.getJSONObject(i)
            val backgroundColor = if (i % 2 == 0) {
                colorResource(id = R.color.bg_ganjil)
            } else {
                colorResource(id = R.color.bg_genap)
            }


            StudentListItem(
                date = tempjson.getString("trx_date"),
                nominal = tempjson.getString("nominal"),
                backgroundColor = backgroundColor
            )
        }
    }

}

@Composable
fun StudentListItem(date: String, nominal: String, backgroundColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(backgroundColor),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Tanggal: ${formatDate(date)}", style = TextStyle(fontSize = 18.sp))
            Text(
                text = "Nominal: ${formatToRupiah(nominal.toInt())}",
                style = TextStyle(fontSize = 16.sp)
            )
        }
    }
}

fun formatToRupiah(number: Int): String {
    val formatter = DecimalFormat.getCurrencyInstance() as DecimalFormat
    val symbols = formatter.decimalFormatSymbols

    symbols.currencySymbol = "Rp"
    symbols.monetaryDecimalSeparator = ','
    symbols.groupingSeparator = '.'

    formatter.decimalFormatSymbols = symbols
    formatter.maximumFractionDigits = 0
    return formatter.format(number)
}

fun formatDate(inputDate: String): String {

    val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    val date = inputFormat.parse(inputDate)
    return outputFormat.format(date)

}

@Preview(showBackground = true)
@Composable
fun testViewTampilanDetil() {
    TampilanDetilTransaksi(datastring = "{\"label\":\"Tarik Tunai\",\"percentage\":\"55\",\"data\":[{\"trx_date\":\"21/01/2023\",\"nominal\":1000000},{\"trx_date\":\"20/01/2023\",\"nominal\":500000},{\"trx_date\":\"19/01/2023\",\"nominal\":1000000}]}")
}