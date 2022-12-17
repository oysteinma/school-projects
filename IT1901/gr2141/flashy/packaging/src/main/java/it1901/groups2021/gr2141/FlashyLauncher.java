package it1901.groups2021.gr2141;

import it1901.groups2021.gr2141.restserver.Server;
import it1901.groups2021.gr2141.ui.Flashy;
import java.util.Arrays;
import java.util.List;

/**
 * A starting point of the application.
 */
public class FlashyLauncher {
  private static String helpMessage = String.join(System.getProperty("line.separator"),
      "Usage: flashy [--remote|--server [options]]",
      "",
      "Options:",
      "  -h, --help     Print this message",
      "  -r, --remote   Start the program in client mode",
      "  -s, --server   Start the server",
      "",
      "Remote/Server Mode Options:",
      "  -a, --address <address>   Set the server IP address",
      "  -p, --port <port>         Set the server port"
  );

  private static boolean argsContains(List<String> argsList, String...args) {
    return Arrays.asList(args).stream().anyMatch(arg -> argsList.contains(arg));
  }

  /**
   * Should be used as the starting point when the application is packaged into a JAR file.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    List<String> argsList = Arrays.asList(args);
    if (argsContains(argsList, "-h", "--help")) {
      System.out.println(helpMessage);
    } else if (argsContains(argsList, "-s", "--server")) {
      Server.main(args);
    } else {
      Flashy.main(args);
    }
  }
}
