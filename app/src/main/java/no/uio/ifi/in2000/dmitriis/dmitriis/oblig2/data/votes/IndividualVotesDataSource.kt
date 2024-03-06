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
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.gson.gson
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.votes.District
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.votes.DistrictVotes
import org.json.JSONArray

class IndividualVotesDataSource () {
    var path1: String =
        "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppgaver/district1.json"
    var path2: String =
        "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppgaver/district2.json"
    private val client =
        HttpClient() { //vi oppreter Http client for tilkobling til. The HTTP client used to make requests
            install(ContentNegotiation) {//With the ContentNegotiation plugin installed, you can deserialize JSON data into a data class when receiving responses
                gson()
            }
        }

    //det må være 3 separate district som man kan velge mellom
    suspend fun fetchDistrictVotes1(): List<DistrictVotes> {
        return singleFetch1()
    }

    suspend fun fetchDistrictVotes2(): List<DistrictVotes> {
        return singleFetch2()
    }

    suspend fun singleFetch1(): List<DistrictVotes> {
        // map med alle district vote objekter
        // der key er party nummer og value er DistrictVotes objekt
        val jsonString: String = client.get(path1).bodyAsText()
        val jsonArray = JSONArray(jsonString)

        // Teller antall stemmer for hver party id ved å bruke en map-funksjon og en midlertidig samling
        val counts = (0 until jsonArray.length()).map { i ->
            val jsonObject = jsonArray.getJSONObject(i)
            jsonObject.getString("id")
        }.groupingBy { it }.eachCount()

        // Går gjennom map og lager DistrictVote-objekt for hver party id ved hjelp av map-funksjonen
        val districtVotesList = counts.map { (id, count) ->
            DistrictVotes(District.District1, id, count)
        }

        return districtVotesList.sortedBy { it.alpacaPartyId.toInt() }
    }

    suspend fun singleFetch2(): List<DistrictVotes> {
        // map med alle district vote objekter
        // der key er party nummer og value er DistrictVotes objekt
        val jsonString: String = client.get(path2).bodyAsText()
        val jsonArray = JSONArray(jsonString)

        // Teller antall stemmer for hver party id ved å bruke en map-funksjon og en midlertidig samling
        val counts = (0 until jsonArray.length()).map { i ->
            val jsonObject = jsonArray.getJSONObject(i)
            jsonObject.getString("id")
        }.groupingBy { it }.eachCount()

        // Går gjennom map og lager DistrictVote-objekt for hver party id ved hjelp av map-funksjonen
        val districtVotesList = counts.map { (id, count) ->
            DistrictVotes(District.District2, id, count)
        }

        return districtVotesList.sortedBy { it.alpacaPartyId.toInt() }
    }
}

/*
@Preview
@Composable
fun test1() {
    val individualVotesDataSource = IndividualVotesDataSource()
    var results1 by remember { mutableStateOf<List<DistrictVotes>?>(null) }
    var results2 by remember { mutableStateOf<List<DistrictVotes>?>(null) }

    LaunchedEffect(key1 = individualVotesDataSource) {
        results1 = individualVotesDataSource.fetchDistrictVotes1()
        results2 = individualVotesDataSource.fetchDistrictVotes2()
    }

    Column {
        Text(text = "District 1 votes: ${results1?.size}")
        Text(text = "District 1 votes: ${results1}")
        Text(text = "District 2 votes: ${results2?.size}")
        Text(text = "District 2 votes: ${results2}")
    }
}
*/

