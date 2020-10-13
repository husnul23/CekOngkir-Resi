package app.cekongkir.kotlin.database.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "tableWaybill")
data class WaybillEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val waybill: String? = "",
    val courier: String? = "",
    val status: String? = ""
)