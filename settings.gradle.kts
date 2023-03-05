rootProject.name = "print-ms"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("spring-boot-starter-web", "org.springframework.boot", "spring-boot-starter-web").version("")
            library("jackson-kotlin", "com.fasterxml.jackson.module", "jackson-module-kotlin").version("")
            library("kotlin-reflect", "org.jetbrains.kotlin", "kotlin-reflect").version("")
            library("kotlin-stdlib", "org.jetbrains.kotlin", "kotlin-stdlib-jdk8").version("")

            // https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox
            library("pdfbox", "org.apache.pdfbox:pdfbox:3.0.0-RC1")

            // https://github.com/profesorfalken/WMI4Java/releases
            library("wmi4java", "com.profesorfalken:WMI4Java:1.6.3")

            library("spring-boot-starter-test", "org.springframework.boot", "spring-boot-starter-test").version("")

        }
    }
}