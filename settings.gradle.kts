rootProject.name = "chrip"

pluginManagement {
    includeBuild("build-logic")
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// Modules
include("app")
include("user")
include("chat")
include("notification")
include("common")