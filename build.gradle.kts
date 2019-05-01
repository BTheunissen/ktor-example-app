group = "org.theunissen.tech"
version = "1.0-SNAPSHOT"

val main_class by extra("AppKt")

plugins {
    application
    kotlin("jvm") version "1.3.31"
    id("com.google.cloud.tools.jib").version("1.1.2")
}

application {
    mainClassName = main_class

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
    maven( "https://oss.jfrog.org/artifactory/oss-snapshot-local/")
}

val kotlin_version = "1.3.31"
val arrow_version = "0.9.0"
val kotlintest_version = "3.3.2"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compile("io.arrow-kt:arrow-core-data:$arrow_version")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:$kotlintest_version")
    testImplementation("io.kotlintest:kotlintest-assertions-arrow:$kotlintest_version")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jib {
    container {
        ports = listOf("8080")
        mainClass = main_class

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
