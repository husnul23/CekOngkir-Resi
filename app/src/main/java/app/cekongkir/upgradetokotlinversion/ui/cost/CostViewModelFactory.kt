package app.cekongkir.upgradetokotlinversion.ui.cost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.upgradetokotlinversion.ui.RajaOngkirRepository

class CostViewModelFactory(
    val repository: RajaOngkirRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CostViewModel(repository) as T
    }
}