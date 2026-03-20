package ca.cegepgarneau.gardemanger

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ca.cegepgarneau.gardemanger.data.AlimentDatabase
import ca.cegepgarneau.gardemanger.screens.FormulaireAliment
import ca.cegepgarneau.gardemanger.screens.GardeMangerScreen
import ca.cegepgarneau.gardemanger.screens.ListeCoursesScreen
import ca.cegepgarneau.gardemanger.ui.theme.GardeMangerTheme
import ca.cegepgarneau.gardemanger.viewmodel.AlimentViewModel
import ca.cegepgarneau.gardemanger.viewmodel.AlimentViewModelFactory
import kotlinx.coroutines.launch

/**
 * Activité principale de l’application.
 * Crée le ViewModel et charge l’interface Compose.
 */
class MainActivity : ComponentActivity() {


    /**
     * Création du ViewModel avec une factory (besoin du DAO en paramètre)
     */
    private val viewModel: AlimentViewModel by viewModels {
        AlimentViewModelFactory(
            AlimentDatabase.getDatabase(applicationContext).alimentDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GardeMangerTheme {
                GardeMangerApp(viewModel = viewModel)
            }
        }
    }
}

/**
 * Composable principal de l’application.
 * Il gère l’état global de l’interface, les onglets, le mode adminn et la communication avec le ViewModel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GardeMangerApp(
    viewModel: AlimentViewModel
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val aliments by viewModel.aliments.collectAsStateWithLifecycle()
    val listeCourses by viewModel.listeCourses.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
    var isAdminMode by rememberSaveable { mutableStateOf(false) }
    var menuExpanded by remember { mutableStateOf(false) }

    var showFormulaire by rememberSaveable { mutableStateOf(false) }
    var alimentEnEditionId by rememberSaveable { mutableStateOf<Int?>(null) }

    var showPermissionDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = androidx.compose.runtime.rememberCoroutineScope()

    /**
     * Initialise les données au premier affichage de l’app.
     */
    LaunchedEffect(Unit) {
        viewModel.initialiser()
    }

    /**
     * Lanceur de permission pour activer le mode admin.
     */
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { permissionAccordee ->
        if (permissionAccordee) {
            isAdminMode = true
        } else {
            val shouldShowRationale = activity?.let {
                ActivityCompat.shouldShowRequestPermissionRationale(
                    it,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            } ?: false

            if (shouldShowRationale) {
                showPermissionDialog = true
            } else {
                showSettingsDialog = true
            }
        }
    }

    val titresOnglets = listOf(
        stringResource(R.string.garde_manger),
        stringResource(R.string.liste_courses)
    )

    /**
     * Structure générale de l’écran
     */
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.app_name))
                },
                actions = {
                    IconButton(
                        onClick = { menuExpanded = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.menu_principal)
                        )
                    }

                    /**
                     * Menu principal contenant : Activation et désactivation du mode admin
                     */
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(
                                        if (isAdminMode) {
                                            R.string.desactiver_admin
                                        } else {
                                            R.string.activer_admin
                                        }
                                    )
                                )
                            },
                            onClick = {
                                menuExpanded = false

                                if (isAdminMode) {
                                    isAdminMode = false
                                } else {
                                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                }
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text(stringResource(R.string.vider_tout))
                            },
                            onClick = {
                                menuExpanded = false
                                viewModel.supprimerTout {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            context.getString(R.string.snackbar_tout_supprime)
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
            )
        },
        /**
         * Bouton flottant visible seulement pour admin et sur l’onglet garde-manger.
         */
        floatingActionButton = {
            if (isAdminMode && selectedTabIndex == 0) {
                FloatingActionButton(
                    onClick = {
                        alimentEnEditionId = null
                        showFormulaire = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.ajouter_aliment)
                    )
                }
            }
        }
    ) { innerPadding ->

        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            /**
             * Barre d’onglets pour naviguer entre le garde-manger et la liste de courses.
             */
            TabRow(selectedTabIndex = selectedTabIndex) {
                titresOnglets.forEachIndexed { index, titre ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(titre) }
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> {
                    GardeMangerScreen(
                        aliments = aliments,
                        isLoading = isLoading,
                        isAdminMode = isAdminMode,
                        onToggleAchat = { aliment ->
                            viewModel.toggleAchat(aliment)
                        },
                        onModifier = { aliment ->
                            alimentEnEditionId = aliment.id
                            showFormulaire = true
                        },
                        onSupprimer = { aliment ->
                            viewModel.supprimer(aliment)
                        },
                        modifier = Modifier
                    )
                }

                1 -> {
                    ListeCoursesScreen(
                        aliments = listeCourses,
                        isLoading = isLoading,
                        onRetirerDeLaListe = { aliment ->
                            viewModel.toggleAchat(aliment)
                        },
                        modifier = Modifier
                    )
                }
            }
        }
    }

    /**
     * Affichage du formulaire d’ajout ou de modification.
     * Si un identifiant est présent, cherche l’aliment correspondant.
     */
    if (showFormulaire) {
        val alimentEnEdition = alimentEnEditionId?.let { id ->
            aliments.firstOrNull { it.id == id }
        }
        FormulaireAliment(
            aliment = alimentEnEdition,
            onDismiss = {
                showFormulaire = false
                alimentEnEditionId = null
            },
            onConfirm = { aliment ->
                if (alimentEnEdition == null) {
                    viewModel.ajouter(aliment)
                } else {
                    viewModel.modifier(aliment)
                }

                showFormulaire = false
                alimentEnEditionId = null
            }
        )
    }

    /**
     * Dialogue affiché lorsque la permission a été refusée,
     */
    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = {
                Text(stringResource(R.string.permission_requise_titre))
            },
            text = {
                Text(stringResource(R.string.permission_requise_message))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showPermissionDialog = false
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                ) {
                    Text(stringResource(R.string.reessayer))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showPermissionDialog = false }
                ) {
                    Text(stringResource(R.string.annuler))
                }
            }
        )
    }

    /**
     * Dialogue affiché lorsque l’utilisateur a refusé la permission.
     */
    if (showSettingsDialog) {
        AlertDialog(
            onDismissRequest = { showSettingsDialog = false },
            title = {
                Text(stringResource(R.string.permission_requise_titre))
            },
            text = {
                Text(stringResource(R.string.permission_parametres_message))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSettingsDialog = false
                        ouvrirParametresApplication(context)
                    }
                ) {
                    Text(stringResource(R.string.ouvrir_parametres))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showSettingsDialog = false }
                ) {
                    Text(stringResource(R.string.annuler))
                }
            }
        )
    }
}
/**
 * Ouvre la page des paramètres de l’application
 * Utilisateur peut gérer les permissions.
 */
private fun ouvrirParametresApplication(context: Context) {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    )
    context.startActivity(intent)
}