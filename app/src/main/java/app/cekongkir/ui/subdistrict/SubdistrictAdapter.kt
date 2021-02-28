package app.cekongkir.ui.subdistrict

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.cekongkir.databinding.AdapterSubdistrictBinding
import app.cekongkir.network.response.SubDistrictResponse

class SubdistrictAdapter(
        val subDistricts: ArrayList<SubDistrictResponse.Rajaongkir.Results>,
        val listener: OnAdapterListener

) : RecyclerView.Adapter<SubdistrictAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            AdapterSubdistrictBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = subDistricts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subDistrict = subDistricts[position]
        holder.binding.textName.text = subDistrict.subdistrict_name
        holder.binding.container.setOnClickListener {
            listener.onClick(subDistrict)
        }
    }

    class ViewHolder(val binding: AdapterSubdistrictBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnAdapterListener {
        fun onClick(result: SubDistrictResponse.Rajaongkir.Results)
    }

    fun setData(data: List<SubDistrictResponse.Rajaongkir.Results>) {
        subDistricts.clear()
        subDistricts.addAll(data)
        notifyDataSetChanged()
    }
}