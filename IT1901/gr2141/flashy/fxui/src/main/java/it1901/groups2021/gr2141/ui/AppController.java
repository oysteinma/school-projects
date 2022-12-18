package it1901.groups2021.gr2141.ui;

import it1901.groups2021.gr2141.core.domainlogic.FlashcardProvider;
import it1901.groups2021.gr2141.ui.controllers.CardAreaController;
import it1901.groups2021.gr2141.ui.controllers.CardNavigationController;
import it1901.groups2021.gr2141.ui.controllers.Controller;
import it1901.groups2021.gr2141.ui.controllers.MenuController;
import it1901.groups2021.gr2141.ui.controllers.NavbarController;
import javafx.fxml.FXML;
/**
 * Root UI controller.
 */

public class AppController extends Controller  {

  @FXML
  private CardAreaController cardAreaController;

  @FXML
  private CardNavigationController cardNavigationController;

  @FXML
  private MenuController menuController;

  @FXML
  private NavbarController navbarController;


  /**
   * sets and returns controller for flashcard provider.
   * @see it1901.groups2021.gr2141.ui.controllers.Controller
   */
  public static Controller withFlashCardProvider(FlashcardProvider flashcardProvider) {
    AppController controller = new AppController();
    controller.setFlashCardProvider(flashcardProvider);
    return controller;
  }
}
