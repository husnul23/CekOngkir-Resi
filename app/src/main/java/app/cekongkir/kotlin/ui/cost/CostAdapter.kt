package app.cekongkir.kotlin.ui.cost

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.cekongkir.R
import app.cekongkir.kotlin.network.responses.CostResponse
import kotlinx.android.synthetic.main.adapter_cost.view.*

class CostAdapter (var results: ArrayList<CostResponse.Rajaongkir.Result>):
        RecyclerView.Adapter<CostAdapter.ViewHolder>(){
    private val TAG = "CostAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.adapter_cost, parent, false
                    )
            )

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.view.text_code.text = result.code
        holder.view.text_name.text = result.name
        holder.view.list_service.apply {
            layoutManager = LinearLayoutManager (context)
            adapter = ServiceAdapter( result.costs )
        }
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

    fun setData(data: List<CostResponse.Rajaongkir.Result>) {
        results.clear()
        results.addAll(data)
        notifyDataSetChanged()
    }
}