package ca.cegepgarneau.gardemanger.screens

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ca.cegepgarneau.gardemanger.R
import ca.cegepgarneau.gardemanger.model.Aliment
import ca.cegepgarneau.gardemanger.ui.iconePourCategorie

/**
 * Écran principal du garde manger.
 * Affiche soit un indicateur de chargement, soit un message vide ou soit la liste des aliments dispos.
 *
 * @param aliments liste des aliments à afficher
 * @param isLoading indique si les données sont encore en chargement
 * @param isAdminMode détermine si le mode administrateur est actif
 * @param onToggleAchat callback appelé lorsqu’on clique sur un aliment
 * @param onModifier callback appelé pour modifier un aliment
 * @param onSupprimer callback appelé pour supprimer un aliment
 * @param modifier modificateur Compose optionnel
 */
@Composable
fun GardeMangerScreen(
    aliments: List<Aliment>,
    isLoading: Boolean,
    isAdminMode: Boolean,
    onToggleAchat: (Aliment) -> Unit,
    onModifier: (Aliment) -> Unit,
    onSupprimer: (Aliment) -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    if (aliments.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize().padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.aucun_aliment),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        return
    }

    /**
     * Affichage de la liste des aliments (LazyColumn) = optimiser laffichage d’une liste longue.
     */
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            items = aliments,
            key = { it.id }
        ) { aliment ->
            AlimentCard(
                aliment = aliment,
                isAdminMode = isAdminMode,
                onClick = { onToggleAchat(aliment) },
                onModifier = { onModifier(aliment) },
                onSupprimer = { onSupprimer(aliment) }
            )
        }
    }
}

/**
 * Carte représentant un seul aliment de la liste.
 * Ajouter ou retirer l’aliment de la liste d’achats.
 * Un appui long, en mode admin, ouvre le menu.
 * @param aliment aliment affiché dans la carte
 * @param isAdminMode indique si le mode administrateur est actif
 * @param onClick action déclenchée lors d’un clic simple
 * @param onModifier action déclenchée lors du choix "Modifier"
 * @param onSupprimer action déclenchée lors du choix "Supprimer"
 * @param modifier modificateur Compose qui est optionnel
 */
@Composable
private fun AlimentCard(
    aliment: Aliment,
    isAdminMode: Boolean,
    onClick: () -> Unit,
    onModifier: () -> Unit,
    onSupprimer: () -> Unit,
    modifier: Modifier = Modifier
) {
    var menuExpanded by remember { mutableStateOf(false) }

    val cardColors = if (aliment.aAcheter) {
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    } else {
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    }

    Card(
        modifier = modifier.fillMaxWidth().combinedClickable(
                onClick = onClick,
                onLongClick = {
                    if (isAdminMode) {
                        menuExpanded = true
                    }
                }
            ),
        colors = cardColors,
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Box {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = iconePourCategorie(aliment.categorie),
                    contentDescription = aliment.categorie,
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.size(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = aliment.nom,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = aliment.categorie,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = stringResource(
                            R.string.quantite_unite_format,
                            aliment.quantite,
                            aliment.unite
                        ),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    if (aliment.aAcheter) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = stringResource(R.string.a_acheter),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.size(6.dp))
                            Text(
                                text = stringResource(R.string.a_acheter),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            /**
             * Menu contextuel affiché lors d’un appui long en mode admin.
             * Permet de modifier ou supprimer l’aliment sélectionné.
             */
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.modifier)) },
                    onClick = {
                        menuExpanded = false
                        onModifier()
                    }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.supprimer)) },
                    onClick = {
                        menuExpanded = false
                        onSupprimer()
                    }
                )
            }
        }
    }
}
