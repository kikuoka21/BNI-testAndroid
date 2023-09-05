package com.example.bni_test.screen.caseAppPembayaranQRIS

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.bni_test.modal.DatabaseHelper
import com.example.bni_test.modal.Transaksi
import com.example.bni_test.ui.theme.BnitestTheme
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

class TransaksiQR : ComponentActivity() {
    companion object {
        const val ISI_QR = "isianQRnya"
    }

    private lateinit var data: List<String>
    private lateinit var databaseHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isiQR = intent.getStringExtra(ISI_QR)

        data = splitString(isiQR!!)
        var noId = 0

        if (data[0] == "") {
            showMessage("QR yang terscan tidak sesuai. $isiQR")
        } else {
            databaseHelper = DatabaseHelper(this)

            if (databaseHelper.isBisaTransaksi(1, data[3].toLong())) {
                val timeStamp = System.currentTimeMillis()
                val tipeTransaksi = 1
                val nominal = data[3].toLong()
                val keterangan = "QRIS${data[0]}.${data[1]}.${data[2]} "
                val newRecord = databaseHelper.addRecord(
                    timeStamp,
                    tipeTransaksi,
                    nominal,
                    keterangan
                )
                if (newRecord != -1L) {
                    val transaksi =
                        Transaksi(newRecord, timeStamp, tipeTransaksi, nominal, keterangan, 0)

                    setContent {
                        BnitestTheme {
                            HomePage(transaksi) {
                                finish()
                            }
                        }
                    }

                } else
                    showMessage("Transaksi Gagal dilakukan")
            } else
                showMessage("Saldo Anda Tidak Mencukupi ${data[3].toLong()}")
        }


//        setContent {
//            HomePage {
//                lifecycleScope.launch {
//                    resultLauncher.launch(Intent(this@TransaksiQR, ScanQR::class.java))
//                }
//            }
//
//        }


    }

    private fun showMessage(message: String) {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Informasi")
        alertDialog.setMessage(message)
        alertDialog.setCancelable(false)
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.setOnDismissListener {
            finish()
        }
        alertDialog?.show()
    }

    private fun splitString(inputString: String): List<String> {
        val parts = inputString.split('.')
        return when {
            parts.size >= 4 -> {
                val id = parts[0]
                val kodeRequest = parts[1]
                val namaTempat = parts.subList(2, parts.size - 1).joinToString(".")
                val tagian = parts.last()
                listOf(id, kodeRequest, namaTempat, tagian)
            }
            else -> listOf("", "", " ", " ") // String tidak sesuai format yang diharapkan
        }
    }


}


@Composable
fun HomePage(data: Transaksi, onScanClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Transaksi Berhasil",
            style = MaterialTheme.typography.h4
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Nama Merchant",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = " : ", style = MaterialTheme.typography.body1
            )
            Text(
                text = data.keterangan,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.weight(2f)
            )
        }

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

            val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            val nominalText = formatter.format(data.nominal)
            Text(
                text = nominalText,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.weight(2f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "ID transaksi",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = " : ", style = MaterialTheme.typography.body1, modifier = Modifier
            )
            Text(
                text = "${data.id}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.weight(2f)
            )
        }
        Button(onClick = {
            onScanClicked()
        }) {
            Text(text = "Tutup")
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun TestResultQR() {
    BnitestTheme {
        HomePage(data = Transaksi(1, 1693845434, 2, 20000, "Setro Tunai", 300000)) {
        }
    }

}
