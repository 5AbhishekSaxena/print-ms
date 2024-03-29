import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin

@Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")
plugins {
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.kotlin.lang)
	alias(libs.plugins.kotlin.spring)
}

group = "tech.developingdeveloper"
version = "0.1.0"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation(platform(SpringBootPlugin.BOM_COORDINATES))
	implementation(libs.spring.boot.starter.web)
	implementation(libs.jackson.kotlin)
	implementation(libs.kotlin.reflect)
	implementation(libs.kotlin.stdlib)

	implementation(libs.pdfbox)
	implementation(libs.wmi4java)

	testImplementation(libs.spring.boot.starter.test)
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
