package ca.cegepgarneau.composants_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ComponentsDemoScreen(onNavigateToHome: () -> Unit) {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Démo Composants UI",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = onNavigateToHome,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Retour accueil")
        }
        
        SectionTitle("1. Gestion de l'état avec remember")
        StateManagementDemo()
        
        SectionTitle("2. Boutons")
        ButtonsDemo()
        
        SectionTitle("3. Champs de texte (TextField)")
        TextFieldsDemo()
        
        SectionTitle("4. Validation et erreurs")
        ValidationDemo()
        
        SectionTitle("5. Cases à cocher et interrupteurs")
        SelectionControlsDemo()
        
        SectionTitle("6. Images et Icônes")
        ImagesDemo()
        
        SectionTitle("7. Autres composants utiles")
        OtherComponentsDemo()
        

        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun SectionTitle(title: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

// 1. Gestion de l'état
@Composable
fun StateManagementDemo() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            // remember = L'état de compteur est "mémorisé" entre les recompositions
            // mutableIntStateOf = observable<Int> autour de compteur : conserve la dernière valeur connue
            // by = sert la variable compteur telle qu'observable par mutableIntStateOf
            var compteur by remember { mutableIntStateOf(0) }
            Text("Compteur : $compteur")
            Button(onClick = { compteur++ }) {
                Text("Incrémenter")
            }

            // Code équivalent à
            // val compteur = remember { mutableIntStateOf(0) }
            // Text("Compteur : ${compteur.intValue}")  // Accès via .intValue
            // compteur.intValue++
        }
    }
}


// 2. Boutons
@Composable
fun ButtonsDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Bouton standard
        Button(onClick = { }) {
            Text("Confirmer")
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Bouton avec contour
            OutlinedButton(onClick = { }) {
                Text("Annuler")
            }
            
            // Bouton texte
            TextButton(onClick = { }) {
                Text("En savoir plus")
            }
        }

        // Bouton avec icône
        Button(onClick = { }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            // espace entre icône et texte
            Spacer(Modifier.width(8.dp))
            Text("Envoyer")
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Bouton icône seul
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Ajouter aux favoris"
                )
            }
            
            // Floating Action Button
            FloatingActionButton(onClick = { }) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter")
            }
        }
        
        // Bouton activé/désactivé
        var email by remember { mutableStateOf("") }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email pour activer le bouton 'Go'") },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = { },
                // retournera false si vide
                enabled = email.isNotBlank()
            ) {
                Text("Go")
            }
        }
    }
}

// 3. Champs de texte
@Composable
fun TextFieldsDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // TextField de base
        var nom by remember { mutableStateOf("") }
        var age by remember { mutableStateOf("") }

        TextField(
            value = nom,
            // it : retour de onValueChange
            onValueChange = { nom = it },
            label = { Text("Nom") },
            placeholder = { Text("Entrez votre nom") },
            modifier = Modifier.fillMaxWidth()
        )

        Text(if (nom == "") "Merci !" else "Merci $nom !")

        TextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            placeholder = { Text("Entrez votre âge") },
            modifier = Modifier.fillMaxWidth(),
            // Spécification du type de clavier affiché
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
        )
        )

        var courriel by remember { mutableStateOf("") }
        OutlinedTextField(
            value = courriel,
            onValueChange = { courriel = it },
            label = { Text("Courriel") },
            // Champ introduit par une icône
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = null)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Champ mot de passe
        var motDePasse by remember { mutableStateOf("") }
        var visible by remember { mutableStateOf(false) }
        
        OutlinedTextField(
            value = motDePasse,
            onValueChange = { motDePasse = it },
            label = { Text("Mot de passe") },
            singleLine = true,
            visualTransformation = if (visible) 
                VisualTransformation.None 
            else 
                PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            trailingIcon = {
                IconButton(onClick = { visible = !visible }) {
                    Icon(
                        imageVector = if (visible) 
                            Icons.Default.VisibilityOff 
                        else 
                            Icons.Default.Visibility,
                        contentDescription = if (visible) 
                            "Masquer" 
                        else 
                            "Afficher"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// 4. Validation
object Validateurs {
    fun estEmailValide(email: String): Boolean = 
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
fun ValidationDemo() {
    var email by remember { mutableStateOf("") }
    var estErreur by remember { mutableStateOf(false) }
    
    Column {
        OutlinedTextField(
            value = email,
            onValueChange = { 
                email = it
                estErreur = false
            },
            label = { Text("Courriel avec validation") },
            isError = estErreur,
            supportingText = {
                if (estErreur) {
                    Text(
                        text = "Courriel invalide",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            trailingIcon = {
                if (estErreur) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = "Erreur",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        
        Button(
            onClick = {
                estErreur = !Validateurs.estEmailValide(email)
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Valider")
        }
    }
}

// 5. Cases à cocher et interrupteurs
@Composable
fun SelectionControlsDemo() {
    Column {
        // Checkbox
        var emailActif by remember { mutableStateOf(true) }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                // rendre la zone cliquable + action au clic
                // sinon, seule la case à cocher est cliquable
                .clickable { emailActif = !emailActif }
                .padding(vertical = 4.dp)
        ) {
            Checkbox(
                checked = emailActif,
                onCheckedChange = { emailActif = it }
            )
            Text(
                text = "Notifications par courriel",
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Text(
            if (emailActif) "Courriel actif" else "Courriel inactif"
        )
        
        // Switch
        var modeNuit by remember { mutableStateOf(false) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Mode nuit")
            Switch(
                checked = modeNuit,
                onCheckedChange = { modeNuit = it }
            )
        }
        
        // RadioButton
        val langues = listOf("Français", "English", "Español")
        var selection by remember { mutableStateOf(langues[0]) }
        
        Text(
            text = "Langue:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
        
        langues.forEach { langue ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selection = langue }
                    .padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = (langue == selection),
                    onClick = { selection = langue }
                )
                Text(
                    text = langue,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

// 6. Images
@Composable
fun ImagesDemo() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text("Note: Icônes utilisées comme exemples d'images")
        
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            // Simulation Image locale
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Exemple 1",
                modifier = Modifier.size(64.dp)
            )

            // Simulation Image avec clip
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profil",
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
        
        // Icônes Material
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Icon(Icons.Default.Settings, contentDescription = "Paramètres")
            Icon(Icons.Default.Search, contentDescription = "Recherche")
            Icon(Icons.Default.Favorite, contentDescription = "Favoris")
        }
    }
}

// 7. Autres composants
@Composable
fun OtherComponentsDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = "Utilisateur",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "user@example.com",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Slider
        var volume by remember { mutableFloatStateOf(0.5f) }
        Column {
            Text("Volume : ${(volume * 100).toInt()}%")
            Slider(
                value = volume,
                onValueChange = { volume = it },
                valueRange = 0f..1f,
                steps = 9
            )
        }

        // CircularProgressIndicator
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.width(32.dp))
            CircularProgressIndicator(
                progress = { 0.7f },
                modifier = Modifier.size(40.dp),
                strokeWidth = 4.dp,
            )
        }

        // AlertDialog
        var afficherDialogue by remember { mutableStateOf(false) }
        Button(onClick = { afficherDialogue = true }) {
            Text("Ouvrir le dialogue")
        }
        
        if (afficherDialogue) {
            AlertDialog(
                // Action au clic hors zone
                onDismissRequest = { afficherDialogue = false },
                // Affichage du Dialog
                title = { Text("Confirmer") },
                text = { Text("Ceci est un exemple de dialogue.") },
                // !!! Boutons inclus : par défaut : OK et Annuler
                // OK : quoi faire ? Ici, afficherDialogue à false forcera la sortie
                confirmButton = {
                    TextButton(onClick = { afficherDialogue = false }) {
                        Text("OK")
                    }
                },
                // Annuler
                // Cas typique
                dismissButton = {
                    TextButton(onClick = { afficherDialogue = false }) {
                        Text("Annuler")
                    }
                }
            )
        }
        
        // Snackbar
        // Note: Pour une vraie application, le SnackbarHost devrait être au niveau supérieur (Scaffold principal)
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(MaterialTheme.shapes.medium)
                .padding(4.dp)
        ) {
             Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ) { padding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Élément sauvegardé !",
                                    actionLabel = "Fermer",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    ) {
                        Text("Tester Snackbar")
                    }
                }
            }
        }
    }
}
