package app.events;

/**
 * Event signalizing that the editor wrap text function was toggled on or off
 */
public class ToggleWrapTextEvent extends Event {

  private boolean isWrapped;
  
  /**
   * Event signalizing that the editor wraptext function was toggled on or off
   * @param isWrapped Whether or not wraptext was turned on or off
   */
  public ToggleWrapTextEvent(boolean isWrapped) {
    this.isWrapped = isWrapped;
  }

  /**
   * @return Whether or not wraptext was turned on or off
   */
  public boolean getIsWrapped() {
    return this.isWrapped;
  }

}
