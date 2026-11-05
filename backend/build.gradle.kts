plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.spotless)
}

group = "squad.nononotaa"
version = "0.0.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.boot.starter.webmvc)
    implementation(libs.kotlin.reflect)
    implementation(libs.springdoc.openapi.starter.webmvc.ui)
    implementation(libs.jackson.module.kotlin)
    testImplementation(libs.spring.boot.starter.webmvc.test)
    testImplementation(libs.kotlin.test.junit5)
    testRuntimeOnly(libs.junit.platform.launcher)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("build/**")
        ktlint()
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register("installGitHooks") {
    val hooksDir = file("${rootProject.projectDir}/.git/hooks")
    val preCommitSource = file("${rootProject.projectDir}/scripts/pre-commit")
    val preCommitHook = file("${hooksDir}/pre-commit")

    doLast {
        hooksDir.mkdirs()
        preCommitSource.copyTo(preCommitHook, overwrite = true)
        preCommitHook.setExecutable(true)
        println("Git pre-commit hook installed successfully.")
    }
}
