import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension // Keep this import

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

// ADD THIS BLOCK TO CONFIGURE NODE GLOBALLY:
rootProject.extensions.configure<NodeJsRootExtension> {
    version = "16.13.0" // Set the version Gradle is looking for
    download = true   // Explicitly enable download via the extension property
}
// END OF BLOCK TO ADD

kotlin {
    js(IR) {
        browser {
            testTask {
                testLogging.showStandardStreams = true
                useKarma {
                    useChromeHeadless()
                    useFirefox()
                }
            }
        }
        // Ensure the nodejs {} block is REMOVED from here
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(compose.web.core)
                implementation(compose.runtime)
                implementation(compose.material)
                implementation(project(":reorderable"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

compose.experimental {
    web.application {}
}

// Keep this commented out or remove it - we configured Node directly above
// afterEvaluate {
//    rootProject.extensions.configure<NodeJsRootExtension> {
// //        versions.webpackDevServer.version = "4.0.0"
// //        versions.webpackCli.version = "4.9.0"
// //        nodeVersion = "16.0.0"
//    }
// }