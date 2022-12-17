package app.service;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;

import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import app.model.ProgrammingLanguage;

/**
 * Common static operations that can be executed on any class
 * that implements {@link app.model.ProgrammingLanguage ProgrammingLanguage}
 */
public final class LanguageOperations {

  private LanguageOperations() {}
  
  /**
   * Use a matcher to find the styleclass of the next match
   * from several available styleclasses.
   * @param m The matcher
   * @param styleClasses Collection of styleclasses
   * @return The styleclass of the next match
   */
  private static String getMatch(Matcher m, Collection<String> styleClasses) {
    return styleClasses
      .stream()
      .filter(keyword -> m.group(keyword) != null)
      .findFirst()
      .orElse(""); // This is not possible, but is needed to convert Optional<String> to String
  }

  /**
   * Calculate syntax highlighting data for a code area
   * @param text The text to highlight
   * @param language The language object to use for highlighting
   * @return The syntax highlighting data
   * @see app.controllers.EditorController#setHighlighting(StyleSpans) EditorController.setHighlighting()
   */
  public static StyleSpans<Collection<String>> 
    syntaxHighlight(String text, ProgrammingLanguage language) {
    Matcher matcher = language.getPattern().matcher(text);
    StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

    int lastKwEnd = 0;
    while(matcher.find()) {
      String styleClass = getMatch(matcher, language.getPatternMap().values());

      spansBuilder.add(
        Collections.emptyList(),
        matcher.start() - lastKwEnd
      );
      spansBuilder.add(
        Collections.singleton(styleClass),
        matcher.end() - matcher.start()
      );

      lastKwEnd = matcher.end();
    }

    spansBuilder.add(
      Collections.emptyList(),
      text.length() - lastKwEnd
    );

    return spansBuilder.create();
  }

}
