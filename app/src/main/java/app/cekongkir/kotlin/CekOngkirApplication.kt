package app.cekongkir.kotlin

import android.app.Application
import app.cekongkir.kotlin.database.CekOngkirPreference
import app.cekongkir.kotlin.database.persistence.CekOngkirDatabase
import app.cekongkir.kotlin.remote.ApiService
import app.cekongkir.kotlin.remote.RajaOngkirEndpoint
import app.cekongkir.kotlin.ui.RajaOngkirRepository
import app.cekongkir.kotlin.ui.cost.CostViewModelFactory
import app.cekongkir.kotlin.ui.waybill.WaybillViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import timber.log.Timber

class CekOngkirApplication: Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@CekOngkirApplication))

        bind() from singleton { CekOngkirPreference(instance()) }
        bind() from singleton { CekOngkirDatabase(instance()) }
        bind<RajaOngkirEndpoint>() with singleton { ApiService.getClient() }

        bind() from singleton { RajaOngkirRepository( instance(), instance() ) }
        bind() from provider { CostViewModelFactory( instance()) }
        bind() from provider { WaybillViewModelFactory( instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}