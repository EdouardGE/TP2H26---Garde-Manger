package ca.cegepgarneau.gardemanger.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.RiceBowl
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Fournit une icône Material par catégorie d'aliment.
 * Retourne une icône par défaut (LocalDining) si la catégorie est inconnue.
 */
fun iconePourCategorie(categorie: String): ImageVector =
    when (categorie) {
        "Fruits" -> Icons.Default.Eco
        "Légumes" -> Icons.Default.SetMeal
        "Produits laitiers" -> Icons.Default.WaterDrop
        "Viandes" -> Icons.Default.Restaurant
        "Céréales" -> Icons.Default.RiceBowl
        else -> Icons.Default.LocalDining
    }
