package app.model.languages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import app.model.ProgrammingLanguage;

public class EmptyTest {

  private ProgrammingLanguage lang = new Empty();
  
  @Test
  @DisplayName("Test commentLine")
  public void testCommentLine() {
    String line = "test";

    assertEquals(line, lang.commentLine(line));
  }

  @Test
  @DisplayName("Test unCommentLine")
  public void testUnCommentLine() {
    String line = "test";

    assertEquals(line, lang.unCommentLine("test"));
  }

  @Test
  @DisplayName("Test combination of commentLine and unCommentLine")
  public void testCommentUnCommentLine() {
    String line = "test";
    String wrapped1 = lang.unCommentLine(lang.commentLine(line));
    String wrapped2 = lang.unCommentLine(lang.commentLine(wrapped1));

    assertEquals(line, wrapped1);
    assertEquals(line, wrapped2);
  }


  @Test
  @DisplayName("Test commentSelection")
  public void testCommentSelection() {
    String selection = "This\nis a multiline\nstring";

    assertEquals(selection, lang.commentSelection(selection));
    assertEquals(selection, lang.commentSelection(lang.commentSelection(selection)));
  }

  @Test
  @DisplayName("Test unCommentSelection")
  public void testUnCommentSelection() {
    String selection = "This\nis a multiline\nstring";

    assertEquals(selection, lang.unCommentSelection(selection));
    assertEquals(selection, lang.unCommentSelection(lang.unCommentSelection(selection)));
  }

  @Test
  @DisplayName("Test isCommentedLine")
  public void testIsCommentedLine() {
    String line = "test";

    assertFalse(lang.isCommentedLine(line));
  }

  @Test
  @DisplayName("Test isCommentedSelection")
  public void testIsCommentedSelection() {
    String selection = "test";

    assertFalse(lang.isCommentedSelection(selection));
  }
  
}
