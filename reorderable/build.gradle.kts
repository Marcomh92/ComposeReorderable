import org.jetbrains.compose.ComposeBuildConfig.composeVersion

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("maven-publish")
    id("signing")
}

group = "org.burnoutcrew.composereorderable"
version = "0.9.7"

kotlin {
    jvm()
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.foundation)
                implementation(compose.animation)
                implementation("org.jetbrains.compose.ui:ui-util:${composeVersion}")
            }
        }
    }
}

val javadocJar = tasks.register("javadocJar", Jar::class.java) {
    archiveClassifier.set("javadoc")
}

publishing {
    publications {
        repositories {
            maven {
                name="oss"
                val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
                credentials {
                    username = extra.properties.getOrDefault("ossrh.Username", "") as String
                    password = extra.properties.getOrDefault("ossrh.Password", "") as String
                }
            }
        }
    }
    publications {
        withType<MavenPublication> {
            artifact(javadocJar)
            pom {
                name.set("ComposeReorderable")
                description.set("Reorderable Compose LazyList")
                licenses {
                    license {
                        name.set("Apache-2.0")
                        url.set("https://opensource.org/licenses/Apache-2.0")
                    }
                }
                url.set("https://github.com/aclassen/ComposeReorderable")
                issueManagement {
                    system.set("Github")
                    url.set("https://github.com/aclassen/ComposeReorderable/issues")
                }
                scm {
                    connection.set("https://github.com/aclassen/ComposeReorderable.git")
                    url.set("https://github.com/aclassen/ComposeReorderable")
                }
                developers {
                    developer {
                        name.set("Andre Claßen")
                        email.set("andreclassen1337@gmail.com")
                    }
                }
            }
        }
    }
}

//signing {
//    sign(publishing.publications)
//}

signing {
    val isJitpackBuild = System.getenv("JITPACK") == "true"

    if (!isJitpackBuild) {
        // Only attempt to sign if NOT running on JitPack
        // You might still want local checks if building locally without keys:
        val keyId = providers.gradleProperty("signing.keyId").orNull
        val password = providers.gradleProperty("signing.password").orNull
        val secRingFile = providers.gradleProperty("signing.secretKeyRingFile").orNull

        if (keyId != null && password != null && secRingFile != null) {
            println("Attempting to sign publications (local build with keys)...")
            sign(publishing.publications)
        } else {
            println("Skipping signing: Local signing properties not fully configured.")
            // Disable signing tasks explicitly if keys aren't set locally
            tasks.withType<Sign>().configureEach {
                enabled = false
            }
        }
    } else {
        println("Skipping signing: JitPack build environment detected.")
        // Explicitly disable signing tasks when on JitPack
        tasks.withType<Sign>().configureEach {
            enabled = false
        }
    }
}