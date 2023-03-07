@file:Suppress("UnstableApiUsage")

rootProject.name = "print-ms"

@Suppress("UnstableApiUsages")
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}