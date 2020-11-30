package app.cekongkir.ui.city

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import app.cekongkir.R
import app.cekongkir.network.responses.CityResponse
import kotlinx.android.synthetic.main.adapter_city.view.*
import timber.log.Timber
import kotlin.collections.ArrayList

class CityAdapter (
        var cities: ArrayList<CityResponse.Rajaongkir.Result>,
        var listener: OnAdapterListener
):
        RecyclerView.Adapter<CityAdapter.ViewHolder>(), Filterable{

    private var citiesFilter = ArrayList<CityResponse.Rajaongkir.Result>()

    init {
        citiesFilter = cities
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.adapter_city, parent, false
                    )
            )

    override fun getItemCount() = citiesFilter.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = citiesFilter[position]
        holder.view.text_name.text = city.city_name
        holder.view.text_name.setOnClickListener {
            listener.onClick(city)
        }
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

    fun setData(data: List<CityResponse.Rajaongkir.Result>) {
        cities.clear()
        cities.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(result: CityResponse.Rajaongkir.Result)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                Timber.d("charSearch: $charSearch")
                if (charSearch.isEmpty()) {
                    citiesFilter = cities
                } else {
                    val citiesFiltered = ArrayList<CityResponse.Rajaongkir.Result>()
                    for (city in cities) {
                        if (city.city_name.toLowerCase().contains(charSearch.toLowerCase())) {
                            citiesFiltered.add(city)
                        }
                    }
                    citiesFilter = citiesFiltered
                }
                val citiesFilteredResult = FilterResults()
                citiesFilteredResult.values = citiesFilter
                return citiesFilteredResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                citiesFilter = results?.values as ArrayList<CityResponse.Rajaongkir.Result>
                notifyDataSetChanged()
            }

        }
    }
}