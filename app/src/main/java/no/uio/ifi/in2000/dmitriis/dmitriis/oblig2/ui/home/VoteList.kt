
package no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.ui.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.alpacas.PartyInfo
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.votes.District
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.votes.DistrictVotes
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.votes.districtValgt

//@Preview
@Composable
fun viseFremStemmer(partiesViewModel: HomeViewModel = viewModel(),district: District) {

//fun viseFremStemmer(partiesViewModel: HomeViewModel = viewModel(), district: District) {
    val partiesUiState: PartiesUiState by partiesViewModel.partiesUiState.collectAsState()
    val votesUiState: PartiesUiState by partiesViewModel.partiesUiState.collectAsState()

    Row(
        modifier = Modifier
            .background(Color(0xFF6200EE))
            .padding(16.dp)
            .fillMaxSize(), // Fyll kortets størrelsew
    ) {
        Text(
            text = "Parties",
            style = MaterialTheme.typography.titleMedium,
            /*textAlign = TextAlign.Left,*/
            modifier = Modifier
                .padding(16.dp)
                .weight(1f)

        )
        Text(
            text = "Antall stemmer",
            style = MaterialTheme.typography.titleMedium,
            /*textAlign = TextAlign.Right,*/
            modifier = Modifier
                .padding(16.dp)
                .weight(1f)
        )
    }
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            partiesUiState.parties.forEach { item ->
                Text(text = item.name, fontSize = 18.sp)
            }
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .offset(y = (-190).dp,x = 220.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        when(district){District.District1 ->votesUiState.votes1.forEach { item ->
            Text(text = item.numberOfVotesForParty.toString(), fontSize = 18.sp)
        }
            District.District2 ->votesUiState.votes2.forEach { item ->
                Text(text = item.numberOfVotesForParty.toString(), fontSize = 18.sp)
            }
            District.District3 ->votesUiState.votes3.forEach { item ->
                Text(text = item.numberOfVotesForParty.toString(), fontSize = 18.sp)
            }
        }

    }

}

