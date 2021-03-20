package app.cekongkir.network

import app.cekongkir.network.response.CityResponse
import app.cekongkir.network.response.SubDistrictResponse
import retrofit2.Response
import retrofit2.http.*

interface RajaOngkirEndpoint {

    @GET("city")
    suspend fun city(): Response<CityResponse>

    @GET("subdistrict")
    suspend fun subdistrict(
            @Query("city") city: String
    ) : Response<SubDistrictResponse>

    @FormUrlEncoded
    @POST("cost")
    suspend fun cost(
            @Field("origin") origin: String,
            @Field("originType") originType: String,
            @Field("destination") destination: String,
            @Field("destinationType") destinationType: String,
            @Field("weight") weight: String,
            @Field("courier") courier: String,
    )
}