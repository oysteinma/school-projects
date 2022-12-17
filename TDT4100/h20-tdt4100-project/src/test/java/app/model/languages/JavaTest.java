package app.model.languages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import app.model.ProgrammingLanguage;

public class JavaTest {

  private ProgrammingLanguage lang = new Java();
  
  @Test
  @DisplayName("Test commentLine")
  public void testCommentLine() {
    String line = "test";

    assertEquals("// test", lang.commentLine(line));
    assertEquals("// // test", lang.commentLine(lang.commentLine(line)));
  }

  @Test
  @DisplayName("Test unCommentLine")
  public void testUnCommentLine() {
    String line = "// // test";

    assertEquals("// test", lang.unCommentLine(line));
    assertEquals("test", lang.unCommentLine(lang.unCommentLine(line)));
    assertEquals("test", lang.unCommentLine("test"));
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

    assertEquals("/* \n * This\n * is a multiline\n * string\n */", lang.commentSelection(selection));
    assertEquals(
      "/* \n * /* \n *  * This\n *  * is a multiline\n *  * string\n *  */\n */",
      lang.commentSelection(lang.commentSelection(selection))
    );
  }

  @Test
  @DisplayName("Test unCommentSelection")
  public void testUnCommentSelection() {
    String selection =  "/* \n * /* \n *  * This\n *  * is a multiline\n *  * string\n *  */\n */";

    assertEquals("/* \n * This\n * is a multiline\n * string\n */", lang.unCommentSelection(selection));
    assertEquals("This\nis a multiline\nstring", lang.unCommentSelection(lang.unCommentSelection(selection)));
    assertEquals("This\nis a multiline\nstring", lang.unCommentLine("This\nis a multiline\nstring"));
  }

  @Test
  @DisplayName("Test isCommentedLine")
  public void testIsCommentedLine() {
    String commentedLine = "// test";
    String unCommentedLine = "test";

    assertTrue(lang.isCommentedLine(commentedLine));
    assertFalse(lang.isCommentedLine(unCommentedLine));
  }

  @Test
  @DisplayName("Test isCommentedSelection")
  public void testIsCommentedSelection() {
    String commentedSelection = "/* \n * test\n */";
    String halfwayCommentedSelection = "/* \n test\n */";
    String unCommentedSelection = "test";

    assertTrue(lang.isCommentedSelection(commentedSelection));
    assertFalse(lang.isCommentedSelection(halfwayCommentedSelection));
    assertFalse(lang.isCommentedSelection(unCommentedSelection));
  }

}
