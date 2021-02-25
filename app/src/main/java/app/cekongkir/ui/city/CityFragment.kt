package app.cekongkir.ui.city

import android.content.Intent
import android.os.Bundle
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
import app.cekongkir.databinding.FragmentCityBinding
import app.cekongkir.network.Resource
import app.cekongkir.network.response.CityResponse
import timber.log.Timber

class CityFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(CityViewModel::class.java) }
    private lateinit var binding: FragmentCityBinding
    private lateinit var cityAdapter: CityAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupRecyclerView()
        setupListener()
        setupObserver()
    }

    private fun setupView() {
        viewModel.titleBar.postValue("Pilih Kota")
    }

    private fun setupListener() {
        binding.editSearch.doAfterTextChanged {
            cityAdapter.filter.filter( it.toString() )
        }
        binding.refreshCity.setOnRefreshListener {
            viewModel.fetchCity()
        }
    }

    private fun setupRecyclerView() {
        cityAdapter = CityAdapter(arrayListOf(), object : CityAdapter.OnAdapterListener{
            override fun onClick(result: CityResponse.Rajaongkir.Results) {
                findNavController().navigate(
                        R.id.action_cityFragment_to_subdistrictFragment,
                        bundleOf("city_id" to result.city_id, "city_name" to result.city_name))
            }
        })
        binding.listCity.adapter = cityAdapter
    }

    private fun setupObserver() {
        viewModel.cityResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    Timber.e("RajaOngkir isLoading")
                    binding.refreshCity.isRefreshing = true
                }
                is Resource.Success -> {
                    binding.refreshCity.isRefreshing = false
                    Timber.e("RajaOngkir ${it.data!!.rajaongkir}")
                    cityAdapter.setData( it.data.rajaongkir.results)
                }
                is Resource.Error -> {
                    binding.refreshCity.isRefreshing = false
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}