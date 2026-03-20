package com.example.h26_correctionexerciceslayouts.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.h26_demo02_navigationcomposable.R
import com.example.h26_demo02_navigationcomposable.Routes
import com.example.h26_demo02_navigationcomposable.ScreenMain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController) {
    var nom by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telephone by remember { mutableStateOf("") }
    var texte by remember { mutableStateOf("") }

    var erreurEmail by remember { mutableStateOf<String?>(null) }
    var erreurTelephone by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(stringResource(R.string.app_name))
            }
        )
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {



            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Champ nom
                OutlinedTextField(
                    value = nom,
                    onValueChange = {
                        nom = it
                        erreurEmail = null
                    },
                    label = { Text("Nom") },
                    supportingText = {
                        Text("Min. 2 caractères")
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Champ courriel
                OutlinedTextField(
                    value = "m@m.com",
                    //value = email,
                    onValueChange = {
                        email = it
                        erreurEmail = null
                    },
                    label = { Text("Courriel") },
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

                // Champ téléphone
                OutlinedTextField(
                    value = telephone,
                    onValueChange = {
                        telephone = it
                        erreurTelephone = null
                    },
                    label = { Text("Téléphone") },
                    leadingIcon = {
                        Icon(Icons.Default.Phone, contentDescription = null)
                    },
                    isError = erreurTelephone != null,
                    supportingText = {
                        erreurTelephone?.let { Text(it) }
                            ?: Text("Format 418-555-5554")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Champ texte
                OutlinedTextField(
                    value = texte,
                    onValueChange = { texte = it },
                    label = { Text("Message") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    minLines = 4,
                    maxLines = 10
                )

                // Bouton de soumission
                Button(
                    onClick = {

                        // Validation
                        var formulaireValide = true

                        if (!Validateurs.estEmailValide(email)) {
                            erreurEmail = "Format de courriel invalide"
                            formulaireValide = false
                        }

                        if (!Validateurs.estTelephoneValide(telephone)) {
                            erreurTelephone = "Format: 514-555-1234"
                            formulaireValide = false
                        }

                        if (formulaireValide) {
                            // Soumettre le formulaire : appel à Profile
                            // Note : le navController passé à Profile sera ajouté dans le graphe
                            navController.navigate(Routes.Profile.route
                                    + "/$nom"
                                    + "/$email"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = (nom.isNotBlank() && email.isNotBlank() && telephone.isNotBlank() && texte.isNotBlank())
                ) {
                    Text("S'inscrire")
                }
            }




        }
    }




}

object Validateurs {
    // Courriel : format standard
    private val regexEmail = Regex(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    )

    // Téléphone : formats canadiens
    // Accepte : 514-555-1234, (514) 555-1234, 5145551234
    private val regexTelephone = Regex(
        "^\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$"
    )

    // Code postal canadien : A1A 1A1 ou A1A1A1
    private val regexCodePostal = Regex(
        "^[A-Za-z]\\d[A-Za-z][ -]?\\d[A-Za-z]\\d$"
    )

    // Fonctions de validation

    fun estEmailValide(email: String): Boolean =
        regexEmail.matches(email)

    fun estTelephoneValide(telephone: String): Boolean =
        regexTelephone.matches(telephone)

    fun estCodePostalValide(codePostal: String): Boolean =
        regexCodePostal.matches(codePostal)
}
