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

// docker run -p 11222:11222 -e USER="foo" -e PASS="bar" infinispan/server

public class ServerVerticle extends AbstractVerticle {

  @Override
  public void start() {
    Router router = Router.router(vertx);

    // Create a clustered session store using defaults
    JsonObject options = new JsonObject()
      .put("servers", new JsonArray()
        .add(new JsonObject()
          .put("host", "localhost")
          .put("port", 11222)
          .put("username", "foo")
          .put("password", "bar"))
      );
    SessionStore store = InfinispanSessionStore.create(vertx, options);

    SessionHandler sessionHandler = SessionHandler.create(store);

    router.route().handler(sessionHandler);

    router.get("/").handler(ctx -> {
      Session session = ctx.session();
      session.computeIfAbsent("createdOn", s -> System.currentTimeMillis());
      String msg = "Session [" + session.id() + "] created on " + new Date(session.<Long>get("createdOn"));
      msg += "\n";
      msg += "\n";
      msg += "Page generated on " + new Date();
      ctx.end(msg);
    });

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080);
  }

  // tag::main[]
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx(); // <1>
    vertx.deployVerticle(new ServerVerticle())
      .onFailure(Throwable::printStackTrace); // <2>
  }
  // end::main[]
}
