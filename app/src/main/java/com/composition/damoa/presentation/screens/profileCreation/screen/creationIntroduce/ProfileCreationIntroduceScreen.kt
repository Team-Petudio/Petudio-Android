package com.composition.damoa.presentation.screens.profileCreation.screen.creationIntroduce

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composition.damoa.data.model.ProfileConceptDetail
import com.composition.damoa.presentation.common.components.KeepGoingButton
import com.composition.damoa.presentation.screens.profileCreation.screen.creationIntroduce.component.ProfileCreationIntroduceContent


@Composable
fun ProfileCreationIntroduceScreen(
    modifier: Modifier = Modifier,
    onKeepGoingClick: () -> Unit,
    profileConceptDetail: ProfileConceptDetail,
) {
    Surface(
        color = Color.White,
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        ProfileCreationIntroduceContent(profileConceptDetail = profileConceptDetail)
        KeepGoingButton(onClick = onKeepGoingClick)
    }
}
