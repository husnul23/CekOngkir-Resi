package app.cekongkir.ui.city

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.R
import app.cekongkir.network.ApiService
import app.cekongkir.network.Resource
import timber.log.Timber

class CityActivity : AppCompatActivity(){

    private val api by lazy { ApiService.getClient() }
    private lateinit var viewModelFactory : CityViewModelFactory
    private lateinit var viewModel : CityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        setupViewModel()
        setupObserver()


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupViewModel() {
        viewModelFactory = CityViewModelFactory(api)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CityViewModel::class.java)
    }

    private fun setupObserver() {
        viewModel.titleBar.observe( this, Observer { title ->
            supportActionBar!!.title = title
        })
        viewModel.cityResponse.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    Timber.e("RajaOngkir isLoading")
                }
                is Resource.Success -> {
                    Timber.e("RajaOngkir ${it.data!!.rajaongkir}")
                }
                is Resource.Error -> {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}