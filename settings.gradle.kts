rootProject.name = "chrip"

pluginManagement {
    includeBuild("build-logic")
}

// Modules
include("app")
include("user")
include("chat")
include("notification")
include("common")