package ca.cegepgarneau.gardemanger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ca.cegepgarneau.gardemanger.data.AlimentDao
import ca.cegepgarneau.gardemanger.model.Aliment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel pour la logique métier de l’application.
 * Il fait le lien entre l’interface utilisateur et la bd.
 */
class AlimentViewModel(
    private val alimentDao: AlimentDao
) : ViewModel() {

    /**
     * StateFlow pour être observé facilement dans Compose.
     */
    val aliments: StateFlow<List<Aliment>> = alimentDao
        .getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val listeCourses: StateFlow<List<Aliment>> = alimentDao
        .getListeCourses()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    /**
     * Initialise les données de l’application.
     * Vérifie si la base est vide, insère des données initiales et notifie linterface via un callback
     */
    fun initialiser(onComplete: () -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            if (alimentDao.count() == 0) {
                insererDonneesInitiales()
            }

            _isLoading.value = false

            withContext(Dispatchers.Main) {
                onComplete()
            }
        }
    }

    /**
     * Ajoute un nouvel aliment dans la bd.
     */
    fun ajouter(aliment: Aliment) {
        viewModelScope.launch(Dispatchers.IO) {
            alimentDao.insert(aliment)
        }
    }

    /**
     * Met à jour un aliment existant.
     */
    fun modifier(aliment: Aliment) {
        viewModelScope.launch(Dispatchers.IO) {
            alimentDao.update(aliment)
        }
    }

    /**
     * Supprime un aliment spécifique.
     */
    fun supprimer(aliment: Aliment) {
        viewModelScope.launch(Dispatchers.IO) {
            alimentDao.delete(aliment)
        }
    }

    /**
     * Supprime tous les aliments de la bd.
     */
    fun supprimerTout(onComplete: () -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            alimentDao.deleteAll()
            _isLoading.value = false
            withContext(Dispatchers.Main) {
                onComplete()
            }
        }
    }

    /**
     * Inverse l’état "à acheter d’un aliment.
     * Ajouter ou retirer un aliment de la liste d’achats.
     */
    fun toggleAchat(aliment: Aliment) {
        viewModelScope.launch(Dispatchers.IO) {
            val alimentMisAJour = aliment.copy(aAcheter = !aliment.aAcheter)
            alimentDao.update(alimentMisAJour)
        }
    }

    /**
     * Insère une liste d’aliments par défaut si la base est vide, comme a linitialisation.
     */
    private suspend fun insererDonneesInitiales() {
        val alimentsInitiaux = listOf(
            Aliment(nom = "Lait", categorie = "Produits laitiers", quantite = 2, unite = "L"),
            Aliment(nom = "Pommes", categorie = "Fruits", quantite = 6, unite = "unité(s)"),
            Aliment(nom = "Carottes", categorie = "Légumes", quantite = 500, unite = "g"),
            Aliment(nom = "Poulet", categorie = "Viandes", quantite = 1, unite = "kg"),
            Aliment(nom = "Riz", categorie = "Céréales", quantite = 1, unite = "kg")
        )

        alimentsInitiaux.forEach { aliment ->
            alimentDao.insert(aliment)
        }
    }
}


/**
 * Class permettant de créer le ViewModel avec un paramètre (AlimentDao).
 */
class AlimentViewModelFactory(
    private val alimentDao: AlimentDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlimentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlimentViewModel(alimentDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
