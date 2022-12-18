package app.controllers;

import com.google.common.eventbus.EventBus;

/**
 * Interface describing a JavaFX controller that contains an EventBus
 */
public interface Controller {  
  /**
   * Registers the main EventBus into the controller.
   * @param eventBus
   */
  public void setEventBus(EventBus eventBus);
}
