allprojects {
    repositories {
        maven {
            url = uri("https://repocentral.it.att.com:8443/nexus/content/groups/att-public-group")
            credentials {
                username = "<username>"
                password = "<password>"
            }
        }
    }
}