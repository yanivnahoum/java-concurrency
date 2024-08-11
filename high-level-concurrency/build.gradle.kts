plugins {
    java
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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
    val mockitoVersion = "2.27.0"
    implementation("com.google.guava:guava:27.1-jre")
    implementation("org.junit.jupiter:junit-jupiter:5.4.2")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("org.mockito:mockito-junit-jupiter:$mockitoVersion")
    testImplementation("org.assertj:assertj-core:3.12.2")
}

tasks.test {
    useJUnitPlatform {
        excludeTags("slow")
    }

    testLogging {
        events("passed", "skipped", "failed")
        setShowStandardStreams(true)
    }
}
