plugins {
  java
  application
}

repositories {
  mavenCentral()
}

dependencies {
  val vertxVersion = "4.1.0-SNAPSHOT"
  implementation("io.vertx:vertx-web-sstore-infinispan:${vertxVersion}")
}

application {
  mainClassName = "io.vertx.howtos.web.sessions.ServerVerticle"
}

tasks.wrapper {
  gradleVersion = "5.2"
}
