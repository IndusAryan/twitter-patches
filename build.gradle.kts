import org.gradle.kotlin.dsl.support.listFilesOrdered

plugins {
    kotlin("jvm") version "2.0.0"
    `maven-publish`
}

group = "indus.org"

repositories {
    mavenCentral()
    mavenLocal()
    google()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation(libs.revanced.patcher)
    implementation(libs.smali)
    // TODO: Required because build fails without it. Find a way to remove this dependency.
    implementation(libs.guava)
    // Used in JsonGenerator.
    implementation(libs.gson)

    // A dependency to the Android library unfortunately fails the build, which is why this is required.
    compileOnly(project("dummy"))
}

kotlin {
    jvmToolchain(11)
}

tasks.withType(Jar::class) {
    manifest {
        attributes["Name"] = "Twitter Patches"
        attributes["Description"] = "Patches for Twitter."
        attributes["Version"] = version
        attributes["Timestamp"] = System.currentTimeMillis().toString()
        attributes["Source"] = "git@github.com:IndusAryan/twitter-patches.git"
        attributes["Author"] = "IndusAryan"
        attributes["Contact"] = "null"
        attributes["Origin"] = "https://revanced.app"
        attributes["License"] = "GNU General Public License v3.0"
    }
}

tasks {
    register<DefaultTask>("generateBundle") {
        description = "Generate DEX files and add them in the JAR file"

        dependsOn(build)

        doLast {
            val d8 = File(System.getenv("ANDROID_HOME")).resolve("build-tools")
                .listFilesOrdered().last().resolve("d8").absolutePath

            val artifacts = configurations.archives.get().allArtifacts.files.files.first().absolutePath
            val workingDirectory = layout.buildDirectory.dir("libs").get().asFile

            exec {
                workingDir = workingDirectory
                commandLine = listOf(d8, artifacts)
            }

            exec {
                workingDir = workingDirectory
                commandLine = listOf("zip", "-u", artifacts, "classes.dex")
            }
        }
    }

    // Required to run tasks because Gradle semantic-release plugin runs the publish task.
    // Tracking: https://github.com/KengoTODA/gradle-semantic-release-plugin/issues/435
    named("publish") {
        dependsOn("generateBundle")
    }
}

publishing {
    publications {
        create<MavenPublication>("revanced-patches-publication") {
            from(components["java"])

            pom {
                name = "Twitter Patches"
                description = "Patches for Twitter."
                url = "https://null.app"

                licenses {
                    license {
                        name = "GNU General Public License v3.0"
                        url = "https://www.gnu.org/licenses/gpl-3.0.en.html"
                    }
                }
                developers {
                    developer {
                        id = "IndusAryan"
                        name = "IndusAryan"
                        email = "contact@null.app"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/IndusAryan/twitter-patches.git"
                    developerConnection = "scm:git:git@github.com:IndusAryan/twitter-patches.git"
                    url = "https://github.com/IndusAryan/twitter-patches"
                }
            }
        }
    }
}
