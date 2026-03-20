package com.example.h26_demo02_navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.h26_demo02_navigation.ui.theme.H26_Demo02_NavigationTheme

class SousActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            H26_Demo02_NavigationTheme {
                var message by remember { mutableStateOf("") }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding).fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text("Sous-Activité", style = MaterialTheme.typography.titleLarge)
                        TextField(
                            value = message,
                            onValueChange = { message = it },
                            label = { Text("Message") },
                            placeholder = { Text("message") },
                            modifier = Modifier.fillMaxWidth(),
                            // Spécification du type de clavier affiché
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            )
                        )
                        Button(onClick = {
                            val intent = intent
                            // passage de valeur - noter qu'ici la fonction d'affectation n'est pas typée contrairement à celle de récupération
                            intent.putExtra("Message", message)
                            // Active la navigation retour vers l'appelant
                            setResult(RESULT_OK, intent)
                            finish()
                        }) {
                            Text("Envoyer")
                        }
                    }
                }
            }
        }
    }
}


