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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.R
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.alpacas.PartyInfo
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.alpacas.currentId
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.votes.District
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.votes.districtValgt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigateToPartyScreen: () -> Unit, partiesViewModel: HomeViewModel = viewModel()) {

    val partiesUiState: PartiesUiState by partiesViewModel.partiesUiState.collectAsState()

    val options = listOf(
        District.District1,
        District.District2,
        District.District3,
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        /*horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.SpaceBetween*/
    ) {


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
                item{
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            modifier = Modifier.menuAnchor(),
                            value = selectedOptionText.name,
                            onValueChange = {},

                            label = { Text("Velg District") },
                            singleLine = true,
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
                            onDismissRequest = { expanded = false },
                        ) {
                            options.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption.name) },
                                    onClick = {
                                        selectedOptionText = selectionOption
                                        //districtValgt = selectedOptionText
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    viseFremStemmer(HomeViewModel(),selectedOptionText)
                }
            }
    }
    }

}

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuComposable(options: List<District>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    Box(
        modifier = Modifier
            .background(Color(0xFF6200EE)) // gir boksen en lilla bakgrunn
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                TextField(
                    modifier = Modifier.menuAnchor(),
                    value = selectedOptionText.name,
                    onValueChange = {},
                    label = { Text("Velg District") },
                    singleLine = true,
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = null
                        )
                    },
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption.name) },
                            onClick = {
                                selectedOptionText = selectionOption
                                districtValgt = selectedOptionText
                                expanded = false
                            }
                        )
                    }
                }
            }

            viseFremStemmer()
        }
    }
}
*/

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
