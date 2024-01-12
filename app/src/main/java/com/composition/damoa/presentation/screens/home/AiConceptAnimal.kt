package com.composition.damoa.presentation.screens.home

data class AiConcept(
    val conceptName: String,
    val conceptDescription: String,
    val conceptImageUrl: String,
    val animalType: AiConceptAnimal,
    val isNewConcept: Boolean,
) {
    companion object {
        fun dummy(): List<AiConcept> =
            listOf(
                AiConcept(
                    "1D 애니메이션 컨셉",
                    "3D 애니메이션 컨셉",
                    "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                    AiConceptAnimal.DOG,
                    true,
                ),
                AiConcept(
                    "2D 애니메이션 컨셉",
                    "3D 애니메이션 컨셉",
                    "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                    AiConceptAnimal.DOG,
                    true,
                ),
                AiConcept(
                    "3D 애니메이션 컨셉",
                    "3D 애니메이션 컨셉",
                    "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                    AiConceptAnimal.DOG,
                    true,
                ),
            )
    }
}

enum class AiConceptAnimal {
    DOG,
    CAT,
}
