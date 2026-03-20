package ca.cegepgarneau.gardemanger.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ca.cegepgarneau.gardemanger.R
import ca.cegepgarneau.gardemanger.model.Aliment

/**
 * Composable représentant le formulaire d’ajout et de modification d’un aliment.
 * Affiché sous forme de boîte AlertDialog.
 * @param aliment aliment existant à modifier (null si ajout)
 * @param onDismiss callback appelé lors de la fermeture du formulaire
 * @param onConfirm callback appelé lorsque les données sont valides
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormulaireAliment(
    aliment: Aliment? = null,
    onDismiss: () -> Unit,
    onConfirm: (Aliment) -> Unit
) {
    val categories = listOf(
        stringResource(R.string.categorie_fruits),
        stringResource(R.string.categorie_legumes),
        stringResource(R.string.categorie_produits_laitiers),
        stringResource(R.string.categorie_viandes),
        stringResource(R.string.categorie_cereales),
        stringResource(R.string.categorie_autre)
    )
    val unites = listOf(
        stringResource(R.string.unite_g),
        stringResource(R.string.unite_kg),
        stringResource(R.string.unite_l),
        stringResource(R.string.unite_ml),
        stringResource(R.string.unite_unites)
    )
    val texteAjouterAliment = stringResource(R.string.ajouter_aliment)
    val texteModifierAliment = stringResource(R.string.modifier_aliment)
    val texteNom = stringResource(R.string.nom)
    val texteCategorie = stringResource(R.string.categorie)
    val texteQuantite = stringResource(R.string.quantite)
    val texteUnite = stringResource(R.string.unite)
    val texteAjouter = stringResource(R.string.ajouter)
    val texteEnregistrer = stringResource(R.string.enregistrer)
    val texteAnnuler = stringResource(R.string.annuler)

    val erreurNomTexte = stringResource(R.string.erreur_nom)
    val erreurCategorieTexte = stringResource(R.string.erreur_categorie)
    val erreurQuantiteTexte = stringResource(R.string.erreur_quantite)
    val erreurUniteTexte = stringResource(R.string.erreur_unite)

    var nom by rememberSaveable { mutableStateOf(aliment?.nom ?: "") }
    var categorie by rememberSaveable { mutableStateOf(aliment?.categorie ?: "") }
    var quantite by rememberSaveable { mutableStateOf(aliment?.quantite?.toString() ?: "") }
    var unite by rememberSaveable { mutableStateOf(aliment?.unite ?: "") }

    var erreurNom by rememberSaveable { mutableStateOf<String?>(null) }
    var erreurCategorie by rememberSaveable { mutableStateOf<String?>(null) }
    var erreurQuantite by rememberSaveable { mutableStateOf<String?>(null) }
    var erreurUnite by rememberSaveable { mutableStateOf<String?>(null) }

    var categorieExpanded by rememberSaveable { mutableStateOf(false) }
    var uniteExpanded by rememberSaveable { mutableStateOf(false) }

    /**
     * Boîte de dialogue contenant le formulaire.
     */
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (aliment == null) texteAjouterAliment else texteModifierAliment
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = nom,
                    onValueChange = {
                        nom = it
                        erreurNom = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(texteNom) },
                    isError = erreurNom != null,
                    supportingText = {
                        erreurNom?.let { Text(text = it) }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true
                )

                /**
                 * Catégorie
                 */
                ExposedDropdownMenuBox(
                    expanded = categorieExpanded,
                    onExpandedChange = { categorieExpanded = !categorieExpanded }
                ) {
                    /**
                     * Quantité (chiffres)
                     */
                    OutlinedTextField(
                        value = categorie,
                        onValueChange = {},
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        readOnly = true,
                        label = { Text(texteCategorie) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = categorieExpanded)
                        },
                        isError = erreurCategorie != null,
                        supportingText = {
                            erreurCategorie?.let { Text(text = it) }
                        }
                    )

                    /**
                     * Unité de mesure
                     */
                    ExposedDropdownMenu(
                        expanded = categorieExpanded,
                        onDismissRequest = { categorieExpanded = false }
                    ) {
                        categories.forEach { categorieItem ->
                            DropdownMenuItem(
                                text = { Text(text = categorieItem) },
                                onClick = {
                                    categorie = categorieItem
                                    erreurCategorie = null
                                    categorieExpanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = quantite,
                    onValueChange = { nouvelleValeur ->
                        quantite = nouvelleValeur.filter { caractere -> caractere.isDigit() }
                        erreurQuantite = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(texteQuantite) },
                    isError = erreurQuantite != null,
                    supportingText = {
                        erreurQuantite?.let { Text(text = it) }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                ExposedDropdownMenuBox(
                    expanded = uniteExpanded,
                    onExpandedChange = { uniteExpanded = !uniteExpanded }
                ) {
                    OutlinedTextField(
                        value = unite,
                        onValueChange = {},
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        readOnly = true,
                        label = { Text(texteUnite) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = uniteExpanded)
                        },
                        isError = erreurUnite != null,
                        supportingText = {
                            erreurUnite?.let { Text(text = it) }
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = uniteExpanded,
                        onDismissRequest = { uniteExpanded = false }
                    ) {
                        unites.forEach { uniteItem ->
                            DropdownMenuItem(
                                text = { Text(text = uniteItem) },
                                onClick = {
                                    unite = uniteItem
                                    erreurUnite = null
                                    uniteExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val nomValide = nom.trim().length >= 2
                    val quantiteInt = quantite.toIntOrNull()
                    val quantiteValide = quantiteInt != null && quantiteInt > 0
                    val categorieValide = categorie.isNotBlank()
                    val uniteValide = unite.isNotBlank()

                    erreurNom = if (!nomValide) erreurNomTexte else null
                    erreurCategorie = if (!categorieValide) erreurCategorieTexte else null
                    erreurQuantite = if (!quantiteValide) erreurQuantiteTexte else null
                    erreurUnite = if (!uniteValide) erreurUniteTexte else null

                    if (nomValide && quantiteValide && categorieValide && uniteValide) {
                        onConfirm(
                            Aliment(
                                id = aliment?.id ?: 0,
                                nom = nom.trim(),
                                categorie = categorie,
                                quantite = quantiteInt,
                                unite = unite,
                                aAcheter = aliment?.aAcheter ?: false
                            )
                        )
                    }
                }
            ) {
                Text(
                    text = if (aliment == null) texteAjouter else texteEnregistrer
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = texteAnnuler)
            }
        }
    )
}
