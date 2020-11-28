package app.cekongkir.ui.trackingresult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.R
import app.cekongkir.network.Resource
import app.cekongkir.ui.tracking.WaybillViewModel
import app.cekongkir.utils.swipeHide
import app.cekongkir.utils.swipeShow
import kotlinx.android.synthetic.main.fragment_tracking_result.view.*

class TrackingResultFragment : Fragment() {

    private lateinit var fragmentView: View
    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(WaybillViewModel::class.java)
    }
    private val waybill by lazy { requireArguments().getString("waybill")!! }
    private val courier by lazy { requireArguments().getString("courier")!! }
    private lateinit var manifestAdapter: TrackingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_tracking_result, container, false)
        return fragmentView
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchWaybill( waybill, courier )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListener()
        setupObserver()
    }

    private fun setupRecyclerView(){
        manifestAdapter = TrackingAdapter(arrayListOf())
        fragmentView.list_manifest.adapter = manifestAdapter
    }

    private fun setupListener(){
        fragmentView.refresh_waybill.setOnRefreshListener {
            viewModel.fetchWaybill( waybill, courier )
        }
    }

    private fun setupObserver(){
        viewModel.waybillResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    fragmentView.refresh_waybill.swipeShow()
                }
                is Resource.Success -> {
                    fragmentView.refresh_waybill.swipeHide()
                    val data = it.data!!.rajaongkir.result
                    fragmentView.text_status.text = data.delivery_status.status
                    fragmentView.text_receiver.text = data.delivery_status.pod_receiver
                    fragmentView.text_date.text = "${data.delivery_status.pod_date} ${data.delivery_status.pod_time}"
                    manifestAdapter.setData( data.manifest )
                }
                is Resource.Error -> {
                    fragmentView.refresh_waybill.swipeHide()
                }
            }
        })
    }
}