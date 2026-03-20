package ca.cegepgarneau.layouts_exemples

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// =============================================================================
// DÉMO DES LAYOUTS COMPOSE - Équivalents aux layouts XML
// =============================================================================

/**
 * Écran principal avec navigation entre les différents exemples de layouts
 */
@Composable
fun LayoutsDemoScreen() {
    var selectedLayout by remember { mutableIntStateOf(0) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding() // Ajoute une marge pour éviter la barre de status
    ) {
        // Barre de navigation
        ScrollableTabRow(selectedTabIndex = selectedLayout) {
            listOf("Box", "Column", "Row", "Weight", "Scroll").forEachIndexed { index, title ->
                Tab(
                    selected = selectedLayout == index,
                    onClick = { selectedLayout = index },
                    text = { Text(title) }
                )
            }
        }
        
        // Contenu selon l'onglet sélectionné
        when (selectedLayout) {
            0 -> BoxLayoutDemo()      // Équivalent FrameLayout
            1 -> ColumnLayoutDemo()   // Équivalent LinearLayout vertical
            2 -> RowLayoutDemo()      // Équivalent LinearLayout horizontal
            3 -> WeightDemo()         // Équivalent layout_weight
            4 -> ScrollDemo()         // Équivalent ScrollView
        }
    }
}

// =============================================================================
// BOX - Équivalent de FrameLayout
// =============================================================================
// Les éléments sont empilés les uns sur les autres
// Le dernier élément est au-dessus (comme les calques)

@Composable
fun BoxLayoutDemo() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF008577)) // Fond vert
    ) {
        // Carré rouge en haut à gauche (comme view2 dans frame_layout.xml)
        Box(
            modifier = Modifier
                .padding(start = 32.dp, top = 32.dp)
                .size(200.dp)
                .background(Color.Red)
        )
        
        // Texte "Center" au centre avec rotation
        Text(
            text = "Center",
            color = Color.White,
            fontSize = 30.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color(0xAA000000))
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .graphicsLayer(
                    rotationZ = -8f,
                    rotationX = 11f,
                    rotationY = -36f
                )
        )
        
        // Texte "Start" en haut à gauche : TopStart
        Text(
            text = "Start",
            color = Color.White,
            fontSize = 25.sp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .background(Color(0xAA000000))
                .padding(horizontal = 24.dp, vertical = 8.dp)
        )
        
        // Texte "End" en haut à droite : TopEnd
        Text(
            text = "End",
            color = Color.White,
            fontSize = 25.sp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .background(Color(0xAA000000))
                .padding(horizontal = 24.dp, vertical = 8.dp)
        )
        
        // Texte "Bottom|Start" en bas à gauche : BottomStart
        Text(
            text = "Bottom|Start",
            color = Color.White,
            fontSize = 25.sp,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .background(Color(0xAA000000))
                .padding(horizontal = 24.dp, vertical = 8.dp)
        )
        
        // Texte "Bottom|End" en bas à droite : BottomEnd
        Text(
            text = "Bottom|End",
            color = Color.White,
            fontSize = 25.sp,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .background(Color(0xAA000000))
                .padding(horizontal = 24.dp, vertical = 8.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun BoxLayoutDemoPreview() {
    BoxLayoutDemo()
}


// =============================================================================
// COLUMN - Équivalent de LinearLayout (orientation="vertical")
// =============================================================================
// Les éléments sont disposés verticalement de haut en bas

@Composable
fun ColumnLayoutDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp) // Espacement entre éléments
    ) {
        Text("Column = LinearLayout vertical", fontSize = 20.sp)
        
        Button(onClick = {}) { Text("Bouton 1") }
        Button(onClick = {}) { Text("Bouton 2") }
        Button(onClick = {}) { Text("Bouton 3") }
        
        // Column avec alignement centré
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Contenu centré")
            Text("horizontalAlignment = CenterHorizontally")
        }
        
        // Column avec alignement à droite
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0E0E0))
                .padding(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text("Aligné à droite")
            Text("horizontalAlignment = End")
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun ColumnLayoutDemoPreview() {
    ColumnLayoutDemo()
}

// =============================================================================
// ROW - Équivalent de LinearLayout (orientation="horizontal")
// =============================================================================
// Les éléments sont disposés horizontalement de gauche à droite

@Composable
fun RowLayoutDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Row = LinearLayout horizontal", fontSize = 20.sp)
        
        // Row simple
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = {}) { Text("A") }
            Button(onClick = {}) { Text("B") }
            Button(onClick = {}) { Text("C") }
        }
        
        // Row avec espacement uniforme (SpaceEvenly)
        Text("Arrangement.SpaceEvenly :")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {}) { Text("1") }
            Button(onClick = {}) { Text("2") }
            Button(onClick = {}) { Text("3") }
        }
        
        // Row avec espacement aux extrémités (SpaceBetween)
        Text("Arrangement.SpaceBetween :")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0E0E0))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {}) { Text("Gauche") }
            Button(onClick = {}) { Text("Droite") }
        }
        
        // Row avec alignement vertical
        Text("verticalAlignment = CenterVertically :")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color.LightGray),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Texte", modifier = Modifier.padding(8.dp))
            Button(onClick = {}) { Text("Bouton") }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun RowLayoutDemoPreview() {
    RowLayoutDemo()
}

// =============================================================================
// WEIGHT - Équivalent de layout_weight
// =============================================================================
// Permet de distribuer l'espace disponible proportionnellement

@Composable
fun WeightDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Modifier.weight() = layout_weight", fontSize = 20.sp)
        
        // Équivalent : deux boutons 1/2 chacun
        Text("weight(1f) + weight(1f) = 50% / 50%")
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {},
                modifier = Modifier.weight(1f)  // 50%
            ) { Text("1/2") }
            
            Button(
                onClick = {},
                modifier = Modifier.weight(1f)  // 50%
            ) { Text("1/2") }
        }
        
        // Équivalent : 2/3 + 1/3
        Text("weight(2f) + weight(1f) = 66% / 33%")
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {},
                modifier = Modifier.weight(2f)  // 2/3
            ) { Text("2/3") }
            
            Button(
                onClick = {},
                modifier = Modifier.weight(1f)  // 1/3
            ) { Text("1/3") }
        }
        
        // Trois rangs égaux avec weight
        Text("Trois rangs égaux :")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.Red),
                contentAlignment = Alignment.Center
            ) { Text("1/3", color = Color.White) }
            
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.Green),
                contentAlignment = Alignment.Center
            ) { Text("1/3", color = Color.White) }
            
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.Blue),
                contentAlignment = Alignment.Center
            ) { Text("1/3", color = Color.White) }
        }
        
        // Weight vertical dans une Column
        Text("Weight vertical (dans Column) :")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.Cyan),
                contentAlignment = Alignment.Center
            ) { Text("1/3") }
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .background(Color.Magenta),
                contentAlignment = Alignment.Center
            ) { Text("2/3", color = Color.White) }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun WeightDemoPreview() {
    WeightDemo()
}

// =============================================================================
// SCROLL - Équivalent de ScrollView
// =============================================================================
// Utiliser verticalScroll() ou horizontalScroll() avec un ScrollState

@Composable
fun ScrollDemo() {
    // État du scroll (nécessaire pour le modifier)
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)  // <-- Rend la Column scrollable
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "Column + verticalScroll() = ScrollView",
            fontSize = 20.sp
        )
        
        Text("Faites défiler vers le bas ↓")
        
        // Génère beaucoup d'éléments pour permettre le scroll
        repeat(30) { index ->
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Élément ${index + 1}",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp
                )
            }
        }
        
        Text("🎉 Fin de la liste !", fontSize = 24.sp)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun ScrollDemoPreview() {
    ScrollDemo()
}
