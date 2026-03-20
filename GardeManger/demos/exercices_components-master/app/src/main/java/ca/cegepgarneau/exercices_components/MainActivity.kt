package ca.cegepgarneau.exercices_components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.cegepgarneau.exercices_components.ui.theme.exercices_componentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            exercices_componentsTheme {
                AppNavigation()
            }
        }
    }
}

// Écrans de l'application
sealed class Ecran {
    data object Menu : Ecran()
    data object Exercice1 : Ecran()
    data object Exercice2 : Ecran()
    data object Exercice3 : Ecran()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    var ecranActuel by remember { mutableStateOf<Ecran>(Ecran.Menu) }
    
    when (ecranActuel) {
        is Ecran.Menu -> MenuPrincipal(
            onExercice1 = { ecranActuel = Ecran.Exercice1 },
            onExercice2 = { ecranActuel = Ecran.Exercice2 },
            onExercice3 = { ecranActuel = Ecran.Exercice3 }
        )
        is Ecran.Exercice1 -> Exercice1FormulaireContact(
            onRetour = { ecranActuel = Ecran.Menu }
        )
        is Ecran.Exercice2 -> Exercice2Parametres(
            onRetour = { ecranActuel = Ecran.Menu }
        )
        is Ecran.Exercice3 -> Exercice3CarteProfil(
            onRetour = { ecranActuel = Ecran.Menu }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuPrincipal(
    onExercice1: () -> Unit,
    onExercice2: () -> Unit,
    onExercice3: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Exercices Composants UI") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Choisissez un exercice :",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            // Exercice 1
            ExerciceCard(
                numero = 1,
                titre = "Formulaire de contact",
                description = "Validation de formulaire avec regex, champs obligatoires et bouton conditionnel",
                icone = Icons.Default.Email,
                onClick = onExercice1
            )
            
            // Exercice 2
            ExerciceCard(
                numero = 2,
                titre = "Écran de paramètres",
                description = "Switch, RadioButtons, Slider et Checkboxes pour les options",
                icone = Icons.Default.Settings,
                onClick = onExercice2
            )
            
            // Exercice 3
            ExerciceCard(
                numero = 3,
                titre = "Carte de profil",
                description = "Card avec image ronde, informations et AlertDialog d'édition",
                icone = Icons.Default.Person,
                onClick = onExercice3
            )
        }
    }
}

@Composable
fun ExerciceCard(
    numero: Int,
    titre: String,
    description: String,
    icone: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Numéro dans un cercle
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(56.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icone,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Exercice $numero",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = titre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}