package ca.cegepgarneau.gardemanger.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ca.cegepgarneau.gardemanger.model.Aliment

@Database(entities = [Aliment::class], version = 1, exportSchema = false)
abstract class AlimentDatabase : RoomDatabase() {
    abstract fun alimentDao(): AlimentDao

    companion object {
        @Volatile
        private var INSTANCE: AlimentDatabase? = null

        fun getDatabase(context: Context): AlimentDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlimentDatabase::class.java,
                    "garde_manger_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
