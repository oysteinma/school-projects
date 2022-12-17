import 'package:equatable/equatable.dart';
import 'package:flashy_client/models/flashcard.dart';

/// Converts flashcards in json to a [flashcards].
class CardDeck extends Equatable {
  List<Flashcard> flashcards;

  CardDeck({
    this.flashcards = const [],
  });

  @override
  List<Object> get props => [flashcards];

  Map<String, dynamic> toJson() => {'flashcards': flashcards};

  factory CardDeck.fromJson(Map<String, dynamic> json) => CardDeck(
        flashcards: (json['flashcards'] as List)
            .map((flashcard) => Flashcard.fromJson(flashcard))
            .toList(),
      );
}
