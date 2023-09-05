package com.example.bni_test.screen.caseAppPembayaranQRIS.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.bni_test.modal.DatabaseHelper
import java.text.NumberFormat
import java.util.*
import androidx.compose.runtime.LaunchedEffect as LaunchedEffect1

class Beranda : Fragment() {
    private var saldo by mutableStateOf(0)
    private lateinit var databaseHelper: DatabaseHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)

        databaseHelper = DatabaseHelper(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Saldo Anda",
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = formatToRupiah(saldo),
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.Bold,

                        )
                    Button(
                        onClick = {
                            val test = databaseHelper.getAllTransactions()
                            for (aa in test){
                                Log.e("ER", aa.toString())
                            }
                        },
                    ) {
                        Text(text = "print all transaksi ")
                    }
                }
            }
        }
    }

    private fun formatToRupiah(value: Int): String {
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(value.toLong())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saldo = databaseHelper.findSaldoTerakhir()
    }
}