package com.example.h26_demo02_navigationcomposable

// Classe finale à cause des objects
// Permet le contrôle des valeurs de routes utilisées dans l'app
sealed class Routes(val route: String) {
    /*
    Pour rappel :
        object : singleton : une seule instance
        Ici, instance de Home et Profile
    Procure un alias pour les routes et leurs noms
     */
    object Home : Routes("home")
    object Profile : Routes("profile")
}