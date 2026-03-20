package com.example.h26_demo02_navigationcomposable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.h26_correctionexerciceslayouts.screens.*
import com.example.h26_demo02_navigationcomposable.ui.theme.H26_Demo02_NavigationComposableTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            H26_Demo02_NavigationComposableTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                        ScreenMain()
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenMain() {
    // singleton
    val navController = rememberNavController()
    // NavHost définit un hôte ainsi que les composables navigables
    //      Également, une vue de départ
    NavHost(navController = navController, startDestination = Routes.Home.route) {
        // Naviguer vers Home : navController en paramètre
        // Home en aura besoin pour gérér la route vers Profile
        composable(Routes.Home.route) {
            Home(navController)
        }
        // Naviguer vers Profile : navController, nom et courriel en paramètre
        //      nom et courriel sont accédés par clés dans la table des arguments de navigation
        composable(Routes.Profile.route + "/{nom}" + "/{email}") { navBackStack ->
            val nomProfil = navBackStack.arguments?.getString("nom")
            val emailProfil = navBackStack.arguments?.getString("email")
            Profile(nomProfil, emailProfil, navController)
        }
    }
}
