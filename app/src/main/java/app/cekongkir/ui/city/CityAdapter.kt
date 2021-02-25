package app.cekongkir.ui.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.cekongkir.databinding.AdapterCityBinding
import app.cekongkir.network.response.CityResponse

class CityAdapter(
        val cities: ArrayList<CityResponse.Rajaongkir.Results>,
        val listener: OnAdapterListener
): RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CityAdapter.ViewHolder (
        AdapterCityBinding.inflate( LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CityAdapter.ViewHolder, position: Int) {
        val city = cities[position]
        holder.binding.textName.text = city.city_name
        holder.binding.container.setOnClickListener {
            listener.onClick(city)
        }
    }

    override fun getItemCount() = cities.size

    class ViewHolder(val binding: AdapterCityBinding): RecyclerView.ViewHolder(binding.root)

    interface OnAdapterListener {
        fun onClick(result: CityResponse.Rajaongkir.Results)
    }

    fun setData(data: List<CityResponse.Rajaongkir.Results>) {
        cities.clear()
        cities.addAll( data )
        notifyDataSetChanged()
    }
}