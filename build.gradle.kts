group = "tech.theunissen"
version = "1.0-SNAPSHOT"

plugins {
    application
    kotlin("jvm") version "1.3.31"
    id("com.google.cloud.tools.jib").version("1.1.2")
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

val kotlinVersion = "1.3.31"
val koinVersion = "2.0.1"
val ktorVersion = "1.2.2"
val arrowVersion = "0.9.0"
val kotlinTestVersion = "3.3.2"
val junitVersion = "5.3.2"
val logbackVersion = "1.2.3"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.ktor:ktor:$ktorVersion")
    implementation("io.ktor:ktor-jackson:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("org.koin:koin-core:$koinVersion")
    compile("org.koin:koin-ktor:$koinVersion")
    compile("io.arrow-kt:arrow-core-data:$arrowVersion")
    compile("ch.qos.logback:logback-classic:$logbackVersion")
    testCompile("io.kotlintest:kotlintest-runner-junit5:$kotlinTestVersion")
    testCompile("org.koin:koin-test:$koinVersion")
    testImplementation("io.kotlintest:kotlintest-assertions-arrow:$kotlinTestVersion")
}

val nettyMainClass = "io.ktor.server.netty.EngineMain"

application {
    mainClassName = nettyMainClass
}

jib {
    container {
        ports = listOf("8080")
        mainClass = nettyMainClass

        // Docker defaults intended for Java 8 (>= 8u191) containers
        jvmFlags = listOf(
            "-server",
            "-XX:+UnlockExperimentalVMOptions",
            "-XX:+UseCGroupMemoryLimitForHeap",
            "-XX:InitialRAMFraction=2",
            "-XX:MinRAMFraction=2",
            "-XX:MaxRAMFraction=2",
            "-XX:+UseG1GC",
            "-XX:MaxGCPauseMillis=100",
            "-XX:+UseStringDeduplication"
        )
    }
}
