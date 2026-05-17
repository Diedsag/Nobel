package com.example.nobel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nobel.data.repository.NobelRepositoryImpl
import com.example.nobel.domain.usecase.GetPrizesUseCase
import com.example.nobel.presentation.navigation.AppNavGraph
import com.example.nobel.presentation.viewmodel.NobelViewModel
import com.example.nobel.ui.theme.NobelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var navController: NavHostController
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = NobelRepositoryImpl(
        )

        val getPrizesUseCase = GetPrizesUseCase(repository)

        val viewModel = NobelViewModel(
            getPrizesUseCase
        )

        setContent {
            navController = rememberNavController()
            NobelTheme {
                Scaffold(modifier = Modifier
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        AppNavGraph(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}