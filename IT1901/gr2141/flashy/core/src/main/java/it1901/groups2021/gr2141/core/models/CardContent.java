package it1901.groups2021.gr2141.core.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Data model representing the content inside a {@link Flashcard Flashcard}.
 * Usually consist of one or more @param lines of text.
 */
public class CardContent {
  private List<String> lines;

  /**
   * Create a new instance with lines of text.
   * @param lines The lines of text.
   */
  public CardContent(List<String> lines) {
    this.setLines(lines);
  }

  /**
   * Create a new instance with lines of text.
   * @param text The lines of text.
   */
  public CardContent(String text) {
    if (text == null || text.isEmpty()) {
      throw new IllegalArgumentException("Lines can not be empty");
    }
    this.setLines(Arrays.asList(text.split("\n")));
  }

  /**
   * sets lines and throws exception if they are null.
   * @param lines a list of strings which makes up the content of the card.
   */
  public void setLines(List<String> lines) {
    if (lines == null || lines.size() == 0) {
      throw new IllegalArgumentException("Lines can not be null");
    }
    this.lines = lines;
  }

  /**
   * A getter for the card content.
   * @return A list of strings which makes up the content of the card.
   */
  public List<String> getLines() {
    List<String> realLines = new ArrayList<String>(lines);
    return realLines;
  }

  /**
   * formats all lines to a string.
   * @return string
   */
  public String getText() {
    return String.join("\n", lines);
  }
}
