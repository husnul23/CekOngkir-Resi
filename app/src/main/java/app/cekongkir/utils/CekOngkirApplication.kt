package app.cekongkir.utils

import android.app.Application
import app.cekongkir.database.preferences.CekOngkirPreference
import app.cekongkir.network.ApiService
import app.cekongkir.network.RajaOngkirEndpoint
import app.cekongkir.network.RajaOngkirRepository
import app.cekongkir.ui.city.CityViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class CekOngkirApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@CekOngkirApplication))

        bind() from singleton { CekOngkirPreference(instance()) }
        bind<RajaOngkirEndpoint>() with singleton { ApiService.getClient() } // cara ini karna menggunakan interface
        bind() from singleton { RajaOngkirRepository(instance(), instance()) }

        bind() from singleton { CityViewModelFactory(instance()) }

    }
}