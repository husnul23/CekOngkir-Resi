package app.cekongkir.kotlin.ui.waybill

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.cekongkir.R
import app.cekongkir.kotlin.database.persistence.WaybillEntity
import app.cekongkir.kotlin.ui.tracking.TrackingActivity
import app.cekongkir.kotlin.ui.tracking.WaybillViewModel
import kotlinx.android.synthetic.main.fragment_waybill.view.*

class WaybillFragment : Fragment() {

    private lateinit var fragmentView: View
    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(WaybillViewModel::class.java)
    }
    private lateinit var waybillAdapter: WaybillAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_waybill, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListener()
        setupObserver()
    }

    private fun setupRecyclerView(){
        waybillAdapter = WaybillAdapter(arrayListOf(), object : WaybillAdapter.OnAdapterListener {
            override fun onClick(waybillEntity: WaybillEntity) {

            }
        })
        fragmentView.list_waybill.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = waybillAdapter
        }
    }

    private fun setupListener(){
        fragmentView.edit_waybill.setOnClickListener {
            startActivity( Intent(requireContext(), TrackingActivity::class.java))
        }
    }

    private fun setupObserver(){
        viewModel.waybillEntity.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                waybillAdapter.setData( it )
            } else {

            }
        })
    }
}