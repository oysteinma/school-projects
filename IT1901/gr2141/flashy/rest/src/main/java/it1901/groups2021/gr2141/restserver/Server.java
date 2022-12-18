package it1901.groups2021.gr2141.restserver;

import it1901.groups2021.gr2141.core.storage.LocalCardDeckStorage;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * The main class of the REST server.
 */
public class Server {

  private static String address = "localhost";
  private static String port = "8080";
  public static final LocalCardDeckStorage localCardDeckStorage = new LocalCardDeckStorage();

  private static void parseArgs(List<String> args) {
    if (args.contains("-a")) {
      address = args.get(args.indexOf("-a") + 1);
    }
    if (args.contains("--address")) {
      address = args.get(args.indexOf("--address") + 1);
    }

    if (args.contains("-p")) {
      port = args.get(args.indexOf("-p") + 1);
    }
    if (args.contains("--port")) {
      port = args.get(args.indexOf("-port") + 1);
    }
  }

  private static URI makeUri() {
    return URI.create(String.format("http://%s:%s/", address, port));
  }

  /**
   * Prepares server.
   */
  public static HttpServer startServer() {

    final ResourceConfig rc = new ServerConfig(localCardDeckStorage);

    return GrizzlyHttpServerFactory.createHttpServer(makeUri(), rc);
  }

  /**
   * Launch the server.
   */
  public static void main(String[] args) {
    parseArgs(Arrays.asList(args));

    System.out.println("Starting REST server started with endpoints available at "
        + makeUri() + " ...");
    final HttpServer server = startServer();

    try {
      System.out.println("Hit Ctrl-C to stop it...");
      System.in.read();

    } catch (IOException e) {
      System.err.println(e);
    } finally {
      server.shutdownNow();
    }
  }

}