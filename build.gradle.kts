import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
    mavenLocal()
    mavenCentral()
}

plugins {
    idea
    id("org.springframework.boot") version "2.7.18"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.spring") version "2.1.20"
    id("com.google.devtools.ksp") version "2.1.20-2.0.0"
}

group = "top.potmot"
version = "0.4.0" // 2025-3-27
java.sourceCompatibility = JavaVersion.VERSION_1_8

val jimmerVersion = "0.9.96"

val mysqlVersion = "9.0.0"
val postgresVersion = "42.7.3"
val h2Version = "2.2.224"

val schemacrawlerVersion = "16.26.2"
val liquibaseVersion = "4.22.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.babyfish.jimmer:jimmer-spring-boot-starter:${jimmerVersion}")

    ksp("org.babyfish.jimmer:jimmer-ksp:${jimmerVersion}")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // https://mvnrepository.com/artifact/com.mysql/mysql-connector-j
    runtimeOnly("com.mysql:mysql-connector-j:${mysqlVersion}")

    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    runtimeOnly("org.postgresql:postgresql:${postgresVersion}")

    // https://mvnrepository.com/artifact/com.h2database/h2
    runtimeOnly("com.h2database:h2:${h2Version}")

    // https://mvnrepository.com/artifact/us.fatehi/schemacrawler
    implementation("us.fatehi:schemacrawler-api:${schemacrawlerVersion}") {
        exclude(group = "org.slf4j", module = "slf4j-nop")
        exclude(group = "org.slf4j", module = "slf4j-jdk14")
    }
    // https://mvnrepository.com/artifact/us.fatehi/schemacrawler-tools
    implementation("us.fatehi:schemacrawler-tools:${schemacrawlerVersion}") {
        exclude(group = "org.slf4j", module = "slf4j-nop")
        exclude(group = "org.slf4j", module = "slf4j-jdk14")
    }
    // https://mvnrepository.com/artifact/us.fatehi/schemacrawler-mysql
    implementation("us.fatehi:schemacrawler-mysql:${schemacrawlerVersion}") {
        exclude(group = "org.slf4j", module = "slf4j-nop")
        exclude(group = "org.slf4j", module = "slf4j-jdk14")
        exclude(group = "com.mysql", module = "mysql-connector-j")
    }
    // https://mvnrepository.com/artifact/us.fatehi/schemacrawler-postgresql
    implementation("us.fatehi:schemacrawler-postgresql:${schemacrawlerVersion}") {
        exclude(group = "org.slf4j", module = "slf4j-nop")
        exclude(group = "org.slf4j", module = "slf4j-jdk14")
    }

    // https://mvnrepository.com/artifact/org.liquibase/liquibase-core
    implementation("org.liquibase:liquibase-core:${liquibaseVersion}")
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
        jvmTarget.set(JvmTarget.JVM_1_8)
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
