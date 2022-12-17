/**
 * Module including everything relevant to the REST Server
 */
module it1901.groups2021.gr2141.rest {
  requires it1901.groups2021.gr2141.core;
  requires jakarta.ws.rs;

  requires jersey.common;
  requires jersey.server;
  requires jersey.media.json.jackson;

  requires org.glassfish.hk2.api;

  opens it1901.groups2021.gr2141.restapi to jersey.server;

  exports it1901.groups2021.gr2141.restserver;
}