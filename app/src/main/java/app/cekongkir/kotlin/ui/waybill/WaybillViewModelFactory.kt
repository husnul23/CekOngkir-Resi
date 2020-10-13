package app.cekongkir.kotlin.ui.waybill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.kotlin.ui.RajaOngkirRepository

class WaybillViewModelFactory(
    val repository: RajaOngkirRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WaybillViewModel(repository) as T
    }
}