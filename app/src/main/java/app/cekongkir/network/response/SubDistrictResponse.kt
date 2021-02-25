package app.cekongkir.network.response

data class SubDistrictResponse(
        val rajaongkir: Rajaongkir
) {
    data class Rajaongkir(
            val query: List<Any>,
            val results: List<Results>,
            val status: Status
    ) {
        data class Query(
                val city: String
                )

        data class Results(
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
}
