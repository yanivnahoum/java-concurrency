plugins {
    java
    id("io.freefair.lombok") version "5.3.0"
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
    val mockitoVersion = "3.6.28"
    implementation("com.google.guava:guava:30.0-jre")
    implementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("org.mockito:mockito-junit-jupiter:$mockitoVersion")
    testImplementation("org.assertj:assertj-core:3.18.1")
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
