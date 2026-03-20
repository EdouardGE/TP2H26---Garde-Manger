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

class AlimentViewModel(
    private val alimentDao: AlimentDao
) : ViewModel() {

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

    fun ajouter(aliment: Aliment) {
        viewModelScope.launch(Dispatchers.IO) {
            alimentDao.insert(aliment)
        }
    }

    fun modifier(aliment: Aliment) {
        viewModelScope.launch(Dispatchers.IO) {
            alimentDao.update(aliment)
        }
    }

    fun supprimer(aliment: Aliment) {
        viewModelScope.launch(Dispatchers.IO) {
            alimentDao.delete(aliment)
        }
    }

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

    fun toggleAchat(aliment: Aliment) {
        viewModelScope.launch(Dispatchers.IO) {
            val alimentMisAJour = aliment.copy(aAcheter = !aliment.aAcheter)
            alimentDao.update(alimentMisAJour)
        }
    }

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
