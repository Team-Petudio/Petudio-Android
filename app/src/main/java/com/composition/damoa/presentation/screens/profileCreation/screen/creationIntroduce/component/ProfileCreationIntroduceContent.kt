package com.composition.damoa.presentation.screens.profileCreation.screen.creationIntroduce.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composition.damoa.data.model.ProfileConceptDetail


@Composable
fun ProfileCreationIntroduceContent(
    modifier: Modifier = Modifier,
    profileConceptDetail: ProfileConceptDetail,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 100.dp),
    ) {
        item { ProfileCreationIntroduceTitle(modifier = modifier.padding(top = 12.dp, bottom = 32.dp)) }
        item { ProfileCreationGoodResultIntroduce() }
        item { ProfileResultExampleList(photoUrls = profileConceptDetail.successImageUrls) }
        item { ProfileCreationBadResultIntroduce(modifier = Modifier.padding(top = 32.dp)) }
        item { ProfileResultExampleList(photoUrls = profileConceptDetail.failImageUrls) }
    }
}