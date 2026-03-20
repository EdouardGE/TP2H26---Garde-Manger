package ca.cegepgarneau.gardemanger.model

import androidx.room.Entity
import androidx.room.PrimaryKey

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