package ca.cegepgarneau.exercices_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Exercice 2 : Écran de paramètres
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Exercice2Parametres(
    onRetour: () -> Unit = {}
) {
    // États des paramètres
    var modeSombre by remember { mutableStateOf(false) }
    
    // Langues
    val langues = listOf("Français", "English", "Español")
    var langueSelectionnee by remember { mutableStateOf(langues[0]) }
    
    // Taille du texte (12sp à 24sp)
    var tailleTexte by remember { mutableFloatStateOf(16f) }
    
    // Notifications
    var notifEmail by remember { mutableStateOf(true) }
    var notifPush by remember { mutableStateOf(true) }
    var notifSMS by remember { mutableStateOf(false) }
    var notifPromo by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Paramètres") },
                navigationIcon = {
                    IconButton(onClick = onRetour) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Section Apparence
            SectionTitre("Apparence")
            
            // Switch Mode sombre
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { modeSombre = !modeSombre }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Mode sombre",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = if (modeSombre) "Activé" else "Désactivé",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = modeSombre,
                    onCheckedChange = { modeSombre = it }
                )
            }
            
            HorizontalDivider()
            
            // Section Langue
            SectionTitre("Langue")
            
            langues.forEach { langue ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { langueSelectionnee = langue }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = langue == langueSelectionnee,
                        onClick = { langueSelectionnee = langue }
                    )
                    Text(
                        text = langue,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
            
            HorizontalDivider()
            
            // Section Taille du texte
            SectionTitre("Taille du texte")
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Aperçu de la taille
                Text(
                    text = "Aperçu du texte",
                    fontSize = tailleTexte.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                
                // Slider
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "A",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Slider(
                        value = tailleTexte,
                        onValueChange = { tailleTexte = it },
                        valueRange = 12f..24f,
                        steps = 5,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    )
                    Text(
                        text = "A",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Text(
                    text = "${tailleTexte.toInt()} sp",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            
            HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
            
            // Section Notifications
            SectionTitre("Notifications")
            
            CheckboxOption(
                titre = "Notifications par courriel",
                description = "Recevoir les mises à jour par courriel",
                checked = notifEmail,
                onCheckedChange = { notifEmail = it }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SectionTitre(titre: String) {
    Text(
        text = titre,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
    )
}

@Composable
private fun CheckboxOption(
    titre: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = titre,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExercice2Parametres() {
    Exercice2Parametres()
}
