package app.cekongkir.ui.city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.cekongkir.network.ApiService
import app.cekongkir.network.RajaOngkirEndpoint

class CityViewModel(
        val api: RajaOngkirEndpoint
) : ViewModel() {

    val titleBar: MutableLiveData<String> = MutableLiveData("")

}