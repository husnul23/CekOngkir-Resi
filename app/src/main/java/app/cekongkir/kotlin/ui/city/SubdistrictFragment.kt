package app.cekongkir.kotlin.ui.city

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.cekongkir.R
import app.cekongkir.kotlin.remote.Resource
import app.cekongkir.kotlin.remote.responses.SubdistrictResponse
import app.cekongkir.kotlin.toast
import kotlinx.android.synthetic.main.fragment_subdistrict.view.*

class SubdistrictFragment : Fragment() {

    private lateinit var fragmentView: View
    private lateinit var subdistrictAdapter: SubdistrictAdapter
    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(CityViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_subdistrict, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupRecyclerView()
        setupObserver()
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchSubdistrict( requireArguments().getString("city")!! )
    }

    private fun setupView(){
        (requireActivity() as CityActivity)
                .supportActionBar!!.title = "Pilih Kecamatan"
    }

    private fun setupRecyclerView(){
        subdistrictAdapter = SubdistrictAdapter(arrayListOf(), object : SubdistrictAdapter.OnAdapterListener {
            override fun onClick(result: SubdistrictResponse.Result) {
                requireActivity().finish()
            }
        })
        fragmentView.list_subdistrict.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = subdistrictAdapter
        }
    }

    private fun setupObserver(){
        viewModel.subdistrictResponse.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Loading -> {
                    requireActivity().toast(resources.getString(R.string.message_loading))
                }
                is Resource.Success -> {
                    subdistrictAdapter.setData( it.data!!.rajaongkir.results )
                }
                is Resource.Error -> {
                    requireActivity().toast(resources.getString(R.string.message_error))
                }
            }
        })
    }
}