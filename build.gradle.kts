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
  mainClass = "io.vertx.howtos.web.sessions.ServerVerticle"
}
