package app.cekongkir.ui.subdistrict

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.cekongkir.R
import app.cekongkir.network.Resource
import app.cekongkir.network.responses.SubdistrictResponse
import app.cekongkir.ui.city.CityActivity
import app.cekongkir.ui.city.CityViewModel
import app.cekongkir.utils.*
import kotlinx.android.synthetic.main.fragment_subdistrict.*
import kotlinx.android.synthetic.main.fragment_subdistrict.view.*

class SubdistrictFragment : Fragment() {

    private lateinit var fragmentView: View
    private lateinit var subdistrictAdapter: SubdistrictAdapter
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(CityViewModel::class.java) }
    private val cityId by lazy { requireArguments().getString("city_id")!! }
    private val cityName by lazy { requireArguments().getString("city_name")!! }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_subdistrict, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupRecyclerView()
        setupListener()
        setupObserver()
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchSubdistrict( cityId )
    }

    private fun setupView(){
        (requireActivity() as CityActivity).supportActionBar!!.title = "Pilih Kecamatan"
    }

    private fun setupRecyclerView(){
        subdistrictAdapter = SubdistrictAdapter(arrayListOf(), object : SubdistrictAdapter.OnAdapterListener {
            override fun onClick(result: SubdistrictResponse.Rajaongkir.Result) {
                when (costType) {
                    "origin" -> {
                        originCityName = "$cityName, "
                        originSubdistricId = result.subdistrict_id
                        originSubdistricName = result.subdistrict_name
                    }
                    "destination" -> {
                        destinationCityName = "$cityName, "
                        destinationSubdistricId = result.subdistrict_id
                        destinationSubdistricName = result.subdistrict_name
                    }
                }
                requireActivity().finish()
            }
        })
        fragmentView.list_subdistrict.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = subdistrictAdapter
        }
    }

    private fun setupListener(){
        refresh_subdistrict.setOnRefreshListener {
            viewModel.fetchSubdistrict( cityId )
        }
    }

    private fun setupObserver(){
        viewModel.subdistrictResponse.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Loading -> {
                    refresh_subdistrict.swipeShow()
                }
                is Resource.Success -> {
                    refresh_subdistrict.swipeHide()
                    subdistrictAdapter.setData( it.data!!.rajaongkir.results )
                }
                is Resource.Error -> {
                    refresh_subdistrict.swipeHide()
                    requireActivity().showToast(resources.getString(R.string.message_error))
                }
            }
        })
    }
}