plugins {
    kotlin("multiplatform") version "1.8.0"
    id("io.kotest.multiplatform") version "5.5.4"
    kotlin("plugin.serialization") version "1.8.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

val kotlinVersion = "1.8.0"
val serializationVersion = "1.5.0-RC"
val ktorVersion = "2.2.2"
val kotestVersion = "5.5.4"
val kotlinWrappers = "org.jetbrains.kotlin-wrappers"
val kotlinWrappersVersion = "1.0.0-pre.490"
val kotlinHtmlVersion = "0.7.2"
val logbackVersion = "1.4.5"

kotlin {
    jvm {
        withJava()
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation ("com.michaeltroger:latin-to-cyrillic:1.0.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-sessions:$ktorVersion")
                implementation("io.ktor:ktor-server-auth:$ktorVersion")
                implementation("io.ktor:ktor-server-core:$ktorVersion")
                implementation("io.ktor:ktor-server-netty:$ktorVersion")
                implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-server-html-builder-jvm:$ktorVersion")
                implementation("io.ktor:ktor-server-auth:$ktorVersion")
                implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")
                implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:$kotlinHtmlVersion")
                implementation("ch.qos.logback:logback-classic:$logbackVersion")

            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("io.ktor:ktor-server-test-host:$ktorVersion")
                implementation("io.kotest:kotest-runner-junit5:$kotestVersion")
                implementation("io.kotest:kotest-assertions-core:$kotestVersion")
            }
        }
        val jsMain by getting {
            dependencies {
                dependencies {
                    implementation(
                        project.dependencies.enforcedPlatform(
                            "$kotlinWrappers:kotlin-wrappers-bom:$kotlinWrappersVersion"
                        )
                    )
                    implementation("$kotlinWrappers:kotlin-emotion")
                    implementation("$kotlinWrappers:kotlin-react")
                    implementation("$kotlinWrappers:kotlin-react-dom")
                    implementation("$kotlinWrappers:kotlin-react-router-dom")
                    implementation("$kotlinWrappers:kotlin-react-redux")
                    implementation("$kotlinWrappers:kotlin-tanstack-react-query")
                    implementation("$kotlinWrappers:kotlin-tanstack-react-query-devtools")
                    implementation(npm("cross-fetch", "3.1.5"))
                    implementation(npm("jszip", "3.10.1"))
                }
            }
            val jsTest by getting
        }
    }
}

application {
    mainClass.set("org.example.application.ServerKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}
tasks.register<Copy>("buildClient") {
    dependsOn("jsBrowserDevelopmentWebpack")
    from("$buildDir/developmentExecutable/")
    into("$buildDir/processedResources/jvm/main/")
}

tasks.named("run") {
    dependsOn("buildClient")
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}