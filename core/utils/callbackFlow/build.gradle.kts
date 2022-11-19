plugins {
    kotlin("jvm")
}

dependencies {
    implementation(Libraries.coroutines)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}