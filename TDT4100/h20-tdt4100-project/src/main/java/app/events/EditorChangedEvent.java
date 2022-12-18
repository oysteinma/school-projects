package app.events;

/**
 * Event signalizing that the content or the cursor state in the editor has been modified
 */
public class EditorChangedEvent extends Event {
  
  private int line;
  private int column;

/**
 * Event signalizing that the content or the cursor state in the editor has been modified
 * @param line The line number of the cursor
 * @param column The column number of the cursor
 */
  public EditorChangedEvent(int line, int column) {
    this.line = line;
    this.column = column;
  }

  /**
   * @return The line number of the cursor
   */
  public int getLine() {
    return this.line;
  }

  /**
   * @return The column number of the cursor
   */
  public int getColumn() {
    return column;
  }
  
}
