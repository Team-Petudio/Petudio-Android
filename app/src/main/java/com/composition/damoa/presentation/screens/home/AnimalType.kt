package com.composition.damoa.presentation.screens.home

data class ProfileConcept(
    val id: Long,
    val conceptName: String,
    val conceptDescription: String,
    val conceptImageUrl: String,
    val animalType: AnimalType,
    val isNewConcept: Boolean,
) {
    enum class AnimalType {
        DOG,
        CAT,
    }

    companion object {
        fun dummy(): List<ProfileConcept> =
            listOf(
                ProfileConcept(
                    0,
                    "1D 애니메이션 컨셉",
                    "3D 애니메이션 컨셉",
                    "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                    AnimalType.DOG,
                    true,
                ),
                ProfileConcept(
                    1,
                    "2D 애니메이션 컨셉",
                    "3D 애니메이션 컨셉",
                    "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                    AnimalType.DOG,
                    true,
                ),
                ProfileConcept(
                    2,
                    "3D 애니메이션 컨셉",
                    "3D 애니메이션 컨셉",
                    "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                    AnimalType.DOG,
                    true,
                ),
            )
    }
}