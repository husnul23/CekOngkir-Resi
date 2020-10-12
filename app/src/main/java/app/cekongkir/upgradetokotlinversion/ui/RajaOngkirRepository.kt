package app.cekongkir.upgradetokotlinversion.ui

import app.cekongkir.upgradetokotlinversion.database.persistence.CekOngkirDatabase
import app.cekongkir.upgradetokotlinversion.remote.RajaOngkirEndpoint

class RajaOngkirRepository (
        private val api: RajaOngkirEndpoint,
        private val db: CekOngkirDatabase
) {

    suspend fun fetchCity() = api.city()
}