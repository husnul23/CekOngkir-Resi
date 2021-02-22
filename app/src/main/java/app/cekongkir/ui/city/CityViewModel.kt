package app.cekongkir.ui.city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cekongkir.network.ApiService
import app.cekongkir.network.RajaOngkirEndpoint
import app.cekongkir.network.Resource
import app.cekongkir.network.response.CityResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class CityViewModel(
        val api: RajaOngkirEndpoint
) : ViewModel() {

    val titleBar: MutableLiveData<String> = MutableLiveData("")
    val cityResponse: MutableLiveData<Resource<CityResponse>> = MutableLiveData()

    init {
        fetchCity()
    }

    fun fetchCity() = viewModelScope.launch {
        cityResponse.value = Resource.Loading()
        try {
            cityResponse.value = Resource.Success( api.city().body()!! )
        } catch ( e: Exception) {
            cityResponse.value = Resource.Error(e.message.toString())
        }
    }

}