package com.composition.damoa.presentation.common.utils

import com.composition.damoa.R
import com.composition.damoa.presentation.screens.profileCreation.PetPhoto

fun goodDogPhotoExamples(): List<PetPhoto> =
    listOf(
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_good_example1,
            descRes = R.string.dog_photo_upload_good_example_desc1,
        ),
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_good_example2,
            descRes = R.string.dog_photo_upload_good_example_desc2,
        ),
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_good_example3,
            descRes = R.string.dog_photo_upload_good_example_desc3,
        ),
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_good_example4,
            descRes = R.string.dog_photo_upload_good_example_desc4,
        ),
    )

fun badDogPhotoExamples(): List<PetPhoto> =
    listOf(
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_bad_example1,
            descRes = R.string.dog_photo_upload_bad_example_desc1,
        ),
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_bad_example2,
            descRes = R.string.dog_photo_upload_bad_example_desc2,
        ),
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_bad_example3,
            descRes = R.string.dog_photo_upload_bad_example_desc3,
        ),
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_bad_example4,
            descRes = R.string.dog_photo_upload_bad_example_desc4,
        ),
    )
