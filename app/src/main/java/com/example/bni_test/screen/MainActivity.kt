package com.example.bni_test.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bni_test.screen.caseAppPembayaranQRIS.MainPembayaran
import com.example.bni_test.screen.caseAppPortfolio.HalamanUtama
import com.example.bni_test.screen.caseAppPromo.halamanUtamaPromo.HalamanUtamaPromo
import com.example.bni_test.ui.theme.BnitestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BnitestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ButtonScreen()
                }
            }
        }


    }
}

@Composable
fun ButtonScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                context.startActivity(Intent(context, MainPembayaran::class.java))
            },
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text("Aplikasi Mobile App Pembayaran QRIS")
        }

        Button(
            onClick = {
                // Arahkan ke aktivitas selanjutnya
//                val intent = Intent(this@ButtonScreen, NextActivity::class.java)
//                startActivity(intent)
                context.startActivity(Intent(context, HalamanUtamaPromo::class.java))
//                context.startActivity(Intent(context, DetilPromo::class.java))
            },
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text("Aplikasi Mobile App Promo")
        }

        Button(
            onClick = {
                context.startActivity(Intent(context, HalamanUtama::class.java))
            },
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text("Aplikasi Mobile App Portfolio")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonScreenPreview() {
    BnitestTheme {
        ButtonScreen()
    }
}

