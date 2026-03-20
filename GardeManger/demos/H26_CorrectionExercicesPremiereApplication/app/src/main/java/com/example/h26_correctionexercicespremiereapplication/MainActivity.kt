package com.example.h26_correctionexercicespremiereapplication

import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.h26_correctionexercicespremiereapplication.ui.theme.H26_CorrectionExercicesPremiereApplicationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            H26_CorrectionExercicesPremiereApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    /**
                     * Exercices :
                     *    Dans tous les cas, on place les composables dans une colonne afin d'appliquer l'innerpadding
                     */

                    /**
                     * Exercice 1.a
                     * Note : la carte sera centrée sur l'horizontale à l'écran :
                     *    1. fillMaxWidth étire la colonne sur la largeur
                     *    2. Alignment.CenterHorizontally centre le contenu
                     */
                    Column(modifier = Modifier.padding(innerPadding).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        CarteVisite("Matthieu Hermet",
                            "Technique de l'Informatique",
                            "mhermet@cegepgarneau.ca")
                    }

                    /**
                     * Exercice 1.b
                     * Note : la carte sera centrée à l'écran :
                     *    1. fillMaxSpace étire la colonne sur les deux axes
                     *    2. Alignment.CenterHorizontally centre le contenu sur l'horizontale
                     *    3. Arrangement.Center centre le contenu sur la verticale
                     */
                    /*Column(modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        CarteVisite("Matthieu Hermet",
                            "Technique de l'Informatique",
                            "mhermet@cegepgarneau.ca")
                    }*/

                    /**
                     * Exercice 2
                     */
                    /*Column(modifier = Modifier.padding(innerPadding)) {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.padding(12.dp)) {
                            EtiquetteProduit("Café", "2.50 $")
                            EtiquetteProduit("Chocolat", "2.50 $")
                            EtiquetteProduit("Thé", "2.50 $")
                        }
                    }*/

                    /**
                     * Exercice 3
                     * Note : le centrage de la fiche est un effet du padding appliqué à Surface
                     */
                    /*Column(modifier = Modifier.padding(innerPadding)) {
                        Surface(color = Color(0xFFE2D3D3), // Gris clair
                            modifier = Modifier.padding(32.dp).shadow(9.dp)) {
                            FicheEtudiant("Nina Gagnon", "Québec", "Hiver 2026")
                        }
                    }*/
                }
            }
        }
    }
}


@Composable
fun CarteVisite(nom: String, programme: String, courriel: String, modifier: Modifier = Modifier) {
    Surface(
        color = Color(0xFFE3F2FD), // Bleu clair
        modifier = modifier.padding(16.dp)
    ) {
        Column(modifier = modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = nom,
                // Utilisez fontSize et fontWeight pour le style
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                // Utilisez modifier.padding pour ajouter de l'espace si nécessaire
                modifier = modifier.padding(top = 8.dp)
            )
            Text(text = programme)
            Text(text = courriel, color = Color.LightGray, modifier = modifier.padding(bottom = 8.dp))
        }
    }
}


@Composable
fun EtiquetteProduit(nom: String, prix: String, modifier: Modifier = Modifier) {
    Surface(color = Color.LightGray) {
        Column(modifier = modifier.padding(12.dp).fillMaxWidth()) {
            Text(text = nom, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = prix)
        }
    }
}


@Composable
fun LigneInfo(libelle: String, valeur: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        // Arrangement.SpaceBetween maximise la séparation entre les éléments de la rangée
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = libelle)
        Text(text = valeur)
    }
}

@Composable
fun FicheEtudiant(nom: String, ville: String, session: String) {
    Column {
        LigneInfo("Nom :", nom)
        LigneInfo("Ville :", ville)
        LigneInfo("Session :", session)
    }
}