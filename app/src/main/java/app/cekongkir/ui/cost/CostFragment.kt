package app.cekongkir.ui.cost

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
import app.cekongkir.database.preferences.*
import app.cekongkir.network.Resource
import app.cekongkir.ui.city.CityActivity
import app.cekongkir.utils.*
import kotlinx.android.synthetic.main.fragment_cost.*
import kotlinx.android.synthetic.main.fragment_cost.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import timber.log.Timber

class CostFragment : Fragment() , KodeinAware {

    override val kodein by closestKodein()
    private val pref: CekOngkirPreference by instance()
    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(CostViewModel::class.java)
    }

    private lateinit var fragmentView: View
    private lateinit var costAdapter: CostAdapter

    private var originSubdistricId: String? = ""
    private var destinationSubdistricId: String? = ""

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
        setupPreferences()
        Timber.d("setupPreferences")
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
            startActivity(
                    Intent(requireActivity(), CityActivity::class.java)
                            .putExtra( intentCostType, "origin")
            )
        }
        fragmentView.edit_destination.setOnClickListener {
            startActivity(
                    Intent(requireActivity(), CityActivity::class.java)
                            .putExtra( intentCostType, "destination")
            )
        }
        fragmentView.button_cost.setOnClickListener {
            if (originSubdistricId.isNullOrEmpty() || destinationSubdistricId.isNullOrEmpty()) {
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

    private fun setupPreferences(){
        pref.getString(prefOriginCityName)?.let {
            edit_origin.setText( "$it, ${pref.getString(prefOriginSubdistricName)}" )
            originSubdistricId = pref.getString(prefOriginSubdistricId)
        }
        pref.getString(prefDestinationCityName)?.let {
            edit_destination.setText( "$it, ${pref.getString(prefDestinationSubdistricName)}" )
            destinationSubdistricId = pref.getString(prefDestinationSubdistricId)
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
        })
    }
}