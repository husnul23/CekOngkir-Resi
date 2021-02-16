package app.cekongkir.database.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(
//    entities = [],
//    exportSchema = false,
//    version = 1
//)
abstract class CekOngkirDatabase: RoomDatabase() {

    companion object {
        @Volatile private var instance: CekOngkirDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        CekOngkirDatabase::class.java, "CekOngkir.db")
                        .allowMainThreadQueries()
                        .build()
    }
}