import 'package:equatable/equatable.dart';

import 'card_content.dart';

/// Gets and converts content of front/back to [front] and [back].
class Flashcard extends Equatable {
  CardContent front;
  CardContent back;

  Flashcard({
    required this.front,
    required this.back,
  });

  @override
  List<Object> get props => [front, back];

  Map<String, dynamic> toJson() => {
        'front': front,
        'back': back,
      };

  factory Flashcard.fromJson(Map<String, dynamic> json) => Flashcard(
        front: CardContent.fromJson(json['front']),
        back: CardContent.fromJson(json['back']),
      );

  factory Flashcard.withText({
    required String front,
    required String back,
  }) =>
      Flashcard(
        front: CardContent.withText(text: front),
        back: CardContent.withText(text: back),
      );
}
