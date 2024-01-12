package com.composition.damoa.presentation.screens.home

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.composition.damoa.R
import com.composition.damoa.presentation.ui.theme.Purple60

@Composable
fun AiProfileScreen(modifier: Modifier = Modifier) {
    AiConcepts(aiConcepts = AiConcept.dummy(), modifier = modifier.fillMaxSize())
}

@Composable
private fun AiConcepts(
    aiConcepts: List<AiConcept>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.background(color = Color.White).padding(horizontal = 20.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            items = aiConcepts,
            key = { aiConcept -> aiConcept.conceptName },
        ) { aiConcept -> AiConceptItem(aiConcept) }
    }
}

@Composable
private fun AiConceptItem(
    aiConcept: AiConcept,
    modifier: Modifier = Modifier,
    onCreationButtonClick: () -> Unit = {},
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 0.dp,
        modifier = modifier.aspectRatio(5 / 4F),
    ) {
        AiConceptBackgroundImage(aiConcept)
        AiConceptContent(aiConcept, onCreationButtonClick)
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun AiConceptBackgroundImage(aiConcept: AiConcept) {
    GlideImage(
        model = aiConcept.conceptImageUrl,
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        transition = CrossFade,
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun AiConceptContent(
    aiConcept: AiConcept,
    onCreationButtonClick: () -> Unit,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        val animalType =
            when (aiConcept.animalType) {
                AiConceptAnimal.DOG -> stringResource(id = R.string.ai_profile_dog_only)
                AiConceptAnimal.CAT -> stringResource(id = R.string.ai_profile_cat_only)
            }

        if (aiConcept.isNewConcept) NewConceptBadge(modifier = Modifier.weight(1F))

        ConceptName(aiConcept)
        ConceptDescription(aiConcept, animalType)
        AiProfileCreationButton(onCreationButtonClick)
    }
}

@Composable
private fun ConceptName(aiConcept: AiConcept) {
    Text(
        text = aiConcept.conceptName,
        color = Color.White,
        fontSize = 24.sp,
        fontWeight = FontWeight.ExtraBold,
    )
}

@Composable
private fun ConceptDescription(
    aiConcept: AiConcept,
    animalType: String,
) {
    Text(
        text = "${aiConcept.conceptDescription} | $animalType",
        color = Color.White,
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.padding(bottom = 6.dp),
    )
}

@Composable
private fun AiProfileCreationButton(onCreationButtonClick: () -> Unit) {
    Button(
        onClick = onCreationButtonClick,
        modifier =
            Modifier
                .fillMaxWidth()
                .heightIn(min = 40.dp, max = 60.dp),
        shape = RoundedCornerShape(12.dp),
        colors =
            ButtonDefaults.buttonColors(
                backgroundColor = Purple60,
                contentColor = Color.White,
            ),
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
            color = Purple60,
            modifier = Modifier.wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
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

@Preview
@Composable
private fun AiProfileScreenPreview() {
    AiProfileScreen()
}
