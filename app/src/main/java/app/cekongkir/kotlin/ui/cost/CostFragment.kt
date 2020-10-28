package app.cekongkir.kotlin.ui.cost

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.cekongkir.R
import app.cekongkir.kotlin.remote.Resource
import app.cekongkir.kotlin.ui.city.CityActivity
import app.cekongkir.kotlin.ui.city.CityFragment
import app.cekongkir.kotlin.ui.home.HomeActivity
import app.cekongkir.kotlin.ui.waybill.WaybillFragment
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
            startActivity(Intent(requireActivity(), CityActivity::class.java))
        }
        fragmentView.button_cost.setOnClickListener {
            viewModel.fetchCost(
                    origin = "501",
                    originType = "city",
                    destination = "574 ",
                    destinationType = "subdistrict",
                    weight = "1000",
                    courier = "sicepat:jnt:pos"
            )
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