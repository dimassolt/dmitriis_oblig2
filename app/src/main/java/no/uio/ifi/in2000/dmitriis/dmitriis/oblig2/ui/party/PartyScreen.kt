package no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.ui.party

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.alpacas.PartyInfo
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartyScreen(partyViewModel: PartyViewModel = viewModel(), onNavigateToHomeScreen: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Party") }, navigationIcon = {
            IconButton(onClick = {onNavigateToHomeScreen()}) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Tilbake"
                )
            }
        },
        )
        }
    )
    { innerPadding ->
        LazyColumn(contentPadding = innerPadding,) {
            items(partyViewModel.partyUiState.party?.let { listOf(it) } ?: emptyList()) { party ->
                PartyCard(party = party)
                Text(text = party.description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp))
            }
        }
    }
}
@Composable
fun PartyCard(
    party: PartyInfo,
    colorString: String = party.color,
    color: androidx.compose.ui.graphics.Color = Color(android.graphics.Color.parseColor(colorString)),
) {
    val cardHeight = 300.dp
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight) // Fast høyde for alle Card
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = color),

        ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(), // Fyll kortets størrelse
            verticalArrangement = Arrangement.Center, // Senter vertikalt
            horizontalAlignment = Alignment.CenterHorizontally // Senter horisontalt
        ) {

            Text(
                text = party.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(6.dp)

            )

            AsyncImage(
                model = party.img,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )


            Text(
                text = "Leader ${party.leader}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(6.dp)
            )
        }
    }
}
