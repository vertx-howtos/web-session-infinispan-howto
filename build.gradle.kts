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
  implementation(platform("io.vertx:vertx-stack-depchain:5.0.0.CR2"))
  implementation("io.vertx:vertx-web-sstore-infinispan")
}

application {
  mainClassName = "io.vertx.howtos.web.sessions.ServerVerticle"
}

tasks.wrapper {
  gradleVersion = "7.6"
}
