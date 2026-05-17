package com.example.nobel.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.nobel.presentation.ui.screen.NobelDetailScreen
import com.example.nobel.presentation.ui.screen.NobelListScreen
import com.example.nobel.presentation.viewmodel.NobelViewModel
import com.example.nobel.presentation.viewmodel.PrizesListUiState

@Composable
fun AppNavGraph(navController: NavHostController, viewModel: NobelViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            NobelListScreen(
                state,
                { id -> Log.d("TAG", id.toString())
                    Log.d("TAG", (state as PrizesListUiState.Success).prizes.size.toString())
                    navController.navigate("item/$id") },
                viewModel::filter
            )
        }
        composable(
            "item/{prizeId}",
            arguments = listOf(navArgument("prizeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val prizeId = backStackEntry.arguments?.getInt("prizeId") ?: return@composable
            val prizes = (state as PrizesListUiState.Success).prizes
            val prize = prizes.find{ prizes.indexOf(it) == prizeId }
            prize?.let {
                NobelDetailScreen(
                    prize = it
                )
            }
        }
    }
}