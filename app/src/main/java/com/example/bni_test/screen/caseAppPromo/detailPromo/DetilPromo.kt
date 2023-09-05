package com.example.bni_test.screen.caseAppPromo.detailPromo

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bni_test.R
import com.example.bni_test.screen.caseAppPortfolio.GenerateButtonTransaksi
import com.example.bni_test.ui.theme.BnitestTheme
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


class DetilPromo : AppCompatActivity()  {
    companion object {
        const val EXTRA_Tittle = "TittleEXtra"
        const val EXTRA_Desc = "DescExtra"
        const val EXTRA_Url = "UrlExtra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val tittle = intent.getStringExtra(EXTRA_Tittle)
        val imageUrl = intent.getStringExtra(EXTRA_Url).toString()
        val title = intent.getStringExtra(EXTRA_Tittle).toString()
        val body = intent.getStringExtra(EXTRA_Desc).toString()

        setContentView(R.layout.halaman_detil_promo)
        findViewById<ComposeView>(R.id.my_composable).setContent {
            DetilPromo(title,body )
        }

        val imgaeBanner = findViewById<ImageView>(R.id.image)
        val picassoBuilder = Picasso.Builder(this)
        val picasso = picassoBuilder.build()
        picasso
            .load(imageUrl)
            .error(R.drawable.ic_baseline_hide)
            .placeholder(R.drawable.ic_baseline)
            .into(imgaeBanner)

        if (supportActionBar != null)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//
//        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
//        val date = inputFormat.parse(inputDate)
//        view.text = outputFormat.format(date)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

}

@Composable
fun DetilPromo( title: String, body: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding( bottom = 16.dp)
    ) {

        Text(

            text = title,
            Modifier.padding(8.dp),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )


        // Body text
        Text(
            text = body,
            Modifier.padding(8.dp),
            style = TextStyle(fontSize = 12.sp)
        )
    }

}

