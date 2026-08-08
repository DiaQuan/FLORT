package com.example.flort

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flort.data.AppDatabase
import com.example.flort.data.PartnerViewModel
import com.example.flort.data.PartnerViewModelFactory
import com.example.flort.ui.EkleEkrani
import com.example.flort.ui.ListeEkrani

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(applicationContext)
        val factory = PartnerViewModelFactory(db.partnerDao())

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier) {
                    val viewModel: PartnerViewModel = viewModel(factory = factory)
                    FlortApp(viewModel)
                }
            }
        }
    }
}

@Composable
fun FlortApp(viewModel: PartnerViewModel) {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "liste") {
        composable("liste") {
            ListeEkrani(
                viewModel = viewModel,
                onEkleTiklandi = { navController.navigate("ekle") }
            )
        }
        composable("ekle") {
            EkleEkrani(
                viewModel = viewModel,
                onKaydedildi = { navController.popBackStack() }
            )
        }
    }
}
