package com.composition.damoa.data.model

data class ProfileConcept(
    val id: Long,
    val conceptName: String,
    val conceptDescription: String,
    val conceptImageUrl: String,
    val petType: PetType,
    val isNewConcept: Boolean,
) {
    companion object {
        fun dummy(): List<ProfileConcept> =
            listOf(
                ProfileConcept(
                    0,
                    "1D 애니메이션 컨셉",
                    "3D 애니메이션 컨셉",
                    "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                    PetType.DOG,
                    true,
                ),
                ProfileConcept(
                    1,
                    "2D 애니메이션 컨셉",
                    "3D 애니메이션 컨셉",
                    "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                    PetType.DOG,
                    true,
                ),
                ProfileConcept(
                    2,
                    "3D 애니메이션 컨셉",
                    "3D 애니메이션 컨셉",
                    "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                    PetType.DOG,
                    true,
                ),
            )
    }
}