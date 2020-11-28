package app.cekongkir.kotlin.ui.tracking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cekongkir.kotlin.database.persistence.WaybillEntity
import app.cekongkir.kotlin.network.Resource
import app.cekongkir.kotlin.network.responses.WaybillResponse
import app.cekongkir.kotlin.network.RajaOngkirRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class WaybillViewModel(
    private val repository: RajaOngkirRepository
) : ViewModel() {

    val waybillResponse: MutableLiveData<Resource<WaybillResponse>> = MutableLiveData()
    val waybillEntity: LiveData<List<WaybillEntity>> = repository.getWaybill()

    fun fetchWaybill(waybill: String, courier: String) = viewModelScope.launch {
        waybillResponse.postValue(Resource.Loading())
        val response = repository.fetchWaybill(waybill, courier)
        waybillResponse.postValue(handleWaybillResponse(response))
    }

    private fun handleWaybillResponse(response: Response<WaybillResponse>) : Resource<WaybillResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                saveWaybill( it.rajaongkir )
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun saveWaybill(waybill: WaybillResponse.Rajaongkir) = viewModelScope.launch {
        repository.saveWaybill(
                WaybillEntity(
                        waybill = waybill.query.waybill ,
                        courier = waybill.query.courier,
                        status = waybill.result.delivery_status.status,
                )
        )
    }

}