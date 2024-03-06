
package no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.ui.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.alpacas.PartyInfo
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.votes.District
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.votes.DistrictVotes

class VoteList{
@Composable
    fun ViseFremStemmer(partiesViewModel: HomeViewModel = viewModel(), district: District) {
        val partiesUiState: PartiesUiState by partiesViewModel.partiesUiState.collectAsState()
        val votes = remember{when(district){District.District1 -> partiesViewModel.velgDistrictVotes.votes1
            District.District2 -> partiesViewModel.velgDistrictVotes.votes2
            District.District3 -> partiesViewModel.velgDistrictVotes.votes3}}
        Card(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Text(
                    text = ("Parti"),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Antall stemmer",
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Right,
                )
            }
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(modifier = Modifier.padding(10.dp)) {
                    items(partiesUiState.parties) { party ->
                        Votes(
                            party = party,
                            votes = votes
                        )
                    }
                }
            }
        }
    }
    @Composable
    fun Votes(party: PartyInfo, votes: List<DistrictVotes>) {
        val vote = votes.find{partyVote -> partyVote.alpacaPartyId == party.id.toString()}
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = party.name,
                textAlign = TextAlign.Left,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = vote?.numberOfVotesForParty.toString(),
                textAlign = TextAlign.Right,
                modifier = Modifier.weight(1f)
            )
        }
    }
}




