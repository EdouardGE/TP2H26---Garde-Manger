# Composants UI - Application de démonstration

Application Android démo pour le cours **420-14D-FX Programmation mobile** du Cégep Garneau.

Cette application présente les composants d'interface utilisateur de **Jetpack Compose** abordés dans le cours 5 : [Composants d'interface utilisateur](https://14d1.gitlab.io/docs/cours/5-composants-ui.html).

## Contenu de l'application

### 1. Écran d'accueil
Navigation vers deux sections principales :
- **Scaffold et TopAppBar** : Structure d'écran avec barre supérieure
- **Composants UI** : Galerie interactive des composants

### 2. Démo Scaffold
Présentation du composant `Scaffold` avec :
- `TopAppBar` avec bouton retour et actions
- `FloatingActionButton` pour les actions rapides
- Contenu principal avec `LazyColumn`

### 3. Démo Composants UI
Exemples interactifs organisés en sections :

#### Gestion d'état
- Compteur avec `remember` et `mutableStateOf`
- Syntaxe avec délégation (`by`)

#### Boutons
- `Button` standard, `OutlinedButton`, `TextButton`
- `IconButton` et `FloatingActionButton`
- États activé/désactivé selon les conditions

#### Champs de texte
- `TextField` et `OutlinedTextField`
- Champs de mot de passe avec visibilité
- Types de clavier spécialisés (`Email`, `Phone`, `Password`)

#### Validation et erreurs
- Validation en temps réel avec regex
- États d'erreur et messages d'aide
- Exemples pour email, téléphone canadien, code postal

#### Cases et interrupteurs
- `Checkbox` avec gestion d'état
- `Switch` pour les paramètres on/off
- `RadioButton` pour les choix exclusifs

#### Images et icônes
- `Image` avec ressources locales
- `Icon` Material Design
- Gestion du `ContentScale` et formes (cercle, arrondis)

#### Autres composants
- `Card` pour les conteneurs
- `Slider` pour les valeurs numériques
- `CircularProgressIndicator` pour le chargement
- `AlertDialog` pour les confirmations

## Structure technique

### Technologies utilisées
- **Kotlin** : Langage de programmation
- **Jetpack Compose** : Framework UI moderne
- **Material 3** : Design system de Google

### Architecture des fichiers

```
app/src/main/java/ca/cegepgarneau/composants_ui/
├── MainActivity.kt           # Point d'entrée avec navigation
├── ComponentsDemo.kt         # Écran de démo des composants
├── ScaffoldDemo.kt          # Démo du Scaffold et TopAppBar
└── ui/theme/                # Configuration du thème Material 3
```

### Concepts Compose démontrés

| Concept | Exemples dans l'app |
|---------|---------------------|
| État local | `remember { mutableStateOf() }` |
| Événements | `onClick`, `onValueChange` |
| Layouts | `Column`, `Row`, `LazyColumn` |
| Modifier | `padding`, `fillMaxWidth`, `clip` |
| Validation | Regex pour email/téléphone |
| Navigation | État conditionnel simple |

## Installation et exécution

1. **Cloner ou télécharger** le projet
2. **Ouvrir** dans Android Studio
3. **Synchroniser** Gradle (bouton "Sync Now")
4. **Exécuter** sur émulateur ou appareil Android


## License

Projet éducatif pour le Cégep Garneau - Session Hiver 2026
