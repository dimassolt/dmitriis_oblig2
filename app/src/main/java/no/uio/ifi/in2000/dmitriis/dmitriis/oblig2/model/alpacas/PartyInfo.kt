package no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.model.alpacas

var currentId = 0
data class PartyInfo(val id: Int,
                     val name: String,
                     val leader: String,
                     val img: String,
                     val color: String,
                     val description: String) {
}