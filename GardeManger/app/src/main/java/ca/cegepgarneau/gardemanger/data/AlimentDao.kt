package ca.cegepgarneau.gardemanger.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ca.cegepgarneau.gardemanger.model.Aliment
import kotlinx.coroutines.flow.Flow

@Dao
interface AlimentDao {

    @Query("SELECT * FROM Aliment ORDER BY nom ASC")
    fun getAll(): Flow<List<Aliment>>

    @Query("SELECT * FROM Aliment WHERE aAcheter = 1 ORDER BY nom ASC")
    fun getListeCourses(): Flow<List<Aliment>>

    @Query("SELECT * FROM Aliment WHERE id = :id")
    suspend fun getById(id: Int): Aliment?

    @Insert
    suspend fun insert(aliment: Aliment)

    @Update
    suspend fun update(aliment: Aliment)

    @Delete
    suspend fun delete(aliment: Aliment)

    @Query("DELETE FROM Aliment")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM Aliment")
    suspend fun count(): Int
}