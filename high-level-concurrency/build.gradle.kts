plugins {
    java
    id("io.freefair.lombok") version "5.2.1"
}

repositories {
    mavenCentral()
}

sourceSets {
    test {
        java {
            srcDir("src/main/java")
        }
    }
}

dependencies {
    val mockitoVersion = "3.5.13"
    implementation("com.google.guava:guava:29.0-jre")
    implementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("org.mockito:mockito-junit-jupiter:$mockitoVersion")
    testImplementation("org.assertj:assertj-core:3.17.2")
}

tasks {
    withType<JavaCompile>().configureEach {
        options.apply {
            release.set(11)
        }
    }
    generateLombokConfig {
        isEnabled = false
    }

    test {
        useJUnitPlatform {
            excludeTags("slow")
        }

        testLogging {
            events("passed", "skipped", "failed")
            showStandardStreams = true
        }
    }
}
