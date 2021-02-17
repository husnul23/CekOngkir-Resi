package app.cekongkir.network.response

data class CityResponse(
        val rajaongkir: RajaOngkir) {

    data class RajaOngkir(
            val query: List<Any>,
            val status: Status,
            val results: List<Results>
    ) {
        data class Status(
                val code: Int,
                val description: String
        )

        data class Results(
                val city_id: String,
                val province_id: String,
                val province: String,
                val type: String,
                val city_name: String,
                val postal_code: String
        )
    }
}
