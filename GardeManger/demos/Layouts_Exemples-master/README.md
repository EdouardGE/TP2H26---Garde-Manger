# Layouts Exemples - Application de démonstration

Application Android démo pour le cours **420-14D-FX Programmation mobile** du Cégep Garneau.

Cette application présente les **layouts de base de Jetpack Compose** abordés dans le cours 3 : [Première application et éléments de base](https://14d1.gitlab.io/docs/cours/3-premiere-app.html).


## Contenu de l'application

L'application présente 5 onglets de démonstration interactive :

### 1. Box Layout
**Équivalent** : FrameLayout XML

Démontre l'empilement d'éléments :
- Superposition de composants (comme des calques)
- Alignement avec `Alignment.TopStart`, `Alignment.Center`, `Alignment.BottomEnd`
- Exemple : carré rouge avec texte centré et badge en coin

### 2. Column Layout  
**Équivalent** : LinearLayout vertical XML

Présente la disposition verticale :
- Empilement vertical d'éléments
- Options d'alignement : `Alignment.CenterHorizontally`, `Alignment.End`
- Espacement avec `Arrangement.spacedBy()`
- Exemples avec boutons et zones colorées

### 3. Row Layout
**Équivalent** : LinearLayout horizontal XML

Montre la disposition horizontale :
- Alignement horizontal d'éléments
- Options d'alignement : `Alignment.CenterVertically`, `Alignment.Bottom`
- Distribution de l'espace avec `Arrangement.SpaceBetween`
- Exemples avec boutons colorés

### 4. Weight (Poids)
**Équivalent** : `layout_weight` XML

Démontre la répartition proportionnelle :
- `Modifier.weight(1f)` pour partager l'espace
- Distribution inégale (2f vs 1f vs 1f)
- Cas d'usage : barres de progression, colonnes flexibles

### 5. Scroll (Défilement)
**Équivalent** : ScrollView XML

Présente le défilement vertical :
- `verticalScroll()` avec `rememberScrollState()`
- Liste de 30 cartes pour démonstration
- Gestion automatique du scroll

## Structure technique

### Technologies utilisées
- **Kotlin** : Langage de programmation
- **Jetpack Compose** : Framework UI moderne
- **Material 3** : Design system

### Architecture des fichiers

```
app/src/main/java/ca/cegepgarneau/layouts_exemples/
├── MainActivity.kt           # Point d'entrée de l'application
├── LayoutsDemo.kt           # Écrans de démo des layouts
└── ui/theme/                # Configuration du thème Material 3
```

### Concepts Compose démontrés

| Concept | Équivalent XML | Exemples dans l'app |
|---------|----------------|---------------------|
| `Box` | `FrameLayout` | Superposition, alignement |
| `Column` | `LinearLayout vertical` | Liste verticale, centrage |
| `Row` | `LinearLayout horizontal` | Barre d'outils, distribution |
| `Modifier.weight()` | `layout_weight` | Colonnes flexibles |
| `verticalScroll()` | `ScrollView` | Liste scrollable |

### Options d'alignement couvertes

**Column** :
- `horizontalAlignment` : `Start`, `CenterHorizontally`, `End`
- `verticalArrangement` : `spacedBy()`, `SpaceBetween`

**Row** :
- `verticalAlignment` : `Top`, `CenterVertically`, `Bottom`
- `horizontalArrangement` : `spacedBy()`, `SpaceBetween`

**Box** :
- `contentAlignment` : `TopStart`, `Center`, `BottomEnd`

## Installation et exécution

1. **Cloner ou télécharger** le projet
2. **Ouvrir** dans Android Studio
3. **Synchroniser** Gradle (bouton "Sync Now")
4. **Exécuter** sur émulateur ou appareil Android

## Code source clé

### Navigation par onglets
```kotlin
ScrollableTabRow(selectedTabIndex = selectedLayout) {
    listOf("Box", "Column", "Row", "Weight", "Scroll").forEachIndexed { index, title ->
        Tab(selected = selectedLayout == index, onClick = { selectedLayout = index })
    }
}
```

### Exemple Box avec alignement
```kotlin
Box(modifier = Modifier.fillMaxSize()) {
    Text("TopStart", modifier = Modifier.align(Alignment.TopStart))
    Text("Center", modifier = Modifier.align(Alignment.Center))
    Text("BottomEnd", modifier = Modifier.align(Alignment.BottomEnd))
}
```

### Exemple Column avec options
```kotlin
Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp)
) {
    Text("Élément centré 1")
    Text("Élément centré 2")
}
```

## License

Projet éducatif pour le Cégep Garneau - Session Hiver 2026

