package app.cekongkir.kotlin.ui.city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cekongkir.kotlin.network.Resource
import app.cekongkir.kotlin.network.responses.CityResponse
import app.cekongkir.kotlin.network.RajaOngkirRepository
import app.cekongkir.kotlin.network.responses.SubdistrictResponse
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class CityViewModel(
    private val repository: RajaOngkirRepository
) : ViewModel() {

    val cityResponse: MutableLiveData<Resource<CityResponse>> = MutableLiveData()
    val subdistrictResponse: MutableLiveData<Resource<SubdistrictResponse>> = MutableLiveData()

    fun fetchCity() = viewModelScope.launch {
        try {
            cityResponse.postValue(Resource.Loading())
            val response = repository.fetchCity()
            cityResponse.postValue(Resource.Success(response.body()!!))
        } catch (e: IOException) {
            cityResponse.postValue(Resource.Error(e.message.toString()))
        } catch (e: Throwable) {
            cityResponse.postValue(Resource.Error(e.message.toString()))
        }
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