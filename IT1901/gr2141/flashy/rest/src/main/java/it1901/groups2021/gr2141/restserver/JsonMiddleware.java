package it1901.groups2021.gr2141.restserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import it1901.groups2021.gr2141.core.models.serializers.ModelSerializingModule;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

/**
 * A middleware module which provides serialization capabilities for.
 * all dataclasses in {@link it1901.groups2021.gr2141.core core}
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JsonMiddleware  implements ContextResolver<ObjectMapper> {

  private final ObjectMapper objectMapper;

  /**
   * Makes an object mapper for server.
   * @see JsonMiddleware
   */
  public JsonMiddleware() {
    objectMapper = ModelSerializingModule.buildMapper();
  }

  @Override
  public ObjectMapper getContext(final Class<?> type) {
    return objectMapper;
  }
}
