package app.model.languages;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import app.model.ProgrammingLanguage;

public class Markdown implements ProgrammingLanguage {

  private String name = "Markdown";
  private static Map<Pattern, String> pattern;

  private static Entry<Pattern, String> e(String k, String v) {
    return new AbstractMap.SimpleEntry<>(Pattern.compile(k), v);
  }

  private static final List<Entry<Pattern, String>> patternList = 
    List.of(
      e("<!--(?:.|\n)*-->", "comment"),
      e("##### .*", "ssssheader"),
      e("#### .*", "sssheader"),
      e("### .*", "ssheader"),
      e("## .*", "sheader"),
      e("# .*", "header"),
      e("\\*\\*\\*.*\\*\\*\\*|___.*___", "emphasizedItalic"),
      e("\\*\\*(?!\\*).*\\*\\*|__(?!_).*__", "emphasized"),
      e("\\*(?!\\*).*\\*|_(?!_).*_", "italic"),
      e("~~.*~~", "strikethrough"),
      e("[\\-*+] .*", "listItem"),
      e("\\d+\\. .*", "numberedItem"),
      e("(?<!\\!)\\[.*\\][\\[\\()].*[\\)\\]]", "link"),
      e("!\\[.*\\][\\[\\()].*[\\)\\]]", "image"),
      e("\\[\\d+\\]: .*", "source")
    );


  public Markdown() {
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
    return Markdown.pattern;
  }

  public Pattern getPattern() {
    return Pattern.compile(
      Markdown.pattern
        .entrySet()
        .stream()
        .map(e -> String.format("(?<%s>%s)", e.getValue(), e.getKey()))
        .collect(Collectors.joining("|")));
  }

  // ----------------------------------------------------------------

  String cStart     = "<!-- ";
  String cEnd       = " -->";
  String cStartRegex = "<!-- ?";
  String cEndRegex   = " ?-->\\s{0,}$";

  public String commentLine(String line) {
    return  cStart + line + cEnd;
  }

  public String unCommentLine(String line) {
    return line
      .replaceFirst(cStartRegex, "")
      .replaceAll(cEndRegex, "");
  }

  public boolean isCommentedLine(String line) {
    return line.startsWith(cStart) && line.endsWith(cEnd);
  }


  public String commentSelection(String selection) {
    return
      cStart + selection + cEnd;
  }

  public String unCommentSelection(String selection) {
    String[] lines     = selection.split("\n");
    String   firstLine = lines[0];
    String   lastLine  = lines[lines.length - 1];

    String midLineString =
      lines.length < 3 ?
      "" :
      String.join("\n", Arrays.copyOfRange(lines, 1, lines.length - 1)) + "\n";

    return
      firstLine.replaceFirst(cStartRegex, "") + "\n"
      + midLineString
      + lastLine.replaceAll(cEndRegex, "");
  }

  public boolean isCommentedSelection(String selection) {
    return selection.startsWith(cStart) && selection.endsWith(cEnd);
  }

}
