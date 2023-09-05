package com.example.bni_test.modal

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.bni_test.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("imageUrl")
fun bindImage(view: ImageView, linkFoto: String?) {
    linkFoto?.let {

        val picassoBuilder = Picasso.Builder(view.context)
        val picasso = picassoBuilder.build()
        picasso
            .load(linkFoto)
            .error(R.drawable.ic_baseline_hide)
            .placeholder(R.drawable.ic_baseline)
            .into(view)
    }
}


@BindingAdapter("ymdText")
fun formatDateYMD(view: TextView, inputDate: String) {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    val date = inputFormat.parse(inputDate)
    view.text = outputFormat.format(date)

}