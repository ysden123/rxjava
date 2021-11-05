plugins {
    // Apply the java Plugin to add support for Java.
    java
}

repositories {
    mavenCentral()
}

val rxJavaVersion = "3.1.2"
val slf4jVersion = "1.7.32"
val log4jSlf4jVersion = "2.14.1"
val junitVersion = "5.8.1"

dependencies {
    implementation("io.reactivex.rxjava3:rxjava:$rxJavaVersion")

    // Logging
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:jcl-over-slf4j:$slf4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:$log4jSlf4jVersion")

    // Use JUnit API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

tasks.compileJava {
    options.encoding = "UTF-8"
    options.release.set(17)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.test {
    // Use junit platform for unit tests.
    useJUnitPlatform()
}

project.version = "2.1.0"
