package app.model;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * An interface describing functions required for a class to
 * provide language specific details and functionality.
 */
public interface ProgrammingLanguage {
  /**
   * @return The name of the programming language
   */
  public String getName();
  
  /**
   * @return The map containing the regexes and corresponding style-classes to be used for syntax highlighting
   */
  public Map<Pattern,String> getPatternMap();

  /**
   * @return A combined regex for syntax highlighting
   */
  public Pattern getPattern();

  /**
   * Comment out a line
   * @param line The text of the line to comment out
   * @return The commented line
   */
  public String commentLine(String line);

  /**
   * Uncomment a line
   * @param line The text of the line to uncomment
   * @return The uncommented line
   */
  public String unCommentLine(String line);

  /**
   * @param line The text of the line
   * @return Whether or not a line is commented
   */
  public boolean isCommentedLine(String line);

  /**
   * Comment out an area of text
   * @param selection The text of the area to comment out
   * @return The commented area
   */
  public String commentSelection(String selection);

  /**
   * Uncomment an area of text
   * @param selection The text of the area to uncomment
   * @return The uncommented area
   */
  public String unCommentSelection(String selection);

  /**
   * @param selection The content of the area
   * @return Whether or not an area of text is commented
   */
  public boolean isCommentedSelection(String selection);

}
