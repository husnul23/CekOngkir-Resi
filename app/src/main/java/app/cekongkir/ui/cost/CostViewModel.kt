package app.cekongkir.ui.cost

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cekongkir.network.Resource
import app.cekongkir.network.responses.CostResponse
import app.cekongkir.network.RajaOngkirRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class CostViewModel(
    private val repository: RajaOngkirRepository
) : ViewModel() {

    val costResponse: MutableLiveData<Resource<CostResponse>> = MutableLiveData()

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