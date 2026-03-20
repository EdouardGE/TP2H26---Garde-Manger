package com.example.h26_demo02_navigation

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.h26_demo02_navigation.ui.theme.H26_Demo02_NavigationTheme

class MainActivity : ComponentActivity() {

    // Déclaration d'un objet Launcher pour navigation vers activité avec résultats
    var activiteResultat: ActivityResultLauncher<Intent>? = null

    @SuppressLint("UnsafeIntentLaunch")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var message: String = ""
        // Callback pour navigation vers SousActivity qui renverra un résultat
        // Voir lancement l89
        // instanciation du launcher :
        //      classe anonyme ActivityResultContracts + méthode de rappel pour traiter résultat
        activiteResultat = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                // Récupération des données reçues : contenues dans l'intent renvoyé
                val data = result.data
                // la fonction de récupération est typée
                var nullableMessage: String? = data!!.getStringExtra("Message")
                Toast.makeText(applicationContext, nullableMessage, Toast.LENGTH_SHORT).show()
            }
        }

            setContent {
                H26_Demo02_NavigationTheme {
                    val context = LocalContext.current
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        //Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                        //AfficherEcran(innerPadding)

                        Column(
                            modifier = Modifier.padding(innerPadding).fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text("Démo Navigation", style = MaterialTheme.typography.titleLarge)

                            Button(onClick = {
                                intent = Intent(context, SousActivity::class.java)
                                // Méthode de classe Launcher
                                activiteResultat!!.launch(intent)
                            }) { Text(text = "Sous-Activité") }
                            Button(onClick = {
                                intent = Intent(context, SecondeActivity::class.java)
                                // passage de donnée
                                // "Clé" -> Valeur
                                intent.putExtra("Donnée", "Merci de votre visite")
                                context.startActivity(intent)
                            }) { Text(text = "Seconde Activité") }

                            Button(onClick = {
                                intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
                                startActivity(Intent.createChooser(intent, "Choose Application"))
                            }) { Text(text = "Web") }
                            Button(onClick = {
                                intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
                                startActivity(Intent.createChooser(intent, "Choose Application"))
                            }) { Text(text = "Contacts") }
                            Button(onClick = {
                                intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:46.793531,-71.262906"))
                                startActivity(Intent.createChooser(intent, "Choose Application"))
                            }) { Text(text = "Carte") }
                        }
                        //}
                    }
                }
            }


    }
}

/*@Composable
fun AfficherEcran(padding: PaddingValues) {
    val context = LocalContext.current
    Column(modifier = Modifier.padding(padding).fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
        Text("Démo Navigation", style = MaterialTheme.typography.titleLarge)

        Button( onClick = {

        } ) { Text(text = "Sous-Activité")}
        Button( onClick = {
            val intent = Intent(context, SecondeActivity::class.java)
            context.startActivity(intent)
        } ) { Text(text = "Seconde Activité")}

        Button( onClick = {
            val intent = Intent(context, SousActivity::class.java)
            // Méthode de classe Launcher
            activiteResultat!!.launch(intent)
        } ) { Text(text = "Sous-Activité")}
        Button( onClick = {} ) { Text(text = "Sous-Activité")}
        Button( onClick = {} ) { Text(text = "Sous-Activité")}
    }
}*/

