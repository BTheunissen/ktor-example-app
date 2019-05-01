group = "org.theunissen.tech"
version = "1.0-SNAPSHOT"

val mainClass by extra("AppKt")

plugins {
    application
    kotlin("jvm") version "1.3.31"
    id("com.google.cloud.tools.jib").version("1.1.2")
}

application {
    mainClassName = mainClass

    applicationDefaultJvmArgs = listOf(
        "-server",
        "-Djava.awt.headless=true",
        "-Xms128m",
        "-Xmx256m",
        "-XX:+UseG1GC",
        "-XX:MaxGCPauseMillis=100"
    )
}

java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
    jcenter()
    maven("https://dl.bintray.com/arrow-kt/arrow-kt/")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("PASSED", "FAILED", "SKIPPED")
    }
}

jib {
    container {
        ports = listOf("8080")
        mainClass = mainClass

        // Docker defaults intended for Java 8 (>= 8u191) containers
        jvmFlags = listOf(
            "-server",
            "-Djava.awt.headless=true",
            "-XX:InitialRAMFraction=2",
            "-XX:MinRAMFraction=2",
            "-XX:MaxRAMFraction=2",
            "-XX:+UseG1GC",
            "-XX:MaxGCPauseMillis=100",
            "-XX:+UseStringDeduplication"
        )
    }
}

val kotlinVersion = "1.3.31"
val arrowVersion = "0.9.0"
val kotlinTestVersion = "3.3.2"
val junitVersion = "5.3.2"
val slf4jVersion = "1.7.26"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compile("io.arrow-kt:arrow-core-data:$arrowVersion")
    testCompile("io.kotlintest:kotlintest-runner-junit5:$kotlinTestVersion")
    testImplementation("io.kotlintest:kotlintest-assertions-arrow:$kotlinTestVersion")
    testImplementation("org.slf4j:slf4j-simple:$slf4jVersion")
}
