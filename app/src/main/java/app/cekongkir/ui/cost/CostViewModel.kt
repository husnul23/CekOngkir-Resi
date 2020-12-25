package app.cekongkir.ui.cost

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cekongkir.database.preferences.*
import app.cekongkir.network.Resource
import app.cekongkir.network.responses.CostResponse
import app.cekongkir.network.RajaOngkirRepository
import app.cekongkir.network.responses.CityResponse
import app.cekongkir.network.responses.SubdistrictResponse
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class CostViewModel(
    private val repository: RajaOngkirRepository
) : ViewModel() {

    val titleBar = MutableLiveData("")
    val cityName: MutableLiveData<String> = MutableLiveData()
    val preferences: MutableLiveData<List<PreferencesModel>> = MutableLiveData()

    val cityResponse: MutableLiveData<Resource<CityResponse>> = MutableLiveData()
    val subdistrictResponse: MutableLiveData<Resource<SubdistrictResponse>> = MutableLiveData()

    val costResponse: MutableLiveData<Resource<CostResponse>> = MutableLiveData()

    init {
        fetchCity()
    }

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
        try {
            subdistrictResponse.postValue(Resource.Loading())
            val response = repository.fetchSubdistrict(city)
            subdistrictResponse.postValue(Resource.Success(response.body()!!))
        } catch (e: IOException) {
            subdistrictResponse.postValue(Resource.Error(e.message.toString()))
        } catch (e: Throwable) {
            subdistrictResponse.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getPreferences () {
        preferences.value = repository.getPreferences()
    }

    fun fetchCost(
            origin: String, originType: String, destination: String,
            destinationType: String, weight: String, courier: String
    ) = viewModelScope.launch {
        costResponse.postValue(Resource.Loading())
        val response = repository.fetchCost(
                origin, originType, destination, destinationType, weight, courier
        )
        costResponse.postValue(handleCostResponse(response))
    }

    private fun handleCostResponse(response: Response<CostResponse>) : Resource<CostResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

}