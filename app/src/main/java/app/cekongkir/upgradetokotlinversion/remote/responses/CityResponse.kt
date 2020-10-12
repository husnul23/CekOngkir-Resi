package app.cekongkir.upgradetokotlinversion.remote.responses

data class CityResponse(
    val rajaongkir: Rajaongkir
) {
    data class Rajaongkir(
            val query: List<Any>,
            val results: List<Result>,
            val status: Status
    )
    data class Result(
            val city_id: String,
            val city_name: String,
            val postal_code: String,
            val province: String,
            val province_id: String,
            val type: String
    )
    data class Status(
            val code: Int,
            val description: String
    )
}