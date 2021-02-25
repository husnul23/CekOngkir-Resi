package app.cekongkir.network

import app.cekongkir.network.response.CityResponse
import app.cekongkir.network.response.SubDistrictResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RajaOngkirEndpoint {

    @GET("city")
    suspend fun city(): Response<CityResponse>

    @GET("subdistrict")
    suspend fun subdistrict(
            @Query("city") city: String
    ) : Response<SubDistrictResponse>
}