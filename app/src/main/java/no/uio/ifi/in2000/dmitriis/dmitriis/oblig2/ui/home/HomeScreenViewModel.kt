package no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.ui.home

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
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.votes.districtValgt


data class PartiesUiState(val parties: List<PartyInfo> = emptyList(),
                          val votes1: List<DistrictVotes> = emptyList(),
                          val votes2: List<DistrictVotes> = emptyList(),
                          val votes3: List<DistrictVotes> = emptyList()
)

//data class PartyWithVotes(val party: PartyInfo, val votes: Int)


class HomeViewModel : ViewModel() {
    private val partiesRepository: AlpacaPartiesRepository = PartiesRepositoryImpl()
    private val _partiesUiState = MutableStateFlow(PartiesUiState())

    val partiesUiState: StateFlow<PartiesUiState> = _partiesUiState.asStateFlow()

    init {
        loadParties()
    }


    private fun loadParties() {

        viewModelScope.launch(Dispatchers.IO) {
            _partiesUiState.update { currentPartiesUiState ->

                val parties = partiesRepository.hentParties()
                val votes1 = partiesRepository.hentVotesInDistrict(District.District1)
                val votes2 = partiesRepository.hentVotesInDistrict(District.District2)
                val votes3 = partiesRepository.hentVotesInDistrict(District.District3)

                currentPartiesUiState.copy(parties = parties,votes1 = votes1,votes2 = votes2,votes3 = votes3)

            }
        }
    }
}




































