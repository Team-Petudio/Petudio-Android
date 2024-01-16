@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    startParameter.isOffline = false
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Petudio"
include(":app")
