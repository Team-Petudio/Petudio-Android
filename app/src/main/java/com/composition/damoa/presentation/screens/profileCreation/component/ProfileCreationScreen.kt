package com.composition.damoa.presentation.screens.profileCreation.component

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.composition.damoa.presentation.screens.profileCreation.ProfileCreationViewModel
import com.composition.damoa.presentation.ui.theme.PetudioTheme


@Composable
fun ProfileCreationScreen(
    viewModel: ProfileCreationViewModel,
    navController: NavHostController = rememberNavController(),
    onPhotoUploadClick: () -> Unit,
) {
    PetudioTheme {
        val activity = LocalContext.current as ComponentActivity
        val conceptDetailUiState by viewModel.conceptDetailUiState.collectAsStateWithLifecycle()
        val petPhotoSelectionUiState by viewModel.petPhotoSelectionUiState.collectAsStateWithLifecycle()
        val petUiState by viewModel.petInfoUiState.collectAsStateWithLifecycle()
        val selectedImageUiState by viewModel.selectedImageUiState.collectAsStateWithLifecycle()
        val ticketUiState by viewModel.paymentUiState.collectAsStateWithLifecycle()

        Scaffold(
            topBar = {
                ProfileCreationTopAppBar(
                    onNavigationClick = { if (!navController.popBackStack()) activity.finish() },
                )
            },
        ) { padding ->
            ProfileCreationNavHost(
                modifier = Modifier.padding(top = padding.calculateTopPadding()),
                navController = navController,
                conceptDetailUiState = conceptDetailUiState,
                petPhotoSelectionUiState = petPhotoSelectionUiState,
                petInfoUiState = petUiState,
                onPetUploadClick = viewModel::uploadPetImages,
                onPhotoUploadClick = onPhotoUploadClick,
                photoUploadResultUiState = selectedImageUiState,
                paymentUiState = ticketUiState,
            )
        }
    }
}
