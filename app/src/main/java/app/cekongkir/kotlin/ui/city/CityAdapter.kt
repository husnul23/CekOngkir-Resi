package app.cekongkir.kotlin.ui.city

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.cekongkir.R
import app.cekongkir.kotlin.remote.responses.CityResponse
import kotlinx.android.synthetic.main.adapter_subdistrict.view.*

class CityAdapter (var results: ArrayList<CityResponse.Result>, var listener: OnAdapterListener):
        RecyclerView.Adapter<CityAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.adapter_subdistrict, parent, false
                    )
            )

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.view.text_name.text = result.city_name
        holder.view.text_name.setOnClickListener {
            listener.onClick(result)
        }

    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
    }

    fun setData(data: List<CityResponse.Result>) {
        results.clear()
        results.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(result: CityResponse.Result)
    }
}