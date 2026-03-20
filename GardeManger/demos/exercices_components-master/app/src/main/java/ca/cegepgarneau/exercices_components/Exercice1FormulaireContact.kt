package ca.cegepgarneau.exercices_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * Exercice 1 : Formulaire de contact
 */

// Objet de validation avec regex
object ValidateursContact {
    // Courriel : format standard
    private val regexEmail = Regex(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    )

    // Téléphone : formats canadiens
    // Accepte : 514-555-1234, (514) 555-1234, 5145551234
    private val regexTelephone = Regex(
        "^\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$"
    )

    fun estNomValide(nom: String): Boolean = nom.trim().length >= 2

    fun estEmailValide(email: String): Boolean = regexEmail.matches(email)

    fun estTelephoneValide(telephone: String): Boolean = regexTelephone.matches(telephone)

    fun estMessageValide(message: String): Boolean = message.trim().isNotEmpty()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Exercice1FormulaireContact(
    onRetour: () -> Unit = {}
) {
    // États des champs
    var nom by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telephone by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    // États des erreurs
    var erreurNom by remember { mutableStateOf<String?>(null) }
    var erreurEmail by remember { mutableStateOf<String?>(null) }
    var erreurTelephone by remember { mutableStateOf<String?>(null) }
    var erreurMessage by remember { mutableStateOf<String?>(null) }

    // État du Snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    // Vérifier si le formulaire est valide (pour activer/désactiver le bouton)
    val formulaireValide = ValidateursContact.estNomValide(nom) &&
            ValidateursContact.estEmailValide(email) &&
            ValidateursContact.estTelephoneValide(telephone) &&
            ValidateursContact.estMessageValide(message)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Formulaire de contact") },
                navigationIcon = {
                    IconButton(onClick = onRetour) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Champ Nom
            OutlinedTextField(
                value = nom,
                onValueChange = {
                    nom = it
                    erreurNom = null
                },
                label = { Text("Nom") },
                placeholder = { Text("Votre nom complet") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null)
                },
                isError = erreurNom != null,
                supportingText = {
                    erreurNom?.let { Text(it) }
                        ?: Text("Minimum 2 caractères")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Champ Courriel
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    erreurEmail = null
                },
                label = { Text("Courriel") },
                placeholder = { Text("exemple@domaine.com") },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                isError = erreurEmail != null,
                supportingText = {
                    erreurEmail?.let { Text(it) }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Champ Téléphone
            OutlinedTextField(
                value = telephone,
                onValueChange = {
                    telephone = it
                    erreurTelephone = null
                },
                label = { Text("Téléphone") },
                placeholder = { Text("514-555-1234") },
                leadingIcon = {
                    Icon(Icons.Default.Phone, contentDescription = null)
                },
                isError = erreurTelephone != null,
                supportingText = {
                    erreurTelephone?.let { Text(it) }
                        ?: Text("Format: 514-555-1234")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Champ Message (multiligne)
            OutlinedTextField(
                value = message,
                onValueChange = {
                    message = it
                    erreurMessage = null
                },
                label = { Text("Message") },
                placeholder = { Text("Écrivez votre message ici...") },
                isError = erreurMessage != null,
                supportingText = {
                    erreurMessage?.let { Text(it) }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                minLines = 4,
                maxLines = 6,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            // Bouton Envoyer
            Button(
                onClick = {
                    // Validation finale
                    var estValide = true

                    if (!ValidateursContact.estNomValide(nom)) {
                        erreurNom = "Le nom doit contenir au moins 2 caractères"
                        estValide = false
                    }

                    if (!ValidateursContact.estEmailValide(email)) {
                        erreurEmail = "Format de courriel invalide"
                        estValide = false
                    }

                    if (!ValidateursContact.estTelephoneValide(telephone)) {
                        erreurTelephone = "Format invalide (ex: 514-555-1234)"
                        estValide = false
                    }

                    if (!ValidateursContact.estMessageValide(message)) {
                        erreurMessage = "Le message ne peut pas être vide"
                        estValide = false
                    }

                    if (estValide) {
                        // Simuler l'envoi

                        // Réinitialiser le formulaire
                        nom = ""
                        email = ""
                        telephone = ""
                        message = ""
                    }
                },
                enabled = formulaireValide,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text("Envoyer")
            }
        }
    }
}


// @Preview(showBackground = true)
@Composable
fun PreviewExercice1FormulaireContact() {
    Exercice1FormulaireContact()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartePersonnalisee() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Accueil", "Recherche", "Profil")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.Search,
        Icons.Default.Person
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Action effectuée !",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            ) {
                Icon(Icons.Default.Add, "Ajouter")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text("Écran : ${items[selectedItem]}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCartePersonnalisee() {
    CartePersonnalisee()
}

