package app.model.languages;

import java.util.regex.Pattern;
import java.util.Map;

import app.model.ProgrammingLanguage;

public class Empty implements ProgrammingLanguage {
  
  private String name = "?";

  public String getName() {
    return this.name;
  }


  public Map<Pattern, String> getPatternMap() {
    return Map.of();
  }

  public Pattern getPattern() {
    return Pattern.compile("");
  }

  // ----------------------------------------------------------------

  public String commentLine(String line) {
    return line;
  }

  public String unCommentLine(String line) {
    return line;
  }

  public boolean isCommentedLine(String line) {
    return false;
  }

  public String commentSelection(String selection) {
    return selection;
  }

  public String unCommentSelection(String selection) {
    return selection;
  }

  public boolean isCommentedSelection(String selection) {
    return false;
  }
}
