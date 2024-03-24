//if using Postgres
//val postgresVersion: String by project
val koinKtor: String by project
val hikaricpVersion: String by project
val exposed_version: String by project

plugins {
    application
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization").version("1.9.10")
}
group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.22")
    implementation("io.ktor:ktor-server-core-jvm:2.3.7")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.7")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("io.ktor:ktor-server-call-logging:2.3.7")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.8")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.8")
    implementation("io.insert-koin:koin-logger-slf4j:3.5.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.23")
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    /*MySQL connector library provides the driver for the MySQL server which
     is necessary for connecting to the database. Similar to the Postgresql library.
    if using mysql*/
    implementation("mysql:mysql-connector-java:8.0.33")

    //if using Postgres
    //implementation("org.postgresql:postgresql:$postgresVersion")

    // Koin for Ktor
    implementation("io.insert-koin:koin-ktor:$koinKtor")

   /* Facilitates connection pooling, optimizing performance and resource usage by
     reusing connections instead of creating new ones for each operation.
    connection pooling*/
    implementation("com.zaxxer:HikariCP:$hikaricpVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}