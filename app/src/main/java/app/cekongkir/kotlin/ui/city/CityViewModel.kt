package app.cekongkir.kotlin.ui.city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cekongkir.kotlin.remote.Resource
import app.cekongkir.kotlin.remote.responses.CityResponse
import app.cekongkir.kotlin.remote.responses.CostResponse
import app.cekongkir.kotlin.remote.RajaOngkirRepository
import app.cekongkir.kotlin.remote.responses.SubdistrictResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class CityViewModel(
    private val repository: RajaOngkirRepository
) : ViewModel() {

    val cityResponse: MutableLiveData<Resource<CityResponse>> = MutableLiveData()
    val subdistrictResponse: MutableLiveData<Resource<SubdistrictResponse>> = MutableLiveData()

    fun fetchCity() = viewModelScope.launch {
        cityResponse.postValue(Resource.Loading())
        val response = repository.fetchCity()
        cityResponse.postValue(handleCityResponse(response))
    }

    private fun handleCityResponse(response: Response<CityResponse>) : Resource<CityResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun fetchSubdistrict(city: String) = viewModelScope.launch {
        subdistrictResponse.postValue(Resource.Loading())
        val response = repository.fetchSubdistrict(city)
        subdistrictResponse.postValue(handleSubdistrictResponse(response))
    }

    private fun handleSubdistrictResponse(response: Response<SubdistrictResponse>) : Resource<SubdistrictResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

}