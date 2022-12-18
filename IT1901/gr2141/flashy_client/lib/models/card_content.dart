import 'package:equatable/equatable.dart';

/// Converts card content in json to [lines].
class CardContent extends Equatable {
  final List<String> lines;

  CardContent({required this.lines}) : assert(lines.isNotEmpty);

  Map<String, dynamic> toJson() => {
        'lines': lines,
      };

  @override
  List<Object> get props => [lines];

  factory CardContent.fromJson(Map<String, dynamic> json) => CardContent(
        lines: (json['lines'] as List).map((line) => line as String).toList(),
      );

  factory CardContent.withText({required String text}) =>
      CardContent(lines: text.split('\n'));

  String get text => lines.join('\n');
}
