plugins {
    java
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
    implementation("com.google.guava:guava:27.1-jre")
    implementation("org.junit.jupiter:junit-jupiter:5.4.2")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        setShowStandardStreams(true)
    }
}
