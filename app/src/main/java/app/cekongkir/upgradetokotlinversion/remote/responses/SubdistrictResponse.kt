package app.cekongkir.upgradetokotlinversion.remote.responses

data class SubdistrictResponse(
    val rajaongkir: Rajaongkir
) {
    data class Rajaongkir(
            val query: Query,
            val results: List<Result>,
            val status: Status
    )
    data class Query(
            val city: String
    )
    data class Result(
            val city: String,
            val city_id: String,
            val province: String,
            val province_id: String,
            val subdistrict_id: String,
            val subdistrict_name: String,
            val type: String
    )
    data class Status(
            val code: Int,
            val description: String
    )
}