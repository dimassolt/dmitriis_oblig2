package no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.ui.home


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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.R
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.alpacas.PartyInfo
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.alpacas.currentId
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.votes.District


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigateToPartyScreen: () -> Unit, partiesViewModel: HomeViewModel = viewModel()) {

    val partiesUiState: PartiesUiState by partiesViewModel.partiesUiState.collectAsState()
    val voteList = VoteList()


    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        DropDown(voteList, partiesViewModel)


        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(
                        text = stringResource(R.string.alpacaparties_header),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 30.sp, fontWeight = FontWeight.Bold
                        )
                    )
                }

                )
            }
        )
        { innerPadding ->

            LazyColumn(modifier = Modifier.padding(innerPadding)) {

                items(partiesUiState.parties) { party ->

                    PartyCard(
                        party = party,
                        onClick = { onNavigateToPartyScreen(); currentId = party.id }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(voteList: VoteList, homeViewModel: HomeViewModel){
    var expanded by remember { mutableStateOf(false) }
    val districter = listOf(
        District.District1,
        District.District2,
        District.District3)
    var klikket1 by remember { mutableStateOf(false) }
    var klikket2 by remember { mutableStateOf(false) }
    var klikket3 by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }
    var districtNummer by remember {mutableStateOf(District.District1)}



    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween) {

        Text(
            text = "Stemmer",
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }) {
            TextField(
                value = districtNummer.name,
                modifier = Modifier
                    .menuAnchor()
                    .padding(bottom = 10.dp),
                onValueChange = {},
                label = { Text("Velg district") },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }) {
                districter.map { element ->
                    DropdownMenuItem(
                        text =  { Text(element.name) },
                        onClick = {
                            districtNummer = element
                            selectedOptionText = element.name
                            expanded = false
                            when (selectedOptionText) {
                                "District1" -> {
                                    klikket3 = false
                                    klikket2 = false
                                    klikket1 = true
                                }
                                "District2" -> {
                                    klikket1 = false
                                    klikket3 = false
                                    klikket2 = true
                                }
                                "District3" -> {
                                    klikket1 = false
                                    klikket2 = false
                                    klikket3 = true
                                }
                            }

                        }
                    )
                }
            }
        }

        if (klikket1) {
            homeViewModel.loadVotesForDistrict()
            voteList.ViseFremStemmer(homeViewModel, District.District1)

        } else if (klikket2){
            homeViewModel.loadVotesForDistrict()
            voteList.ViseFremStemmer(homeViewModel, District.District2)

        } else if (klikket3){
            homeViewModel.loadVotesForDistrict()
            voteList.ViseFremStemmer(homeViewModel, District.District3)

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartyCard(
    party: PartyInfo,
    colorString: String = party.color,
    color: Color = Color(android.graphics.Color.parseColor(colorString)),
    onClick: () -> Unit

) {
    val cardHeight = 400.dp
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = color),

        onClick = {onClick() }



    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = party.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)

            )

            AsyncImage(
                model = party.img,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )

            Text(
                text = "Leader ${party.leader}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
        }
    }
}