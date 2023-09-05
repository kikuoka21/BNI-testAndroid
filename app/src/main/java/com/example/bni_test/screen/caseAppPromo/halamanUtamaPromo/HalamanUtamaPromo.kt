package com.example.bni_test.screen.caseAppPromo.halamanUtamaPromo

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bni_test.R
import com.example.bni_test.databinding.HalamanPromoBinding
import com.example.bni_test.modal.ModelPromo
import com.example.bni_test.screen.caseAppPromo.detailPromo.DetilPromo

class HalamanUtamaPromo : AppCompatActivity(), AdapterPromo.BtnSourcesListener {
    private val viewModel: PromoViewModel by viewModels {
        ViewModelFactoryPromo(application)
    }

    private lateinit var binding: HalamanPromoBinding
    private lateinit var progressBar: Dialog
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterPromo: AdapterPromo
    private var arrayList = ArrayList<ModelPromo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.halaman_promo)
        binding.lifecycleOwner = this

        initProgresDialog()
        initRecycleview()

        iniObserver()
    }

    private fun initRecycleview() {
        recyclerView = binding.recycleSources
        adapterPromo = AdapterPromo(this, arrayList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterPromo

    }

    private fun initProgresDialog() {
        progressBar = Dialog(this)
        progressBar.setCancelable(false)
        progressBar.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressBar.setContentView(R.layout.custom_progres_bar)
        progressBar.window?.setBackgroundDrawableResource(android.R.color.transparent)

    }

    private fun iniObserver() {

        viewModel.volleyRun.observe(this) {
            if (it)
                progressBar.show()
            else
                progressBar.dismiss()
        }
        viewModel.promos.observe(this){
            arrayList.clear()
            arrayList.addAll(it)
            adapterPromo.notifyItemRangeChanged(0, arrayList.size)
        }
        viewModel.runVolleySources()
    }

    override fun pindahActivity(model: ModelPromo) {
        val intent = Intent(this, DetilPromo::class.java)
        intent.putExtra(DetilPromo.EXTRA_Url, model.UrlBannerMedium)
        intent.putExtra(DetilPromo.EXTRA_Tittle, model.name)
        intent.putExtra(DetilPromo.EXTRA_Desc, model.desc)
        startActivity(intent)
    }
}