import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.11"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	id("com.google.cloud.tools.jib") version "3.1.2"


	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
}


group = "com.chatandpay"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {

	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.kafka:spring-kafka")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	// reactor-netty 및 netty-all 의존성 추가
	implementation("io.projectreactor.netty:reactor-netty:1.0.31")
	implementation("io.netty:netty-all:4.1.68.Final")
	implementation ("org.springframework.boot:spring-boot-starter-amqp")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:3.1.7")



//	testImplementation("org.springframework.security:spring-security-test")


	implementation("org.springframework.boot:spring-boot-starter-freemarker")
	implementation("org.webjars.bower:bootstrap:4.3.1")
	implementation("org.webjars.bower:vue:2.5.16")
	implementation("org.webjars.bower:axios:0.17.1")
	implementation("org.webjars:sockjs-client:1.1.2")
	implementation("org.webjars:stomp-websocket:2.3.3-1")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.session:spring-session-data-redis")
	implementation("io.lettuce:lettuce-core:6.2.6.RELEASE")
	implementation("org.springframework.boot:spring-boot-starter-aop")
//	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j:2.1.7")




//
//	implementation("com.fasterxml.uuid:java-uuid-generator:3.2.0")

	implementation("org.springframework.boot:spring-boot-devtools:2.6.3")
	implementation("javax.validation:validation-api:2.0.1.Final")


}

jib {
	from {
		image = "openjdk:17-jdk-alpine"
	}
	to {
		image = "ankyeongeun/chat-and-pay:chat4"
	}
	container {
		jvmFlags = listOf("-Xms512m", "-Xmx512m")
	}

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

