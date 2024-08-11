plugins {
    java
    id("io.freefair.lombok") version "6.4.3"
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
    val mockitoVersion = "4.5.1"
    implementation("com.google.guava:guava:31.1-jre")
    implementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("org.mockito:mockito-junit-jupiter:$mockitoVersion")
    testImplementation("org.assertj:assertj-core:3.22.0")
}

tasks {
    withType<JavaCompile>().configureEach {
        options.apply {
            release.set(11)
        }
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
