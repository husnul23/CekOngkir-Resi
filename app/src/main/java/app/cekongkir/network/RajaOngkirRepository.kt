package app.cekongkir.network

import app.cekongkir.database.preferences.*

class RajaOngkirRepository(
        private val api: RajaOngkirEndpoint,
        private val pref: CekOngkirPreference
) {

    suspend fun fetchCity() = api.city()
    suspend fun fetchSubdistrict(cityId: String) = api.subdistrict(cityId)

    fun savePreferences(type: String, id: String, name: String) {
        when(type) {
            "origin" -> {
                pref.put( prefOriginId, id )
                pref.put( prefOriginName, name )
            }
            "destination" -> {
                pref.put( prefDestinationId, id )
                pref.put( prefDestinationName, name )
            }
        }
    }

    fun getPreferences(): List<PreferencesModel> {
        return listOf(
                PreferencesModel(type = "origin", id = pref.getString(prefOriginId), name = pref.getString(prefOriginName)),
                PreferencesModel(type = "destination", id = pref.getString(prefDestinationId), name = pref.getString(prefDestinationName))
        )
    }
}