package app.cekongkir.network.responses

data class ProviceResponse(
    val rajaongkir: Rajaongkir
) {
    data class Rajaongkir(
            val query: List<Any>,
            val results: List<Result>,
            val status: Status
    ) {
        data class Result(
                val province: String,
                val province_id: String
        )
        data class Status(
                val code: Int,
                val description: String
        )
    }

}