package ca.cegepgarneau.gardemanger.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Représente un aliment dans le garde manger.
 * Cette classe est une entité room, donc cest une table dans la bd.
 */
@Entity(tableName = "Aliment")
data class Aliment(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nom: String,
    val categorie: String,
    val quantite: Int,
    val unite: String,
    val aAcheter: Boolean = false
)