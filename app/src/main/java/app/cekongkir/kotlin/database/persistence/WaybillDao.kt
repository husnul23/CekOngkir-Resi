package app.cekongkir.kotlin.database.persistence

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WaybillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(waybillEntity: WaybillEntity)

    @Update
    suspend fun update(waybillEntity: WaybillEntity)

    @Query("DELETE FROM tableWaybill")
    suspend fun delete()

    @Query("SELECT * FROM tableWaybill")
    fun select(): LiveData<List<WaybillEntity>>
}