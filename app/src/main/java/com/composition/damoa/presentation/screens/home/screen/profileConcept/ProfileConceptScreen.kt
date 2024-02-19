package com.composition.damoa.presentation.screens.home.screen.profileConcept

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composition.damoa.presentation.screens.home.screen.profileConcept.component.ProfileConceptList
import com.composition.damoa.presentation.screens.home.screen.profileConcept.component.ProfileConceptTopAppBar
import com.composition.damoa.presentation.screens.home.screen.profileConcept.state.ProfileConceptUiState


@Composable
fun ProfileConceptScreen(
    modifier: Modifier = Modifier,
    profileConceptUiState: ProfileConceptUiState,
    onProfileConceptClick: (conceptId: Long) -> Unit,
) {
    Column {
        ProfileConceptTopAppBar()
        ProfileConceptList(
            modifier = modifier.fillMaxSize(),
            profileConcepts = profileConceptUiState.profileConcepts,
            onProfileConceptClick = onProfileConceptClick,
        )
    }
}
