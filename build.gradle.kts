buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Plugin.gradle)
        classpath(Plugin.kotlinGradle)
        classpath(Plugin.hilt)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}