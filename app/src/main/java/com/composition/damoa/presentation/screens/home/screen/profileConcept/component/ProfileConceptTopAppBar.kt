package com.composition.damoa.presentation.screens.home.screen.profileConcept.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.GradientPetudioTitle


@Composable
fun ProfileConceptTopAppBar() {
    TopAppBar(
        title = { GradientPetudioTitle(modifier = Modifier.offset(x = (-12).dp)) },
        navigationIcon = { ProfileConceptAppBarIcon() },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        modifier = Modifier.background(Color.White),
    )
}

@Composable
private fun ProfileConceptAppBarIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = null,
        tint = Color.Unspecified,
    )
}