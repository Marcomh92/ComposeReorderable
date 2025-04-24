pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        // This is correct here for plugins
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

// ADD THIS ENTIRE BLOCK FOR DEPENDENCY REPOSITORIES:
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT) // Recommended setting
    repositories {
        google()
        mavenCentral()
        // Add this again HERE for dependencies like skiko-js
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
// END OF BLOCK TO ADD

rootProject.name = "ComposeReorderList" // Or ComposeReorderable, match folder name ideally

// Includes are correct
include(":android")
include(":desktop")
include(":reorderable")
include(":web")