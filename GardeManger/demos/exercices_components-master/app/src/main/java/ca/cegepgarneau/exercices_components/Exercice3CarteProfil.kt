package ca.cegepgarneau.exercices_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Exercice 3 : Carte de profil
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Exercice3CarteProfil(
    onRetour: () -> Unit = {}
) {
    // États du profil
    var nom by remember { mutableStateOf("Aminata Tremblay") }
    var email by remember { mutableStateOf("Aminata.tremblay@example.com") }
    
    // État du dialogue
    var afficherDialogue by remember { mutableStateOf(false) }
    var nomTemporaire by remember { mutableStateOf("") }
    var erreurNom by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mon Profil") },
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            // Carte de profil
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Image de profil ronde
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.vladstudio_merry_snowman_1280x1024_signed),
                            contentDescription = "Photo de profil",
                            contentScale = ContentScale.Crop,  // Recadrage
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)  // Image ronde
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Nom
                    Text(
                        text = nom,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Courriel
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.Email,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = email,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Bouton Modifier
                    Button(
                        onClick = {
                            nomTemporaire = nom
                            erreurNom = null
                            afficherDialogue = true
                        }
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Modifier")
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Informations supplémentaires
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Informations du compte",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    InfoLigne("Membre depuis", "Janvier 2024")
                    InfoLigne("Statut", "Actif")
                    InfoLigne("Rôle", "Utilisateur")
                }
            }
        }
        
        // Dialogue de modification
        if (afficherDialogue) {
            AlertDialog(
                onDismissRequest = { afficherDialogue = false },
                title = { Text("Modifier le profil") },
                text = {
                    Column {
                        Text(
                            text = "Entrez votre nouveau nom :",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        OutlinedTextField(
                            value = nomTemporaire,
                            onValueChange = {
                                //nomTemporaire = it
                                nom = it
                                erreurNom = null
                            },
                            label = { Text("Nom") },
                            placeholder = { Text("Votre nom complet") },
                            isError = erreurNom != null,
                            supportingText = {
                                erreurNom?.let { Text(it) }
                                    ?: Text("Minimum 2 caractères")
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            // Validation
                            if (nomTemporaire.trim().length < 2) {
                                erreurNom = "Le nom doit contenir au moins 2 caractères"
                            } else {
                                nom = nomTemporaire.trim()
                                afficherDialogue = false
                            }
                        }
                    ) {
                        Text("Enregistrer")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { afficherDialogue = false }) {
                        Text("Annuler")
                    }
                }
            )
        }
    }
}

@Composable
private fun InfoLigne(label: String, valeur: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = valeur,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
