plugins {
    `maven-publish`
    id("com.android.library") version "8.9.0" apply false
    id("org.jetbrains.kotlin.multiplatform") version "1.8.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("org.jetbrains.compose") version "1.3.0" apply false
}

ext {
    extra["compileSdkVersion"] = 33
    extra["minSdkVersion"] = 21
    extra["targetSdkVersion"] = 33
}

//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
//    }
//}