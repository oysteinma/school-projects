package app.model.languages;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import app.model.ProgrammingLanguage;

public class Java implements ProgrammingLanguage {

  private String name = "Java";
  private static Map<Pattern, String> pattern;

  private static final String[] keywords = new String[] {
    "abstract", "assert", "boolean", "break", "byte",
    "case", "catch", "char", "class", "const",
    "continue", "default", "do", "double", "else",
    "enum", "extends", "final", "finally", "float",
    "for", "goto", "if", "implements", "import",
    "instanceof", "int", "interface", "long", "native",
    "new", "package", "private", "protected", "public",
    "return", "short", "static", "strictfp", "super",
    "switch", "synchronized", "throw", "throws",
    "transient", "try", "void", "volatile", "while"
  };

  private static Entry<Pattern, String> e(String k, String v) {
    return new SimpleEntry<>(Pattern.compile(k), v);
  }


  private static final List<Entry<Pattern, String>> patternList =
    List.of(
      e("\"([^\"\\\\]|\\\\.)*\"",       "string"),
      e("\\bthis\\b",                   "this"),
      e("\\btrue\\b",                   "true"),
      e("\\bfalse\\b",                  "false"),
      e("(?<=\\.?)\\w+(?=\\()",         "method"),
      e("\\(|\\)",                      "paranthesis"),
      e("\\{|\\}",                      "curlyBrackets"),
      e("\\[|\\]",                      "squareBrackets"),
      e(";",                            "semicolon"),
      e("\\.\\w+\\b(?!\\()",            "property"),
      e("\\b[A-Z]\\w+\\b",              "identifier"),
      e("\\b(" + String.join("|", keywords) + ")\\b",
                                        "keyword"),
      e("(?://.*)|/\\*(?:\\n|.)*?\\*/", "comment")
    );
  
  public Java() {
    this.initializePatternMap();
  }

  private void initializePatternMap() {
    pattern = new LinkedHashMap<>();
    patternList
      .forEach(e -> pattern.put(e.getKey(), e.getValue()));
  }

  public String getName() {
    return this.name;
  }

  public Map<Pattern, String> getPatternMap() {

    return Java.pattern;
  }

  public Pattern getPattern() {
    return Pattern.compile(
      Java.pattern
        .entrySet()
        .stream()
        .map(e -> String.format("(?<%s>%s)", e.getValue(), e.getKey()))
        .collect(Collectors.joining("|")));
  }

  // ----------------------------------------------------------------

  public String commentLine(String line) {
    return "// " + line;
  }

  public String unCommentLine(String line) {
    return line.replaceFirst("// ?", "");
  }

  public boolean isCommentedLine(String line) {
    return line.matches("// ?.*");
  }


  public String commentSelection(String selection) {
    return
      "/* \n" +
      selection.lines()
        .map(l -> " * " + l)
        .collect(Collectors.joining("\n"))
      + "\n */";
  }

  public String unCommentSelection(String selection) {
    String[] rawLines = selection.split("\n");
    String[] lines = Arrays.copyOfRange(rawLines, 1, rawLines.length - 1);

    return Arrays
      .stream(lines)
      .map(l -> l.replaceFirst("^ \\* ", ""))
      .collect(Collectors.joining("\n"));
  }

  public boolean isCommentedSelection(String selection) {
    var lines = selection.split("\n");

    boolean midPartStartsWithStar =
      lines.length < 2 ||
      Arrays.asList(Arrays.copyOfRange(lines, 1, lines.length - 1))
        .stream()
        .allMatch(l -> l.startsWith(" * "));

    return 
      Stream.of(
        selection.startsWith("/*"),
        midPartStartsWithStar,
        selection.endsWith(" */")
      ).allMatch(b -> b);
  }

}

