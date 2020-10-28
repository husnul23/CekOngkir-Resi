package app.cekongkir.kotlin.ui.cost

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.cekongkir.R
import app.cekongkir.kotlin.remote.responses.CostResponse
import kotlinx.android.synthetic.main.adapter_service.view.*

class ServiceAdapter (var costs: List<CostResponse.Rajaongkir.Result.Cost>):
        RecyclerView.Adapter<ServiceAdapter.ViewHolder>(){
    private val TAG = "CostAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.adapter_service, parent, false
                    )
            )

    override fun getItemCount() = costs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cost = costs[position]
        holder.view.text_service.text = cost.service
        holder.view.text_description.text = cost.description
        holder.view.text_value.text = cost.cost[0].value.toString()
        holder.view.text_etd.text = cost.cost[0].etd

    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)
}