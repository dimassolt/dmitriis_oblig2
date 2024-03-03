package no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.data.alpacas

import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.data.votes.VotesRepository
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.data.votes.VotesRepositorySamlet
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.alpacas.PartyInfo
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.votes.District
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.votes.DistrictVotes

interface AlpacaPartiesRepository {
    suspend fun hentParties(): List<PartyInfo>
    suspend fun hentParty(id: Int): PartyInfo?
    suspend fun hentPartyAndDistrict(id: Int,district: District): DistrictVotes?
    suspend fun hentVotesInDistrict(district: District): List<DistrictVotes>

}

class PartiesRepositoryImpl(
    private val partiesDataSource: AlpacaPartiesDataSource = AlpacaPartiesDataSource(),
) : AlpacaPartiesRepository {
    override suspend fun hentParties(): List<PartyInfo> {
        return partiesDataSource.fetchParties()
    }
    override suspend fun hentParty(id: Int): PartyInfo? {
        val parties = hentParties()
        val trengteParty = parties.find { it.id == id }
        return trengteParty
    }
    override suspend fun hentPartyAndDistrict(id: Int,district: District): DistrictVotes?{
        val votesRepository: VotesRepository = VotesRepositorySamlet()
        val trengteParty = hentParty(id)
        val trengtePartyVotes = votesRepository.hentPartyVotesInDistrict(id,district)
        return trengtePartyVotes
    }
    override suspend fun hentVotesInDistrict(district: District): List<DistrictVotes> {
        val votesRepository: VotesRepository = VotesRepositorySamlet()
        val votes1 = votesRepository.hentDistrict1VotesListe()
        val votes2 = votesRepository.hentDistrict2VotesListe()
        val votes3 = votesRepository.hentDistrict3VotesListe()
        return when(district){District.District1 -> votes1
            District.District2 -> votes2
            District.District3 -> votes3
        }

    }
}

