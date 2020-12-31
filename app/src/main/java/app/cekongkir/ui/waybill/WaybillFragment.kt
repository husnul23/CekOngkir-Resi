package app.cekongkir.ui.waybill

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
import app.cekongkir.database.persistence.WaybillEntity
import app.cekongkir.databinding.FragmentWaybillBinding
import app.cekongkir.ui.tracking.TrackingActivity
import app.cekongkir.ui.tracking.WaybillViewModel

class WaybillFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(WaybillViewModel::class.java) }
    private lateinit var binding: FragmentWaybillBinding
    private lateinit var waybillAdapter: WaybillAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentWaybillBinding.inflate(inflater, container, false)
        return binding.root
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
        binding.listWaybill.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = waybillAdapter
        }
    }

    private fun setupListener(){
        binding.editWaybill.setOnClickListener {
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