package app.cekongkir.upgradetokotlinversion.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.R
import app.cekongkir.upgradetokotlinversion.remote.Resource
import app.cekongkir.upgradetokotlinversion.ui.cost.CostViewModel
import app.cekongkir.upgradetokotlinversion.ui.cost.CostViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import timber.log.Timber

class HomeActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: CostViewModelFactory by instance()
    private val viewModel by lazy {
        ViewModelProvider(this, factory).get(CostViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupObserver()
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchCity()
    }

    private fun setupObserver(){
        viewModel.cityResponse.observe(this, Observer {
            when(it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    Timber.d("cityResponse: ${it.data!!.rajaongkir}")
                }
                is Resource.Error -> {

                }
            }
        })
    }
}