package app.cekongkir.kotlin.ui.waybill

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cekongkir.kotlin.remote.Resource
import app.cekongkir.kotlin.remote.responses.WaybillResponse
import app.cekongkir.kotlin.remote.RajaOngkirRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class WaybillViewModel(
    private val repository: RajaOngkirRepository
) : ViewModel() {

    val waybillResponse: MutableLiveData<Resource<WaybillResponse>> = MutableLiveData()

    fun fetchWaybill(waybill: String, courier: String) = viewModelScope.launch {
        waybillResponse.postValue(Resource.Loading())
        val response = repository.fetchWaybill(waybill, courier)
        waybillResponse.postValue(handleWaybillResponse(response))
    }

    private fun handleWaybillResponse(response: Response<WaybillResponse>) : Resource<WaybillResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

}