import org.asciidoctor.gradle.jvm.AsciidoctorTask

plugins {
    "2.0.21".let { kotlinVersion ->
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
    }
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    idea
    id("org.asciidoctor.jvm.convert") version "4.0.3"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
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
    maven { url = uri("https://repo.spring.io/milestone") }
}

extra["springModulithVersion"] = "1.3.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.modulith:spring-modulith-starter-core")
    implementation("org.springframework.modulith:spring-modulith-starter-jdbc")
    implementation("org.springframework.modulith:spring-modulith-events-core")
    implementation("org.springframework.modulith:spring-modulith-events-api")
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")

    runtimeOnly("org.springframework.boot:spring-boot-starter-actuator")
    runtimeOnly("org.springframework.modulith:spring-modulith-actuator")

    runtimeOnly("org.springframework.modulith:spring-modulith-observability")
    runtimeOnly("io.micrometer:micrometer-tracing-bridge-otel")
    runtimeOnly("io.opentelemetry:opentelemetry-exporter-zipkin")

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

    /*
     Creates a nice "test report" in the console.
     */
    addTestListener(
        object : TestListener {
            override fun beforeTest(p0: TestDescriptor?) = Unit
            override fun beforeSuite(p0: TestDescriptor?) = Unit
            override fun afterTest(desc: TestDescriptor, result: TestResult) = Unit
            override fun afterSuite(desc: TestDescriptor, result: TestResult) {
                if (desc.parent == null) {
                    val output =
                        "${desc.name} results: ${result.resultType} (${result.testCount} tests, ${result.successfulTestCount} successes, ${result.failedTestCount} failures, ${result.skippedTestCount} skipped)"
                    val startItem = "|  "
                    val endItem = "  |"
                    val repeatLength = startItem.length + output.length + endItem.length
                    println(
                        "\n" + ("-".repeat(repeatLength)) + "\n" + startItem + output + endItem + "\n" + ("-".repeat(
                            repeatLength
                        ))
                    )
                }
            }
        }
    )
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
// TODO pevest: This does not work...yet :)
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

/*
If you have problems with a certain rule in just one or maybe two places, you can switch them off in the code using e.g:
@Suppress("ktlint:standard:function-signature")
 */
configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    verbose.set(true)
}
