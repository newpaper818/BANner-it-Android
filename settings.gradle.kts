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
    }
}

rootProject.name = "BANner it!"
include(":app")
include(":core:ui:ui")
include(":core:ui:designsystem")
include(":core:data:data")
include(":core:model")
include(":core:data:local-db")
include(":feature:signin")
include(":feature:report")
include(":feature:record")
include(":feature:more")
include(":core:utils")
include(":core:data:credentials")
include(":core:data:remote-db")
include(":core:data:local-image-file")
