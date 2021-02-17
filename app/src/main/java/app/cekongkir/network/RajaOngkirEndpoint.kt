package app.cekongkir.network

import app.cekongkir.network.response.CityResponse
import retrofit2.Response
import retrofit2.http.GET

interface RajaOngkirEndpoint {

    @GET("city")
    suspend fun city(): Response<CityResponse>
}