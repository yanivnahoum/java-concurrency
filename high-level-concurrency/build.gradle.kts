plugins {
    java
    id("io.freefair.lombok") version "8.6"
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
    val mockitoVersion = "5.12.0"
    val guavaVersion = "33.2.1-jre"
    val junitJupiterVersion = "5.10.3"
    val assertjVersion = "3.26.3"
    implementation("com.google.guava:guava:$guavaVersion")
    implementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("org.mockito:mockito-junit-jupiter:$mockitoVersion")
    testImplementation("org.assertj:assertj-core:$assertjVersion")
}

tasks {
    withType<JavaCompile>().configureEach {
        options.apply {
            release.set(21)
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
