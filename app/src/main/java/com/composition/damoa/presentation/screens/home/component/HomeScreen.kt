package com.composition.damoa.presentation.screens.home.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.composition.damoa.presentation.screens.home.HomeViewModel
import com.composition.damoa.presentation.ui.theme.PetudioTheme


@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
) {
    PetudioTheme {
        val homeNavController = rememberNavController()
        val profileUiState by viewModel.profileConceptUiState.collectAsStateWithLifecycle()
        val userUiState by viewModel.userUiState.collectAsStateWithLifecycle()
        val albumUiState by viewModel.albumUiState.collectAsStateWithLifecycle()
        val petFeedUiState by viewModel.petFeedUiState.collectAsStateWithLifecycle()

        Scaffold(
            bottomBar = { HomeBottomNavigationBar(navController = homeNavController) },
        ) { padding ->
            HomeNavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = padding.calculateTopPadding(), bottom = padding.calculateBottomPadding()),
                navController = homeNavController,
                profileConceptUiState = profileUiState,
                albumUiState = albumUiState,
                petFeedUiState = petFeedUiState,
                userUiState = userUiState,
            )
        }
    }
}
