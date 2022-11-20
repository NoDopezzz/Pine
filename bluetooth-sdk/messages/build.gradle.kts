plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(Libraries.Utils.jsonSerialization)
}
