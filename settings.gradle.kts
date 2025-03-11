pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // allows for Jitpack Repos (we use SecuredAndroidPersist library on that repo)
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "MitropolitikoMoviesAdvanced"
include(":app")
