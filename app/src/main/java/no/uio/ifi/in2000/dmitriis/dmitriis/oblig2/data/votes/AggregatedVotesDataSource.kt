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
import io.ktor.serialization.gson.gson
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.votes.District
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.votes.DistrictVotes
import org.json.JSONObject

class AggregatedVotesDataSource{

    var path: String = "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppgaver/district3.json"
    private val client = HttpClient(){
        install(ContentNegotiation){
            gson()
        }
    }
    suspend fun fetchDistrict3(): List<DistrictVotes>{
        var jsonString: String = client.get(path).body()
        val jsonObject = JSONObject(jsonString)
        val partiesArray = jsonObject.getJSONArray("parties")

        val votes: MutableList<DistrictVotes> = mutableListOf()
        for (i in 0 until partiesArray.length()) {
            val jsonObject = partiesArray.getJSONObject(i)
            val partiId = jsonObject.getString("partyId")
            val voteCount = jsonObject.getString("votes").toInt()
            val districtVotes = DistrictVotes(District.District3, partiId, voteCount)
            votes.add(districtVotes)
        }
        return votes.sortedBy { it.alpacaPartyId.toInt() }
    }
}
/*
@Preview
@Composable
fun test2() {
    val aggregatedVotesDataSource = AggregatedVotesDataSource()
    var results by remember { mutableStateOf<List<DistrictVotes>?>(null) }

    LaunchedEffect(key1 = aggregatedVotesDataSource) {
        results = aggregatedVotesDataSource.fetchDistrict3()
    }

    Column {
        Text(text = "District 3 votes: ${results?.size}")
        Text(text = "District 3 votes: ${results}")
    }
}*/
