package ca.cegepgarneau.composants_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.cegepgarneau.composants_ui.ui.theme.Composants_uiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Composants_uiTheme {
                var currentScreen by remember { mutableStateOf("home") }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Apply the padding to the demo screen content to respect edge-to-edge
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(start = 16.dp, end = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                    Text(
                        text = "Démo Composants UI",
                        style = MaterialTheme.typography.headlineMedium
                    )

                        SectionTitle("Navigation et Scaffold")
                        Button(
                            onClick = { currentScreen = "scaffold" },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Voir la démo Scaffold + TopAppBar")
                        }


                        SectionTitle("Composants UI")
                        Button(
                            onClick = { currentScreen = "composant" },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Voir les composants ui")
                        }

                    }
                }

                if (currentScreen == "composant") {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        // Apply the padding to the demo screen content to respect edge-to-edge
                        Box(modifier = Modifier.padding(innerPadding)) {
                            ComponentsDemoScreen(
                                onNavigateToHome = { currentScreen = "home" }
                            )
                        }
                    }
                }
                if (currentScreen == "scaffold") {
                    ScaffoldDemoScreen(
                        onBack = { currentScreen = "home" }
                    )
                }
            }
        }
    }
}
