package app.cekongkir.ui.city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cekongkir.network.ApiService
import app.cekongkir.network.RajaOngkirEndpoint
import app.cekongkir.network.RajaOngkirRepository
import app.cekongkir.network.Resource
import app.cekongkir.network.response.CityResponse
import app.cekongkir.network.response.SubDistrictResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class CityViewModel(
        val repository: RajaOngkirRepository
) : ViewModel() {

    val titleBar: MutableLiveData<String> = MutableLiveData("")
    val cityResponse: MutableLiveData<Resource<CityResponse>> = MutableLiveData()
    val subDistrictResponse: MutableLiveData<Resource<SubDistrictResponse>> = MutableLiveData()

    init {
        fetchCity()
    }

    fun fetchCity() = viewModelScope.launch {
        cityResponse.value = Resource.Loading()
        try {
            val response = repository.fetchCity()
            cityResponse.value = Resource.Success( response.body()!! )
        } catch ( e: Exception) {
            cityResponse.value = Resource.Error(e.message.toString())
        }
    }

    fun fetchSubDistrict(id: String) = viewModelScope.launch {
        cityResponse.value = Resource.Loading()
        try {
            val response = repository.fetchSubdistrict(id)
            subDistrictResponse.value = Resource.Success( response.body()!! )
        } catch ( e: Exception) {
            subDistrictResponse.value = Resource.Error(e.message.toString())
        }
    }

}