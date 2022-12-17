package it1901.groups2021.gr2141.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CardContentTest {

  private List<String> exampleLines;
  private String exampleText;

  @BeforeEach
  public void setUpExampleData() {
    exampleLines = List.of("asdf", "fdsa", "asdf");
    exampleText = "asdf\nfdsa\nasdf";
  }
  
  @Test
  public void testNullInput() {
    String a = null;
    List<String> b = null;
    assertThrows(IllegalArgumentException.class, () -> new CardContent(a));
    assertThrows(IllegalArgumentException.class, () -> new CardContent(b));
  }

  @Test
  public void testEmptyListInput() {
    assertThrows(IllegalArgumentException.class, () -> new CardContent(""));
    assertThrows(IllegalArgumentException.class, () -> new CardContent(List.of()));
  }

  @Test
  public void testRealInput() {
    CardContent content = new CardContent(exampleLines);
    assertEquals(exampleLines, content.getLines());

    content = new CardContent(exampleText);
    assertEquals(exampleLines, content.getLines());
  }

  @Test
  public void testEquivalentInput() {
    var cc1 = new CardContent(exampleLines);
    var cc2 = new CardContent(exampleLines);
    assertEquals(cc1.getLines(), cc2.getLines());
  }

  @Test
  public void testEquality() {
    CardContent cc1 = new CardContent(exampleLines);
    CardContent cc2 = new CardContent(exampleLines);

    assertNotEquals(cc1, new Object());
    assertNotEquals(cc1.hashCode(), cc2.hashCode());
    assertEquals(cc1.getLines(), cc2.getLines());
  }

}
