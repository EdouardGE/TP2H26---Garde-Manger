package com.example.h26_demo04_persistancemini

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.h26_demo04_persistancemini.ui.theme.H26_Demo04_PersistanceMiniTheme
import java.text.SimpleDateFormat
import java.time.LocalDateTime

/**
 *
 * ATTENTION ! Ne décrit pas de layout en mode paysage
 *
 */

class MainActivity : ComponentActivity() {

    // déclaration pour instanciation ultérieure
    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Une app peut utiliser plusieurs SharedPreferences : on lui donne un nom
        // MODE_PRIVATE : seule cette application y aura accès
        sp = getSharedPreferences("PREFS", MODE_PRIVATE)

        enableEdgeToEdge()
        setContent {
            H26_Demo04_PersistanceMiniTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Visite(
                        modifier = Modifier.padding(innerPadding),
                        sp
                    )
                }
            }
        }
    }
}

@Composable
fun Visite(modifier: Modifier = Modifier, sharedPreferences: SharedPreferences) {
    var nomUtilisateur by rememberSaveable { mutableStateOf("") }
    var nbVisites by rememberSaveable { mutableStateOf(0) }
    var erreurNom by rememberSaveable { mutableStateOf<String?>(null) }
    var derniereVisite by rememberSaveable { mutableStateOf("") }

    var isClicked by rememberSaveable { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth().padding(top = 24.dp)
    ) {
        Text(
            text = "Votre nom",
            modifier = modifier
        )
        OutlinedTextField(
            value = nomUtilisateur,
            onValueChange = { nomUtilisateur = it
                            erreurNom = null},
            label = { Text("Nom") },
            placeholder = { Text("nom d'utilisateur") },
            isError = erreurNom != null,
            supportingText = {
                erreurNom?.let { Text(it) }
            },
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()

        )
        Button( onClick = {

            if (estNomVide(nomUtilisateur)) {
                erreurNom = "Vous devez saisir un nom"
            }
            else {
                // éditeur sur SharedPreferences
                val editor = sharedPreferences!!.edit()

                // on vérifie si ce nom est déjà présent
                if (sharedPreferences.contains(nomUtilisateur)) {
                    // Attention ! Toujours passer une valeur par défaut lorsqu'on get, car ne sait pas gérer les null
                    nbVisites = sharedPreferences!!.getInt(nomUtilisateur, 1)
                    nbVisites++
                    editor.putInt(nomUtilisateur, nbVisites)
                    derniereVisite = sharedPreferences.getString("DATE", LocalDateTime.now().toString())!!
                }
                // sinon, première visite
                else {
                    nbVisites = 1
                    editor.putInt(nomUtilisateur, nbVisites)
                    derniereVisite = LocalDateTime.now().toString()
                }
                editor.putString("DATE", LocalDateTime.now().toString())
                // commiter l'écriture
                editor.apply()
                isClicked = true
                // Affichage dans la pile des logs889
                Log.i("*** H26_Demo04", "$nomUtilisateur : $nbVisites")
                Log.i("*** H26_Demo04", "Dernière visite : $derniereVisite")
            }
        }) {
            Text(text = "Merci !")
        }
        val visite = if(nbVisites>1) {
                        "visites"
                    }
                    else {
                        "visite"
                    }
        if(isClicked) {
            Text(text = "$nbVisites $visite")
            Text(text = "Dernière visite : $derniereVisite")
        }
    }

}

fun estNomVide(nom: String): Boolean =
    nom.equals("")