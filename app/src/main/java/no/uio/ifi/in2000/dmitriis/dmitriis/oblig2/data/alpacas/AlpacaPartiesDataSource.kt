package no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.data.alpacas

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.alpacas.Parties
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.alpacas.PartyInfo


class AlpacaPartiesDataSource {
    private val path: String =
        "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppgaver/alpacaparties.json"
    private val client =
        HttpClient {
            install(ContentNegotiation) {
                gson()
            }
        }
    suspend fun fetchParties(): List<PartyInfo> {
        val response = client.get(path)
        val parties: Parties = response.body()

        return parties.parties

    }
}


