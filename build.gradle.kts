import org.asciidoctor.gradle.jvm.AsciidoctorTask

plugins {
    "2.0.21".let { kotlinVersion ->
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
    }
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    idea
    id("org.asciidoctor.jvm.convert") version "4.0.3"
}

group = "nu.westlin"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

extra["springModulithVersion"] = "1.2.5"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.modulith:spring-modulith-starter-core")
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.0")

    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.modulith:spring-modulith-starter-test")
    testImplementation("com.ninja-squad:springmockk:4.0.2")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.modulith:spring-modulith-bom:${property("springModulithVersion")}")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

/**
 * Tells IntelliJ to download JavaDoc and sources.
 */
idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

// TODO pevest: Depends on tests
tasks {
  "asciidoctor"(AsciidoctorTask::class) {
    setSourceDir(file("build/spring-modulith-docs"))
/*
    sources(delegateClosureOf<PatternSet> {
      include("all-docs.adoc", )
    })
*/
    setOutputDir(file("build/docs"))
  }
}

tasks.withType<org.springframework.boot.gradle.tasks.run.BootRun> {
    systemProperties(System.getProperties().mapKeys { it.key as String })
}
