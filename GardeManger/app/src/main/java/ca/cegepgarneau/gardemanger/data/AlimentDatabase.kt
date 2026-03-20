package ca.cegepgarneau.gardemanger.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ca.cegepgarneau.gardemanger.model.Aliment

/**
 * Classe principale de la base de données room.
 * Elle définit les entités utilisées et fournit l’accès au DAO.
 */
@Database(entities = [Aliment::class], version = 1, exportSchema = false)
abstract class AlimentDatabase : RoomDatabase() {

    /**
     * Permet d'accéder aux opérations de la bd
     * définies dans AlimentDao.
     */
    abstract fun alimentDao(): AlimentDao

    companion object {
        /**
         * Instance de la bd.
         * @Volatile garantit que les changements sont visible
         */
        @Volatile
        private var INSTANCE: AlimentDatabase? = null

        /**
         * Retourne l’instance de la bd.
         * Si elle n’existe pas, elle est créée de manière sécurisée.
         * @param context contexte de l’app.
         * @return Instance unique de AlimentDatabase
         */
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
