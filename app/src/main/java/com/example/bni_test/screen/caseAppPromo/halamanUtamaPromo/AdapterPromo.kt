package com.example.bni_test.screen.caseAppPromo.halamanUtamaPromo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bni_test.databinding.RowPromoBannerBinding
import com.example.bni_test.modal.ModelPromo

class AdapterPromo(
    private val jembatannya: BtnSourcesListener,
    private val arrayList: ArrayList<ModelPromo>
) :
    RecyclerView.Adapter<PromoViewHolder>() {

    private lateinit var binding: RowPromoBannerBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoViewHolder {
        binding = RowPromoBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PromoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PromoViewHolder, position: Int) {
        val sourcesnya = arrayList[position]
        holder.bind(sourcesnya, jembatannya)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


    interface BtnSourcesListener {
        fun pindahActivity(model: ModelPromo)
    }
}

class PromoViewHolder(private val binding: RowPromoBannerBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(model: ModelPromo, itemClick: AdapterPromo.BtnSourcesListener) {

        binding.modelnya = model
        binding.itemRow.setOnClickListener {
            itemClick.pindahActivity(model)
        }

    }
}
