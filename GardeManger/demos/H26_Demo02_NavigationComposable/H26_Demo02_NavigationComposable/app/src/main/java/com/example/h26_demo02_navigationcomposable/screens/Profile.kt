package com.example.h26_correctionexerciceslayouts.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.h26_demo02_navigationcomposable.R
import com.example.h26_demo02_navigationcomposable.Routes
import com.example.h26_demo02_navigationcomposable.ScreenMain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(nom: String?, email: String?, navController: NavHostController) {
    var afficherDialogue by remember { mutableStateOf(false) }
    var nom by remember { mutableStateOf(nom) }
    var email by remember { mutableStateOf(email) }

    var erreurNom by remember { mutableStateOf<String?>(null) }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(stringResource(R.string.app_name))
            },
            navigationIcon = {
                // Bouton Up ira vers Home
                IconButton(onClick = { navController.navigate(Routes.Home.route) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
                }

            }
        )
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()
                .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(R.drawable.snow),
                    contentDescription = "Photo de profil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = nom!!,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Courriel",
                    )
                    Text(text = email!!)
                }
                Button(onClick = {
                    afficherDialogue = true
                    erreurNom = null
                }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Modifier") }
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
                        value = nom!!,
                        onValueChange = {
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
                        if (nom!!.trim().length < 2) {
                            erreurNom = "Le nom doit contenir au moins 2 caractères"
                        } else {
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