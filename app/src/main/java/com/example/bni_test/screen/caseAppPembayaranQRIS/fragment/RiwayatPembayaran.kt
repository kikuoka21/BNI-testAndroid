package com.example.bni_test.screen.caseAppPembayaranQRIS.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.bni_test.modal.DatabaseHelper
import com.example.bni_test.modal.Transaksi
import com.example.bni_test.R
import com.example.bni_test.ui.theme.BnitestTheme
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class RiwayatPembayaran : Fragment() {


    private lateinit var databaseHelper: DatabaseHelper // Gantilah dengan nama database Anda

    //    private lateinit var composeView: ComposeView
    override fun onAttach(context: Context) {
        super.onAttach(context)
        databaseHelper = DatabaseHelper(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_riwayat_transaksi, container, false)

//        return ComposeView(requireContext()).apply {
//            setContent {
//                BnitestTheme {
//
//                    MyFragmentContent(list = listTransaksi.value)
//
//                }
//
//            }
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<ComposeView>(R.id.my_composable).setContent {
            MyFragmentContent(list = databaseHelper.getAllTransactions())
        }


    }
}


@Composable
fun MyFragmentContent(list: List<Transaksi>) {
//        for (activity in transaksiList) {
//            RowTransaksi(transaksi = activity)
//        }
    Column {
        for (datanya in list) {
            RowTransaksi(transaksi = datanya)
        }
    }

//    LazyColumn {
//        items(list.size){ke->
//            RowTransaksi(transaksi = list[ke])
//        }
//    }
}

@Composable
fun RowTransaksi(transaksi: Transaksi) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, bottom = 8.dp, top = 4.dp),
        elevation = 4.dp,
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = transaksi.keterangan, style = MaterialTheme.typography.h6
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Nominal dan Sisa Uang
            val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            val nominalText = if (transaksi.tipeMutasi == 1) {
                "- ${formatter.format(transaksi.nominal)}"
            } else {
                formatter.format(transaksi.nominal)
            }
            val saldoText = formatter.format(transaksi.saldo)
            val textColor = if (transaksi.tipeMutasi == 1) Color.Red else Color.Black

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Nominal",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = " : ", style = MaterialTheme.typography.body1
                )
                Text(
                    text = nominalText,
                    style = MaterialTheme.typography.body1,
                    color = textColor,
                    modifier = Modifier.weight(2f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Tanggal",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = " : ", style = MaterialTheme.typography.body1
                )
                Text(
                    text = formatTimestamp(transaksi.timeStamp),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.weight(2f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Saldo",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = " : ", style = MaterialTheme.typography.body1, modifier = Modifier
                )
                Text(
                    text = saldoText,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.weight(2f)
                )
            }
        }
    }
}

@Composable
private fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale.getDefault())
    val date = Date(timestamp)
    return sdf.format(date)
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun TestCardDebet() {
    Column(Modifier.background(Color.Black)) {
        RowTransaksi(transaksi = Transaksi(1, 1693845434, 1, 20000, "merchant", 300000))
    }

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun TestCardKredit() {

    BnitestTheme {
        Column {
            RowTransaksi(transaksi = Transaksi(1, 1693845434, 2, 20000, "Setro Tunai", 300000))
            RowTransaksi(transaksi = Transaksi(1, 1693845434, 2, 20000, "Setro Tunai", 300000))
            RowTransaksi(transaksi = Transaksi(1, 1693845434, 2, 20000, "Setro Tunai", 300000))

        }
    }
}