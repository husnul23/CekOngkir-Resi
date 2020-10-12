package app.cekongkir.upgradetokotlinversion.remote

import app.cekongkir.upgradetokotlinversion.remote.responses.CityResponse
import app.cekongkir.upgradetokotlinversion.remote.responses.ProviceResponse
import app.cekongkir.upgradetokotlinversion.remote.responses.SubdistrictResponse
import app.cekongkir.upgradetokotlinversion.remote.responses.WaybillResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface RajaOngkirEndpoint {

    @GET("provice")
    suspend fun provice() : Response<ProviceResponse>

    @GET("city")
    suspend fun city() : Response<CityResponse>

    @GET("subdistrict")
    suspend fun subdistrict() : Response<SubdistrictResponse>

    @FormUrlEncoded
    @POST("waybill")
    suspend fun waybill(
            @Field("waybill") waybill: String,
            @Field("courier") courier: String
    ) : Response<WaybillResponse>
}