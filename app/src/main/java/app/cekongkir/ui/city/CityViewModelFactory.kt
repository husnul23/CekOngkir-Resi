package app.cekongkir.ui.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.network.RajaOngkirEndpoint

class CityViewModelFactory(
        private val api: RajaOngkirEndpoint
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CityViewModel(api = api) as T
    }
}