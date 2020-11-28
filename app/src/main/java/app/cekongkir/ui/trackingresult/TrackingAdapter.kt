package app.cekongkir.ui.trackingresult

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.cekongkir.R
import app.cekongkir.network.responses.WaybillResponse
import kotlinx.android.synthetic.main.adapter_tracking.view.*

class TrackingAdapter (var manifests: ArrayList<WaybillResponse.Rajaongkir.Result.Manifest>):
        RecyclerView.Adapter<TrackingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.adapter_tracking, parent, false
                    )
            )

    override fun getItemCount() = manifests.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val manifest = manifests[position]
        holder.view.text_date.text = manifest.manifest_date
        holder.view.text_description.text = manifest.manifest_description
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(data: List<WaybillResponse.Rajaongkir.Result.Manifest>) {
        manifests.clear()
        manifests.addAll(data)
        notifyDataSetChanged()
    }
}