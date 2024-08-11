allprojects {
    repositories {
        maven {
            url = uri("http://mavencentral.it.att.com:8081/nexus/content/groups/att-public-group")
            credentials {
                username = "<username>"
                password = "<password>"
            }
        }
    }
}