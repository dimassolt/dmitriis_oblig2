package no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.alpacas.PartyInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.data.alpacas.AlpacaPartiesRepository
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.data.alpacas.PartiesRepositoryImpl
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.votes.District
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.votes.DistrictVotes

data class PartiesUiState(val parties: List<PartyInfo> = emptyList(),

)
data class VotesUiState( var votes1: List<DistrictVotes>,
                         var votes2: List<DistrictVotes>,
                         var votes3: List<DistrictVotes>
)

class HomeViewModel : ViewModel() {
    private val partiesRepository: AlpacaPartiesRepository = PartiesRepositoryImpl()
    private val _partiesUiState = MutableStateFlow(PartiesUiState())
    val partiesUiState: StateFlow<PartiesUiState> = _partiesUiState.asStateFlow()

    init {
        loadParties()
    }

    var velgDistrictVotes by mutableStateOf(VotesUiState(votes1 = mutableListOf<DistrictVotes>(), votes2 = mutableListOf<DistrictVotes>(), votes3 = mutableListOf<DistrictVotes>()))


    private fun loadParties() {
        viewModelScope.launch(Dispatchers.IO) {
            val parties = partiesRepository.hentParties()
            _partiesUiState.update { it.copy(parties = parties) }
        }
    }

    fun loadVotesForDistrict() {
        viewModelScope.launch(Dispatchers.IO) {

                val votes1 = partiesRepository.hentVotesInDistrict(District.District1)
                val votes2 = partiesRepository.hentVotesInDistrict(District.District2)
                val votes3 = partiesRepository.hentVotesInDistrict(District.District3)
            velgDistrictVotes = VotesUiState(votes1 = votes1,votes2 = votes2,votes3 = votes3)
            }
        }
    }



































