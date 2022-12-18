package app.events;

/**
 * Event signalizing that the application is going to open a link in an external browser
 */
public class OpenLinkInBrowserEvent extends Event {

  private String link;

  /**
   * Event signalizing that the application is going to open a link in an external browser
   * @param link The link to open
   */
  public OpenLinkInBrowserEvent(String link) {
    this.link = link;
  }

  /**
   * @return The link to open
   */
  public String getLink() {
    return link;
  }
  
}
