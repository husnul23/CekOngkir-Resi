package app.cekongkir.kotlin.remote

import app.cekongkir.kotlin.database.persistence.CekOngkirDatabase
import app.cekongkir.kotlin.remote.RajaOngkirEndpoint
import retrofit2.http.Field

class RajaOngkirRepository (
        private val api: RajaOngkirEndpoint,
        private val db: CekOngkirDatabase
) {

    suspend fun fetchSubdistrict(city: String) = api.subdistrict(city)

    suspend fun fetchCity() = api.city()

    suspend fun fetchCost( origin: String, originType: String, destination: String,
                          destinationType: String, weight: String, courier: String )
            = api.cost( origin, originType, destination, destinationType, weight, courier )

    suspend fun fetchWaybill(waybill: String, courier: String)
            = api.waybill(waybill, courier)
}