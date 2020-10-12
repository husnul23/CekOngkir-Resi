package app.cekongkir.upgradetokotlinversion.ui.cost

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cekongkir.upgradetokotlinversion.remote.Resource
import app.cekongkir.upgradetokotlinversion.remote.responses.CityResponse
import app.cekongkir.upgradetokotlinversion.ui.RajaOngkirRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class CostViewModel(
    private val repository: RajaOngkirRepository
) : ViewModel() {

    val cityResponse: MutableLiveData<Resource<CityResponse>> = MutableLiveData()

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

}