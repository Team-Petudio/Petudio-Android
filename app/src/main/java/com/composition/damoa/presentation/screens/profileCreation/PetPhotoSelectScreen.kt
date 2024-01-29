package com.composition.damoa.presentation.screens.profileCreation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.composition.damoa.R
import com.composition.damoa.data.model.Pet
import com.composition.damoa.presentation.common.components.KeepGoingButton
import com.composition.damoa.presentation.common.components.MediumDescription
import com.composition.damoa.presentation.common.components.MediumTitle
import com.composition.damoa.presentation.ui.theme.Gray20
import com.composition.damoa.presentation.ui.theme.Purple60

@Composable
fun PetPhotoSelectScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    pets: List<Pet>,
) {
    Surface(
        color = Color.White,
        modifier =
        modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        PetPhotoSelectContent(
            pets = pets,
            onNewPhotoUploadClick = { navController.navigate(ProfileCreationScreen.PET_NAME.route) }
        )
        KeepGoingButton(onClick = {
            // 결제하기 화면 이동
        })
    }
}

@Composable
private fun PetPhotoSelectContent(
    pets: List<Pet>,
    onNewPhotoUploadClick: () -> Unit,
) {
    PetList(
        pets = pets,
        onNewPhotoUploadClick = onNewPhotoUploadClick,
    )
}

@Composable
private fun PetPhotoSelectTitle() {
    MediumTitle(
        modifier = Modifier.padding(top = 12.dp, bottom = 6.dp),
        titleRes = R.string.pet_photo_select_title,
    )
}

@Composable
private fun PetPhotoSelectDescription() {
    MediumDescription(
        modifier = Modifier.padding(bottom = 20.dp),
        descriptionRes = R.string.pet_photo_select_desc,
    )
}

@Composable
private fun NewPhotoUploadButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    OutlinedButton(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.5.dp, Gray20),
        onClick = onClick,
    ) {
        Row(
            Modifier.padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.padding(end = 4.dp),
            )
            Text(
                text = stringResource(id = R.string.pet_photo_new_upload),
                color = Color.Black,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun PetList(
    modifier: Modifier = Modifier,
    pets: List<Pet>,
    onNewPhotoUploadClick: () -> Unit,
) {
    LazyColumn(
        modifier = modifier.selectableGroup(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 100.dp),
    ) {
        item {
            PetPhotoSelectTitle()
            PetPhotoSelectDescription()
            NewPhotoUploadButton(onClick = onNewPhotoUploadClick)
        }
        items(pets) { uploadedPhoto ->
            PetItem(photo = uploadedPhoto)
        }
    }
}

@Composable
private fun PetItem(
    modifier: Modifier = Modifier,
    photo: Pet,
    onClick: () -> Unit = {},
    selected: Boolean = false,
) {
    val borderWidth = if (selected) 2.dp else 1.5.dp
    val borderColor = if (selected) Purple60 else Gray20
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .selectable(
                selected = true,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
                role = Role.RadioButton,
            )
            .fillMaxWidth()
            .aspectRatio(10 / 4F)
            .border(borderWidth, borderColor, RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        UploadedPhotoThumbnail(thumbnailUrl = photo.thumbnailUrl)
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun UploadedPhotoThumbnail(
    modifier: Modifier = Modifier,
    thumbnailUrl: String,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 0.dp,
        modifier =
        modifier
            .fillMaxHeight()
            .aspectRatio(1F),
    ) {
        GlideImage(
            model = thumbnailUrl,
            contentDescription = null,
            transition = CrossFade,
            contentScale = ContentScale.Crop,
        )
    }
}
