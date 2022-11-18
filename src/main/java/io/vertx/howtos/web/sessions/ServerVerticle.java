package io.vertx.howtos.web.sessions;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.SessionStore;
import io.vertx.ext.web.sstore.infinispan.InfinispanSessionStore;

import java.util.Date;

public class ServerVerticle extends AbstractVerticle {

  public static final String TEMPLATE = ""
    + "Session [%s] created on %s%n"
    + "%n"
    + "Page generated on %s%n";

  @Override
  public void start() {
    Router router = Router.router(vertx);

    JsonObject options = new JsonObject()
      .put("servers", new JsonArray()
        .add(new JsonObject()
          .put("host", "localhost")
          .put("port", 11222)
          .put("username", "admin")
          .put("password", "bar"))
      );
    SessionStore store = InfinispanSessionStore.create(vertx, options); // <1>

    router.route().handler(SessionHandler.create(store)); // <2>

    router.get("/").handler(ctx -> {
      Session session = ctx.session();
      session.computeIfAbsent("createdOn", s -> System.currentTimeMillis()); // <3>

      String sessionId = session.id();
      Date createdOn = new Date(session.<Long>get("createdOn"));
      Date now = new Date();

      ctx.end(String.format(TEMPLATE, sessionId, createdOn, now)); // <4>
    });

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080);
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new ServerVerticle());
  }
}
