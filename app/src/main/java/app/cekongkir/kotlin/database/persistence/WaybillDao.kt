package app.cekongkir.kotlin.database.persistence

import androidx.room.*

@Dao
interface WaybillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(waybillEntity: WaybillEntity)

    @Update
    fun update(waybillEntity: WaybillEntity)

    @Query("DELETE FROM tableWaybill")
    fun delete()

    @Query("SELECT * FROM tableWaybill")
    fun select(): List<WaybillEntity>
}