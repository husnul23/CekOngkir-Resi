package app.cekongkir.kotlin.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.R
import app.cekongkir.kotlin.remote.Resource
import app.cekongkir.kotlin.ui.cost.CostViewModel
import app.cekongkir.kotlin.ui.cost.CostViewModelFactory
import app.cekongkir.kotlin.ui.waybill.WaybillViewModel
import app.cekongkir.kotlin.ui.waybill.WaybillViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_home.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import timber.log.Timber

class HomeActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val costViewModelFactory: CostViewModelFactory by instance()
    private val waybillViewModelFactory: WaybillViewModelFactory by instance()

    private lateinit var costViewModel: CostViewModel
    private lateinit var waybillViewModel: WaybillViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupView()
        setupViewModel()
        setupListener()
        setupObserver()
    }

    override fun onStart() {
        super.onStart()
        waybillViewModel.fetchWaybill(app.cekongkir.kotlin.database.Constant.sicepat, "sicepat")
    }

    private fun setupView(){
        val tabTitles = arrayOf("CEK ONGKIR", "CEK RESI")
        val adapter = HomeTabAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    private fun setupViewModel(){
        costViewModel =
                ViewModelProvider(this, costViewModelFactory)
                        .get(CostViewModel::class.java)
        waybillViewModel =
                ViewModelProvider(this, waybillViewModelFactory)
                        .get(WaybillViewModel::class.java)
    }

    private fun setupListener(){

    }

    private fun setupObserver(){

        waybillViewModel.waybillResponse.observe(this, Observer {
            when(it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    Timber.d("waybillResponse: ${it.data!!.rajaongkir}")
                }
                is Resource.Error -> {

                }
            }
        })
    }

    public fun showTabFragment(show: Boolean){
        when (show) {
            true -> {
                supportActionBar!!.show()
                viewPager.visibility = View.VISIBLE
                tabLayout.visibility = View.VISIBLE
            }
            false -> {
                supportActionBar!!.hide()
                viewPager.visibility = View.GONE
                tabLayout.visibility = View.GONE
            }
        }

    }
}