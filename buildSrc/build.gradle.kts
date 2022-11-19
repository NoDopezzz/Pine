plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:7.3.1")

    implementation(kotlin("gradle-plugin", "1.7.20"))
}