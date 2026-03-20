package ca.cegepgarneau.gardemanger.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ca.cegepgarneau.gardemanger.model.Aliment
import kotlinx.coroutines.flow.Flow

/**
 * DAO = acces aux données de la table Aliment.
 * Définit toutes les opérations possibles sur la base de données.
 */
@Dao
interface AlimentDao {

    /**
     * Récupère tous les aliments de la base de données,
     * triés par ordre alphabétique et utilise un Flow pour observer les changements en temps réel.
     */
    @Query("SELECT * FROM Aliment ORDER BY nom ASC")
    fun getAll(): Flow<List<Aliment>>

    /**
     * Récupère uniquement les aliments à acheter (aAcheter = true),
     * triés par ordre alphabétique.
     * Sert pour afficher la liste d’achats.
     */
    @Query("SELECT * FROM Aliment WHERE aAcheter = 1 ORDER BY nom ASC")
    fun getListeCourses(): Flow<List<Aliment>>

    /**
     * Récupère un aliment spécifique à partir de son identifiant (id).
     * @param id Identifiant de l’aliment recherché
     * @return aliment correspondant ou null s’il existe pas
     */
    @Query("SELECT * FROM Aliment WHERE id = :id")
    suspend fun getById(id: Int): Aliment?

    /**
     * Insère un nouvel aliment dans la base de données.
     * @param aliment aliment à ajouter
     */
    @Insert
    suspend fun insert(aliment: Aliment)

    /**
     * Met à jour un aliment existant dans la bd.
     * @param aliment aliment modifié
     */
    @Update
    suspend fun update(aliment: Aliment)

    /**
     * Supprime un aliment spécifique de la bd.
     * @param aliment aliment à supprimer
     */
    @Delete
    suspend fun delete(aliment: Aliment)

    /**
     * Supprime tous les aliments de la bd.
     * Utilisé pour la fonction-bouton "Vider tout".
     */
    @Query("DELETE FROM Aliment")
    suspend fun deleteAll()

    /**
     * Retourne le nombre total d’aliments présents dans la base.
     * @return Nombre total d’aliments
     */
    @Query("SELECT COUNT(*) FROM Aliment")
    suspend fun count(): Int
}