package app.cekongkir.kotlin.ui

import app.cekongkir.kotlin.database.persistence.CekOngkirDatabase
import app.cekongkir.kotlin.remote.RajaOngkirEndpoint

class RajaOngkirRepository (
        private val api: RajaOngkirEndpoint,
        private val db: CekOngkirDatabase
) {

    suspend fun fetchCity() = api.city()

    suspend fun fetchWaybill(waybill: String, courier: String)
            = api.waybill(waybill, courier)
}