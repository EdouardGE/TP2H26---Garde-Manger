package com.example.h26_demo03_saverotation

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.h26_demo03_saverotation.ui.theme.H26_Demo03_SaveRotationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            H26_Demo03_SaveRotationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    // rememberSaveable : utilisera le bundle (savedInstanceState) pour stocker les valeurs lorsqu'il y a destruction d'activité
    var label by rememberSaveable { mutableStateOf("Aller") }
    var message by rememberSaveable { mutableStateOf("") }

    // Afin d'accéder aux valeurs d'orientation
    val configuration = LocalConfiguration.current

    when (configuration.orientation) {
        // Écran en mode paysage
        Configuration.ORIENTATION_LANDSCAPE -> {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
            ) {
                Text(
                    text = "Hello $name!",
                    modifier = modifier
                )
                TextField(
                    value = message,
                    onValueChange = { message = it },
                    label = { Text("Message") },
                    placeholder = { Text("message") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()

                )
                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                    Button( onClick = {
                        if(label.equals("Aller"))
                            label = "Revenir"
                        else
                            label = "Aller"
                    }) {
                        Text(text = label)
                    }
                    Text(text = message)
                }

            }
        }
        // Écran en mode portrait
        else -> {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
            ) {
                Text(
                    text = "Hello $name!",
                    modifier = modifier
                )
                TextField(
                    value = message,
                    onValueChange = { message = it },
                    label = { Text("Message") },
                    placeholder = { Text("message") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()

                )
                Button( onClick = {
                    if(label.equals("Aller"))
                        label = "Revenir"
                    else
                        label = "Aller"
                }) {
                    Text(text = label)
                }
                Text(text = message)
            }
        }
    }



}