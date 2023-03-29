plugins {
  java
  application
}

repositories {
  mavenCentral()
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(11))
  }
}

dependencies {
  implementation(platform("io.vertx:vertx-stack-depchain:4.4.0"))
  implementation("io.vertx:vertx-web-sstore-infinispan")
}

application {
  mainClassName = "io.vertx.howtos.web.sessions.ServerVerticle"
}

tasks.wrapper {
  gradleVersion = "7.6"
}
