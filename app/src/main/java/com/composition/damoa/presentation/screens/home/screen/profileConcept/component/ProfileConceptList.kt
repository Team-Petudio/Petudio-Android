package com.composition.damoa.presentation.screens.home.screen.profileConcept.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.composition.damoa.R
import com.composition.damoa.data.model.PetType
import com.composition.damoa.data.model.ProfileConcept
import com.composition.damoa.presentation.ui.theme.Purple60

@Composable
fun ProfileConceptList(
    profileConcepts: List<ProfileConcept>,
    modifier: Modifier = Modifier,
    onProfileConceptClick: (conceptId: Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .background(color = Color.White)
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            items = profileConcepts,
            key = { profileConcept -> profileConcept.conceptName },
        ) { profileConcept ->
            ProfileConceptItem(
                profileConcept = profileConcept,
                onCreationButtonClick = { onProfileConceptClick(profileConcept.id) },
            )
        }
    }
}

@Composable
private fun ProfileConceptItem(
    modifier: Modifier = Modifier,
    profileConcept: ProfileConcept,
    onCreationButtonClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 0.dp,
        modifier = modifier.aspectRatio(5 / 4F),
    ) {
        ProfileConceptImage(profileConcept)
        ProfileConceptBody(profileConcept, onCreationButtonClick)
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun ProfileConceptImage(profileConcept: ProfileConcept) {
    GlideImage(
        model = profileConcept.conceptImageUrl,
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        transition = CrossFade,
        contentScale = ContentScale.Crop,
        failure = placeholder(R.drawable.img_placeholder),
        loading = placeholder(R.drawable.img_placeholder),
    )
}

@Composable
private fun ProfileConceptBody(
    profileConcept: ProfileConcept,
    onCreationButtonClick: () -> Unit,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        val animalType = when (profileConcept.petType) {
            PetType.DOG -> stringResource(id = R.string.ai_profile_dog_only)
            PetType.CAT -> stringResource(id = R.string.ai_profile_cat_only)
        }

        if (profileConcept.isNewConcept) NewConceptBadge(modifier = Modifier.weight(1F))

        ConceptName(profileConcept)
        ConceptDescription(profileConcept, animalType)
        ProfileCreationButton(onCreationButtonClick)
    }
}

@Composable
private fun ConceptName(profileConcept: ProfileConcept) {
    Text(
        text = profileConcept.conceptName,
        color = Color.White,
        fontSize = 24.sp,
        fontWeight = FontWeight.ExtraBold,
    )
}

@Composable
private fun ConceptDescription(
    profileConcept: ProfileConcept,
    animalType: String,
) {
    Text(
        text = "${profileConcept.conceptDescription} | $animalType",
        color = Color.White,
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.padding(bottom = 6.dp),
    )
}

@Composable
private fun ProfileCreationButton(onCreationButtonClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp, max = 60.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Purple60,
            contentColor = Color.White,
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = onCreationButtonClick,
    ) {
        Text(
            text = stringResource(id = R.string.ai_profile_creation),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
private fun NewConceptBadge(modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.Start, modifier = modifier) {
        Surface(
            modifier = Modifier.wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
            color = Purple60,
        ) {
            Text(
                text = stringResource(id = R.string.new_concept),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            )
        }
    }
}