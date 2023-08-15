import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
    // 依赖使用阿里云 maven 源
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
    id("io.spring.dependency-management") version "1.0.12.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    id("com.google.devtools.ksp") version "1.7.10-1.0.6"
}

group = "top.potmot"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val jimmerVersion = "0.8.1"
val mysqlVersion = "8.0.30"
val caffeineVersion = "2.9.1"
val velocityVersion = "2.3"
val schemacrawlerVersion = "16.20.4"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.babyfish.jimmer:jimmer-spring-boot-starter:${jimmerVersion}")

    ksp("org.babyfish.jimmer:jimmer-ksp:${jimmerVersion}")

    runtimeOnly("com.github.ben-manes.caffeine:caffeine:${caffeineVersion}")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    runtimeOnly("mysql:mysql-connector-java:${mysqlVersion}")

    implementation("org.apache.velocity:velocity-engine-core:${velocityVersion}")

    // https://mvnrepository.com/artifact/us.fatehi/schemacrawler
    implementation("us.fatehi:schemacrawler-api:${schemacrawlerVersion}") {
        exclude(group = "org.slf4j", module = "slf4j-nop")
    }
    // https://mvnrepository.com/artifact/us.fatehi/schemacrawler-tools
    implementation("us.fatehi:schemacrawler-tools:${schemacrawlerVersion}"){
        exclude(group = "org.slf4j", module = "slf4j-nop")
    }
    // https://mvnrepository.com/artifact/us.fatehi/schemacrawler-mysql
    implementation("us.fatehi:schemacrawler-mysql:${schemacrawlerVersion}") {
        exclude(group = "org.slf4j", module = "slf4j-nop")
    }
}

// Without this configuration, gradle command can still run.
// However, Intellij cannot find the generated source.
kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
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
