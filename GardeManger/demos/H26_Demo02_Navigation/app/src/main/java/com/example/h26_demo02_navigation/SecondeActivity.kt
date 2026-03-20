package com.example.h26_demo02_navigation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.h26_demo02_navigation.ui.theme.H26_Demo02_NavigationTheme

class SecondeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Récupération des données
        var intent: Intent = intent
        var data = intent.getStringExtra("Donnée")
        
        setContent {
            H26_Demo02_NavigationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding).fillMaxWidth()) {
                        Text("Seconde Activité", style = MaterialTheme.typography.titleLarge)
                        Button(onClick = {
                            // Destruction de cette activité forcera le retour
                            finish()
                        }) {
                            Text("Retour")
                        }
                        Text(text = data!!)
                    }
                }
            }
        }
    }
}

