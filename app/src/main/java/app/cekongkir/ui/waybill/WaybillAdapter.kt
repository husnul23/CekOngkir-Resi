package app.cekongkir.ui.waybill

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.cekongkir.R
import app.cekongkir.database.persistence.WaybillEntity
import kotlinx.android.synthetic.main.adapter_waybill.view.*

class WaybillAdapter (var waybills: ArrayList<WaybillEntity>, val listener: OnAdapterListener):
        RecyclerView.Adapter<WaybillAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.adapter_waybill, parent, false
                    )
            )

    override fun getItemCount() = waybills.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val waybill = waybills[position]
        holder.view.text_waybill.text = waybill.waybill
        holder.view.text_status.text = waybill.status
        holder.view.text_courier.text = waybill.courier
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(data: List<WaybillEntity>) {
        waybills.clear()
        waybills.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(waybill: WaybillEntity)
    }
}