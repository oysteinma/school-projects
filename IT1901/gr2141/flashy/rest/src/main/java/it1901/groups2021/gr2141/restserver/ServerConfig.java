package it1901.groups2021.gr2141.restserver;

import it1901.groups2021.gr2141.core.storage.LocalCardDeckStorage;
import it1901.groups2021.gr2141.restapi.FlashyModelService;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Configuration for the REST server.
 * Registers all middleware and API modules for the server to use.
 */
public class ServerConfig extends ResourceConfig {

  private LocalCardDeckStorage localCardDeckStorage;

  /**
   * Configures servers.
   */
  public ServerConfig(LocalCardDeckStorage localCardDeckStorage) {
    setLocalCardDeckStorage(localCardDeckStorage);
    register(JacksonFeature.class);
    register(JsonMiddleware.class);
    register(FlashyModelService.class);
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(ServerConfig.this.localCardDeckStorage);
      }
    });
  }

  protected LocalCardDeckStorage getLocalCardDeckStorage() {
    return this.localCardDeckStorage;
  }

  protected void setLocalCardDeckStorage(LocalCardDeckStorage localCardDeckStorage) {
    this.localCardDeckStorage = localCardDeckStorage;
  }
}
