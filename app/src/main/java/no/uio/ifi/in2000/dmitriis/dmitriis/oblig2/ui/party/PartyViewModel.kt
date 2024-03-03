package no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.ui.party

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.data.alpacas.AlpacaPartiesRepository
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.data.alpacas.PartiesRepositoryImpl
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.alpacas.PartyInfo
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.alpacas.currentId


data class PartyUiState(val party: PartyInfo?)

class PartyViewModel(): ViewModel(){
    var partyUiState by mutableStateOf(PartyUiState(party = null))
    private val alpacaPartiesRepository: AlpacaPartiesRepository = PartiesRepositoryImpl()

    init{
        loadParty()
    }

    private fun loadParty(){
        viewModelScope.launch (Dispatchers.IO){

            val party = alpacaPartiesRepository.hentParty(currentId)
            partyUiState = partyUiState.copy(party = party )

        }
    }
}