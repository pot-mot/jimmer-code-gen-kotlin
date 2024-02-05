import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
//    maven {
//        setUrl("https://maven.aliyun.com/repository/public/")
//    }
//    maven {
//        setUrl("https://maven.aliyun.com/repository/spring/")
//    }
    mavenLocal()
    mavenCentral()
}

plugins {
    id("org.springframework.boot") version "2.7.13"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.spring") version "1.9.21"
    id("com.google.devtools.ksp") version "1.9.21-1.0.15"
}

group = "top.potmot"
version = "0.0.6" // 2024-2-5
java.sourceCompatibility = JavaVersion.VERSION_1_8

val jimmerVersion = "0.8.87"

val mysqlVersion = "8.3.0"
val postgreVersion = "42.6.0"
val h2Version = "2.2.224"

val schemacrawlerVersion = "16.21.1"
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

    runtimeOnly("com.mysql:mysql-connector-j:${mysqlVersion}")

    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:${postgreVersion}")

    runtimeOnly("com.h2database:h2:${h2Version}")

    // https://mvnrepository.com/artifact/us.fatehi/schemacrawler
    implementation("us.fatehi:schemacrawler-api:${schemacrawlerVersion}") {
        exclude(group = "org.slf4j", module = "slf4j-nop")
        exclude(group = "org.slf4j", module = "slf4j-jdk14")
    }
    // https://mvnrepository.com/artifact/us.fatehi/schemacrawler-tools
    implementation("us.fatehi:schemacrawler-tools:${schemacrawlerVersion}"){
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
    sourceSets{
        main {
            kotlin.srcDir("build/generated/ksp/main/kotlin")
        }
    }
}

ksp {
    arg("jimmer.dto.dirs", "src/main/dto")
    arg("jimmer.dto.mutable", "true")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
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
