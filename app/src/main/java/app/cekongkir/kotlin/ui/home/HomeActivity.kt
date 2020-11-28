package app.cekongkir.kotlin.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.R
import app.cekongkir.kotlin.ui.cost.CostViewModel
import app.cekongkir.kotlin.ui.cost.CostViewModelFactory
import app.cekongkir.kotlin.ui.tracking.WaybillViewModel
import app.cekongkir.kotlin.ui.tracking.WaybillViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_home.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val costViewModelFactory: CostViewModelFactory by instance()
    private val waybillViewModelFactory: WaybillViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupTab()
        setupViewModel()
    }

    private fun setupTab(){
        val tabTitles = arrayOf("CEK ONGKIR", "CEK RESI")
        val tabAdapter = HomeTabAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = tabAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    private fun setupViewModel(){
        ViewModelProvider(this, costViewModelFactory).get(CostViewModel::class.java)
        ViewModelProvider(this, waybillViewModelFactory).get(WaybillViewModel::class.java)
    }
}