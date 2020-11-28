package app.cekongkir.ui.city

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import app.cekongkir.R
import app.cekongkir.network.Resource
import app.cekongkir.network.responses.CityResponse
import app.cekongkir.utils.swipeHide
import app.cekongkir.utils.swipeShow
import kotlinx.android.synthetic.main.fragment_city.*
import kotlinx.android.synthetic.main.fragment_city.view.*

class CityFragment : Fragment() {

    private lateinit var fragmentView: View
    private lateinit var cityAdapter: CityAdapter
    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(CityViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_city, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupListener()
        setupRecyclerView()
        setupObserver()
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchCity()
    }

    private fun setupView(){
        (requireActivity() as CityActivity)
                .supportActionBar!!.title = "Pilih Kota"
        fragmentView.edit_search.isEnabled = false
    }

    private fun setupListener(){
        fragmentView.edit_search.doAfterTextChanged {
            Log.d("CityFragment", "doAfterTextChanged: ${it.toString()}")
            cityAdapter.filter.filter( it.toString() )
        }
        refresh_city.setOnRefreshListener {
            viewModel.fetchCity()
        }
    }

    private fun setupRecyclerView(){
        cityAdapter = CityAdapter(arrayListOf(), object : CityAdapter.OnAdapterListener {
            override fun onClick(result: CityResponse.Rajaongkir.Result) {
                val bundle = bundleOf(
                        "city_id" to result.city_id,
                        "city_name" to result.city_name
                )
                findNavController().navigate(
                        R.id.action_cityFragment_to_subdistrictFragment,
                        bundle
                )
            }
        })
        fragmentView.list_city.adapter = cityAdapter
    }

    private fun setupObserver(){
        viewModel.cityResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    refresh_city.swipeShow()
                }
                is Resource.Success -> {
                    refresh_city.swipeHide()
                    cityAdapter.setData(it.data!!.rajaongkir.results)
                    fragmentView.edit_search.isEnabled = true
                }
                is Resource.Error -> {
                    refresh_city.swipeHide()
                    Toast.makeText(context, it.message!!, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}