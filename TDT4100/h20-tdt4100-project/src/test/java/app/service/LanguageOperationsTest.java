package app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.Collections;

import org.fxmisc.richtext.model.StyleSpan;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import app.model.languages.Java;

public class LanguageOperationsTest {

  private String javaCode =
"""
class TestClass {
  // This is a comment
  private int myInt = 1;

  /*
  * This is a longer comment
  */
  public int getMyInt() {
    return this.myInt;
  } 

  public static void main(String[] args) {
    TestClass class = new TestClass();
    System.out.println(class.getMyInt());
    System.out.println(\"This is a string\");
    System.out.println(true);
  }
}
""";

  private StyleSpan<Collection<String>> s(String style, int length) {
    return new StyleSpan<Collection<String>>(Collections.singleton(style), length);
  }

  private StyleSpan<Collection<String>> s(int length) {
    return new StyleSpan<Collection<String>>(Collections.emptyList(), length);
  }

  private StyleSpans<Collection<String>> javaStyle =
    new StyleSpansBuilder<Collection<String>>()
      .add(s("keyword", 5))
      .add(s(1))
      .add(s("identifier", 9))
      .add(s(1))
      .add(s("curlyBrackets", 1))
      .add(s(3))
      .add(s("comment", 20))
      .add(s(3))
      .add(s("keyword", 7))
      .add(s(1))
      .add(s("keyword", 3))
      .add(s(10))
      .add(s("semicolon", 1))
      .add(s(4))
      .add(s("comment", 36))
      .add(s(3))
      .add(s("keyword", 6))
      .add(s(1))
      .add(s("keyword", 3))
      .add(s(1))
      .add(s("method", 8))
      .add(s("paranthesis", 2))
      .add(s(1))
      .add(s("curlyBrackets", 1))
      .add(s(5))
      .add(s("keyword", 6))
      .add(s(1))
      .add(s("this", 4))
      .add(s("property", 6))
      .add(s("semicolon", 1))
      .add(s(3))
      .add(s("curlyBrackets", 1))
      .add(s(4))
      .add(s("keyword", 6))
      .add(s(1))
      .add(s("keyword", 6))
      .add(s(1))
      .add(s("keyword", 4))
      .add(s(1))
      .add(s("method", 4))
      .add(s("paranthesis", 1))
      .add(s("identifier", 6))
      .add(s("squareBrackets", 2))
      .add(s(5))
      .add(s("paranthesis", 1))
      .add(s(1))
      .add(s("curlyBrackets", 1))
      .add(s(5))
      .add(s("identifier", 9))
      .add(s(1))
      .add(s("keyword", 5))
      .add(s(3))
      .add(s("keyword", 3))
      .add(s(1))
      .add(s("method", 9))
      .add(s("paranthesis", 2))
      .add(s("semicolon", 1))
      .add(s(5))
      .add(s("identifier", 6))
      .add(s("property", 4))
      .add(s(1))
      .add(s("method", 7))
      .add(s("paranthesis", 1))
      .add(s("keyword", 5))
      .add(s(1))
      .add(s("method", 8))
      .add(s("paranthesis", 3))
      .add(s("semicolon", 1))
      .add(s(5))
      .add(s("identifier", 6))
      .add(s("property", 4))
      .add(s(1))
      .add(s("method", 7))
      .add(s("paranthesis", 1))
      .add(s("string", 18))
      .add(s("paranthesis", 1))
      .add(s("semicolon", 1))
      .add(s(5))
      .add(s("identifier", 6))
      .add(s("property", 4))
      .add(s(1))
      .add(s("method", 7))
      .add(s("paranthesis", 1))
      .add(s("true", 4))
      .add(s("paranthesis", 1))
      .add(s("semicolon", 1))
      .add(s(3))
      .add(s("curlyBrackets", 1))
      .add(s(1))
      .add(s("curlyBrackets", 1))
      .add(s(1))
      .create();

  @Test
  @DisplayName("test syntaxHighlight")
  public void testSyntaxHighlight() {
    StyleSpans<Collection<String>> highlightData =
      LanguageOperations.syntaxHighlight(javaCode, new Java());

    // highlightData.forEach(sp -> 
    //   System.out.println(
    //     sp.getStyle().isEmpty()
    //     ? String.format(".add(s(%d))", sp.getLength())
    //     : String.format(".add(s(\"%s\", %d))", sp.getStyle().toArray()[0], sp.getLength())
    //   )
    // );

    assertEquals(javaStyle, highlightData);
  }
  
}
