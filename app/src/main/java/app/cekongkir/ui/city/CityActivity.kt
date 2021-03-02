package app.cekongkir.ui.city

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.R
import app.cekongkir.database.preferences.CekOngkirPreference
import app.cekongkir.network.ApiService
import app.cekongkir.network.RajaOngkirRepository
import app.cekongkir.network.Resource
import timber.log.Timber

class CityActivity : AppCompatActivity(){

    private val api by lazy { ApiService.getClient() }
    private val pref by lazy { CekOngkirPreference(this) }
    private lateinit var viewModelFactory : CityViewModelFactory
    private lateinit var viewModel : CityViewModel
    private lateinit var repository: RajaOngkirRepository

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
        repository = RajaOngkirRepository( api, pref )
        viewModelFactory = CityViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CityViewModel::class.java)
    }

    private fun setupObserver() {
        viewModel.titleBar.observe( this, Observer { title ->
            supportActionBar!!.title = title
        })
    }
}