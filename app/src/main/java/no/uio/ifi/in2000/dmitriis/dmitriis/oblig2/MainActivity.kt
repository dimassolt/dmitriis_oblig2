package no.uio.ifi.in2000.dmitriis.dmitriis.oblig2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.ui.home.HomeScreen
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.ui.party.PartyScreen
import no.uio.ifi.in2000.dmitriis.dmitriis.oblig2.ui.theme.Dmitriis_oblig2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Dmitriis_oblig2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
    @Composable
    fun MyApp() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "HomeScreen") {
            composable("HomeScreen") {
                HomeScreen(onNavigateToPartyScreen = { navController.navigate("PartyScreen") }) }
            composable("PartyScreen") {
                PartyScreen(onNavigateToHomeScreen = { navController.navigate("HomeScreen") }) }
        }
    }
}

