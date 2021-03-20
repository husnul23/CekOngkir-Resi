package app.cekongkir.ui.cost

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cekongkir.database.preferences.PreferencesModel
import app.cekongkir.network.ApiService
import app.cekongkir.network.RajaOngkirEndpoint
import app.cekongkir.network.RajaOngkirRepository
import app.cekongkir.network.Resource
import app.cekongkir.network.response.CityResponse
import app.cekongkir.network.response.SubDistrictResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class CostViewModel(
        val repository: RajaOngkirRepository
) : ViewModel() {

   val preference: MutableLiveData<List<PreferencesModel>> = MutableLiveData()

    fun getPreferences() {
        preference.value= repository.getPreferences()
    }

}