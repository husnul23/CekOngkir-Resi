package app.cekongkir.network

import app.cekongkir.database.persistence.CekOngkirDatabase
import app.cekongkir.database.persistence.WaybillEntity
import app.cekongkir.database.preferences.*
import app.cekongkir.network.responses.CityResponse
import timber.log.Timber

class RajaOngkirRepository (
        private val api: RajaOngkirEndpoint,
        private val db: CekOngkirDatabase,
        private val pref: CekOngkirPreference
) {

    suspend fun fetchCity() = api.city()

    suspend fun fetchSubdistrict(city: String) = api.subdistrict(city)

    fun saveCostPreferences(type: String, cityName: String, subdistricName: String, subdistricId: String) {
        Timber.d("saveCostPreferences $type $cityName $subdistricName $subdistricId")
        when (type) {
            "origin" -> {
                pref.put(prefOriginCityName, cityName)
                pref.put(prefOriginSubdistricName, subdistricName)
                pref.put(prefOriginSubdistricId, subdistricId)
            }
            "destination" -> {
                pref.put(prefDestinationCityName, cityName)
                pref.put(prefDestinationSubdistricName, subdistricName)
                pref.put(prefDestinationSubdistricId, subdistricId)
            }
        }
    }

    suspend fun fetchCost( origin: String, originType: String, destination: String,
                          destinationType: String, weight: String, courier: String )
            = api.cost( origin, originType, destination, destinationType, weight, courier )

    suspend fun fetchWaybill(waybill: String, courier: String)
            = api.waybill(waybill, courier)

    suspend fun saveWaybill( waybillEntity: WaybillEntity) {
        db.waybillDao().insert( waybillEntity )
    }

    fun getWaybill() = db.waybillDao().select()
}