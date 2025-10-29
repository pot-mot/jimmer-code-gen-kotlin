import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
    mavenLocal()
    mavenCentral()
}

plugins {
    idea
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ksp)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.spring.boot)
}

group = "top.potmot"
version = "0.5.0" // 2025-8-8
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.validation)
    testImplementation(libs.spring.boot.starter.test)

    implementation(libs.kotlin.reflect)

    implementation(libs.jimmer.spring.boot.starter)
    ksp(libs.jimmer.ksp)

    runtimeOnly(libs.h2)
    runtimeOnly(libs.postgres)
    runtimeOnly(libs.mysql)
    runtimeOnly(libs.oracle)
    runtimeOnly(libs.sqlserver)
    runtimeOnly(libs.sqlite)
}

// Without this configuration, gradle command can still run.
// However, Intellij cannot find the generated source.
kotlin {
    sourceSets {
        main {
            kotlin.srcDir("build/generated/ksp/main/kotlin")
        }
    }
}

ksp {
//    切换dto可变性
//    arg("jimmer.dto.mutable", "true")
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

// 强制要求kspKotlin任务添加src/main/dto
afterEvaluate {
    tasks {
        "kspKotlin" {
            inputs.dir(layout.projectDirectory.dir("src/main/dto"))
        }
    }
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

tasks.register<Jar>("slimJar") {
    // 设置 JAR 文件名
    archiveBaseName.set("${project.name}-slim")

    // 包含项目自身编译后的类文件
    from(sourceSets.main.get().output) {
        exclude("/sql/**", "/dist/**", "/application**", "/top/potmot/service/**", "/top/potmot/JimmerCodeGenApplication**")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "top.potmot.JimmerCodeGenApplicationKt"
    }
}
