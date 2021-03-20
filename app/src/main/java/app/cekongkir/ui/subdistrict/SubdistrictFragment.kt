package app.cekongkir.ui.subdistrict

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.databinding.FragmentSubdistrictBinding
import app.cekongkir.network.Resource
import app.cekongkir.network.response.SubDistrictResponse
import app.cekongkir.ui.city.CityViewModel
import timber.log.Timber

class SubdistrictFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(CityViewModel::class.java) }
    private lateinit var binding: FragmentSubdistrictBinding
    private lateinit var subdistrictAdapter: SubdistrictAdapter
    private val cityId by lazy { requireArguments().getString("city_id") }
    private val type by lazy { requireActivity().intent.getStringExtra("intent_type") }
    private val cityName by lazy { requireArguments().getString("city_name") }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSubdistrictBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupRecyclerView()
        setupListener()
        setupObserver()
    }

    private fun setupRecyclerView() {
        subdistrictAdapter = SubdistrictAdapter(arrayListOf(), object : SubdistrictAdapter.OnAdapterListener{
            override fun onClick(result: SubDistrictResponse.Rajaongkir.Results) {
                viewModel.savePreferences(
                    type = type!!,
                        id = result.subdistrict_id,
                        name = "$cityName, ${result.subdistrict_name}"
                )
                requireActivity().finish()
            }

        })
        binding.listSubdistrict.adapter = subdistrictAdapter
    }

    private fun setupListener() {
        binding.refreshSubdistrict.setOnRefreshListener{
                viewModel.fetchSubDistrict( cityId!! )
        }
    }

    private fun setupView() {
        viewModel.titleBar.postValue("Pilih Kecamatan")
    }

    private fun setupObserver() {
        viewModel.subDistrictResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> binding.refreshSubdistrict.isRefreshing = true

                is Resource.Success -> {
                    binding.refreshSubdistrict.isRefreshing = false
                    Timber.e("subDistrictResponse ${it.data!!.rajaongkir.results}")
                    subdistrictAdapter.setData( it.data!!.rajaongkir.results)
                }
                is Resource.Error -> {
                    binding.refreshSubdistrict.isRefreshing = false
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}