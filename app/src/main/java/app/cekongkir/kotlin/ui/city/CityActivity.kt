package app.cekongkir.kotlin.ui.city

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.R
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class CityActivity : AppCompatActivity() , KodeinAware {

    override val kodein by kodein()
    private val cityViewModelFactory: CityViewModelFactory by instance()
    private lateinit var cityViewModel: CityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        cityViewModel =
                ViewModelProvider(this, cityViewModelFactory).get(CityViewModel::class.java)
    }
}