package no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.data.votes

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.votes.District
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.votes.DistrictVotes


interface VotesRepository {
    suspend fun hentDistrict3VotesListe(): List<DistrictVotes>
    suspend fun hentDistrict1VotesListe(): List<DistrictVotes>
    suspend fun hentDistrict2VotesListe(): List<DistrictVotes>
    suspend fun hentPartyVotesInDistrict(id:Int, district: District): DistrictVotes?
    suspend fun hentVotesInDistrict(district: District): List<DistrictVotes>

}
class VotesRepositorySamlet(
    private val district3DataSource: AggregatedVotesDataSource = AggregatedVotesDataSource(),
    val district1DataSource: IndividualVotesDataSource = IndividualVotesDataSource(),
    val district2DataSource: IndividualVotesDataSource = IndividualVotesDataSource()
) : VotesRepository {
    override suspend fun hentDistrict3VotesListe(): List<DistrictVotes> {
        return district3DataSource.fetchDistrict3()
    }
    override suspend fun hentDistrict1VotesListe(): List<DistrictVotes> {
        return district1DataSource.fetchDistrictVotes1()
    }
    override suspend fun hentDistrict2VotesListe(): List<DistrictVotes> {
        return district2DataSource.fetchDistrictVotes2()
    }
    override suspend fun hentPartyVotesInDistrict(id: Int, district: District): DistrictVotes? {

        val votes1 = hentDistrict1VotesListe()
        val votes2 = hentDistrict2VotesListe()
        val votes3 = hentDistrict3VotesListe()


        val trengteParty: DistrictVotes? =
            when(district){District.District1 ->  votes1.find{it.alpacaPartyId.toInt() == id }
            District.District2 -> votes2.find{it.alpacaPartyId.toInt() == id}
            District.District3 -> votes3.find{it.alpacaPartyId.toInt() == id}
        }
        return trengteParty
    }
    override suspend fun hentVotesInDistrict(district: District): List<DistrictVotes> {

        val votes1 = hentDistrict1VotesListe()
        val votes2 = hentDistrict2VotesListe()
        val votes3 = hentDistrict3VotesListe()


        val trengteDistrict: List<DistrictVotes> =
            when(district){District.District1 -> votes1
                District.District2 -> votes2
                District.District3 -> votes3
            }
        return trengteDistrict
    }
}


@Preview
@Composable
fun test3() {
    val individualVotesDataSource = IndividualVotesDataSource()
    var results1 by remember { mutableStateOf<List<DistrictVotes>?>(null) }
    var results2 by remember { mutableStateOf<List<DistrictVotes>?>(null) }

    LaunchedEffect(key1 = individualVotesDataSource) {
        results1 = individualVotesDataSource.fetchDistrictVotes1()
        results2 = individualVotesDataSource.fetchDistrictVotes2()

    }
    val aggregatedVotesDataSource = AggregatedVotesDataSource()
    var results3 by remember { mutableStateOf<List<DistrictVotes>?>(null) }

    LaunchedEffect(key1 = aggregatedVotesDataSource) {
        results3 = aggregatedVotesDataSource.fetchDistrict3()
    }

    var repo1 = VotesRepositorySamlet()
    var result1 by remember { mutableStateOf<DistrictVotes?>(null) }
    var repo2 = VotesRepositorySamlet()
    var result2 by remember { mutableStateOf<DistrictVotes?>(null) }
    var repo3 = VotesRepositorySamlet()
    var result3 by remember { mutableStateOf<DistrictVotes?>(null) }


    LaunchedEffect(key1 = repo1) {
        result1 = repo1.hentPartyVotesInDistrict(1,District.District1)
        result2 = repo2.hentPartyVotesInDistrict(2,District.District2)
        result3 = repo3.hentPartyVotesInDistrict(3,District.District3)
    }

    Column {
        Text(text = "District 1 votes: ${results1?.size}")
        Text(text = "District 1 votes: ${results1}")
        Text(text = "District 2 votes: ${results2?.size}")
        Text(text = "District 2 votes: ${results2}")
        Text(text = "District 3 votes: ${results3?.size}")
        Text(text = "District 3 votes: ${results3}")
        Text(text = "Party ID 1, District 1 vote: ${result1}")
        Text(text = "Party ID 2, District 2 vote: ${result2}")
        Text(text = "Party ID 3, District 3 vote: ${result3}")


    }
}



