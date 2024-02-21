package com.composition.damoa.presentation.screens.store.component

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composition.damoa.data.model.PurchaseItem
import com.composition.damoa.presentation.screens.store.StoreViewModel
import com.composition.damoa.presentation.ui.theme.PetudioTheme


@Composable
fun StoreScreen(
    viewModel: StoreViewModel,
    onPurchaseClick: (PurchaseItem) -> Unit,
) {
    PetudioTheme {
        val activity = LocalContext.current as ComponentActivity
        val ticketPurchaseUiState by viewModel.ticketPurchaseUiState.collectAsStateWithLifecycle()

        Scaffold(
            topBar = { StoreTopBar { activity.finish() } },
        ) { padding ->
            StoreContent(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(top = padding.calculateTopPadding()),
                ticketPurchaseUiState = ticketPurchaseUiState,
                onPurchaseClick = onPurchaseClick,
            )
        }
    }
}