package app.cekongkir.kotlin.ui.cost

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
import app.cekongkir.kotlin.network.Resource
import app.cekongkir.kotlin.ui.city.CityActivity
import app.cekongkir.kotlin.utils.*
import kotlinx.android.synthetic.main.fragment_cost.*
import kotlinx.android.synthetic.main.fragment_cost.view.*

class CostFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(CostViewModel::class.java)
    }

    private lateinit var fragmentView: View
    private lateinit var costAdapter: CostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_cost, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListener()
        setupObserver()
    }

    override fun onStart() {
        super.onStart()
        loadingCost( false )
        edit_origin.setText( "$originCityName$originSubdistricName" )
        edit_destination.setText( "$destinationCityName$destinationSubdistricName" )
    }

    private fun setupRecyclerView(){
        costAdapter = CostAdapter(arrayListOf())
        list_cost.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = costAdapter
        }
    }

    private fun setupListener(){
        fragmentView.edit_origin.setOnClickListener {
            costType = "origin"
            startActivity( Intent(requireActivity(), CityActivity::class.java) )
        }
        fragmentView.edit_destination.setOnClickListener {
            costType = "destination"
            startActivity( Intent(requireActivity(), CityActivity::class.java) )
        }
        fragmentView.button_cost.setOnClickListener {
            if (edit_origin.text.isNullOrEmpty() || edit_destination.text.isNullOrEmpty()) {
                showToast("Lengkapi data pencarian")
            } else {
                viewModel.fetchCost(
                        origin = originSubdistricId!!,
                        originType = "subdistrict",
                        destination = destinationSubdistricId!!,
                        destinationType = "subdistrict",
                        weight = "1000",
                        courier = "sicepat:jnt:pos"
                )
            }
        }
    }

    private fun loadingCost(loading: Boolean) {
        when (loading) {
            true -> {
                progress_cost.visibility = View.VISIBLE
            }
            false -> {
                progress_cost.visibility = View.GONE
            }
        }
    }

    private fun setupObserver(){
        viewModel.costResponse.observe( viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    fragmentView.progress_cost.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    fragmentView.progress_cost.visibility = View.GONE
                    costAdapter.setData( it.data!!.rajaongkir.results )
                }
                is Resource.Error -> {
                    fragmentView.progress_cost.visibility = View.GONE
                }
            }
        } )
    }
}