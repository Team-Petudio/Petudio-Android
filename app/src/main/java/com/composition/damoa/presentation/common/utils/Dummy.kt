package com.composition.damoa.presentation.common.utils

import com.composition.damoa.R
import com.composition.damoa.data.model.PointChargeItem
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

fun pointChargeItems(): List<PointChargeItem> =
    listOf(
        PointChargeItem(
            point = 110,
            price = 1100,
        ),
        PointChargeItem(
            point = 220,
            price = 2200,
        ),
        PointChargeItem(
            point = 330,
            price = 3300,
        ),
        PointChargeItem(
            point = 440,
            price = 4400,
        ),
        PointChargeItem(
            point = 550,
            price = 5500,
        ),
        PointChargeItem(
            point = 660,
            price = 6600,
        ),
        PointChargeItem(
            point = 770,
            price = 7700,
        ),
        PointChargeItem(
            point = 880,
            price = 8800,
        ),
        PointChargeItem(
            point = 990,
            price = 9900,
        ),
        PointChargeItem(
            point = 1100,
            price = 11000,
        ),
    )
