// ignore_for_file: unnecessary_string_escapes

import 'dart:convert';

import 'package:flashy_client/models/card_content.dart';
import 'package:flashy_client/models/card_deck.dart';
import 'package:flashy_client/models/flashcard.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  CardContent exampleCardContent = CardContent.withText(text: 'A cow');
  Flashcard exampleFlashcard = Flashcard(
      front: exampleCardContent,
      back: CardContent.withText(text: 'Nǎiniú\n奶牛'));
  CardDeck exampleCardDeck = CardDeck(flashcards: [exampleFlashcard]);

  String exampleSerializedCardContent = '{"lines":["A cow"]}';
  String exampleSerializedFlashcard =
      '{"front":$exampleSerializedCardContent,"back":{"lines":["Nǎiniú","奶牛"]}}';
  String exampleSerializedCardDeck =
      '{"flashcards":[$exampleSerializedFlashcard]}';

  test('Test serialization', () {
    expect(exampleSerializedCardContent, jsonEncode(exampleCardContent));
    expect(exampleSerializedFlashcard, jsonEncode(exampleFlashcard));
    expect(exampleSerializedCardDeck, jsonEncode(exampleCardDeck));
  });

  test('Test deserialization', () {
    expect(exampleCardContent, CardContent.fromJson(jsonDecode(exampleSerializedCardContent)));
    expect(exampleFlashcard, Flashcard.fromJson(jsonDecode(exampleSerializedFlashcard)));
    expect(exampleCardDeck, CardDeck.fromJson(jsonDecode(exampleSerializedCardDeck)));
  });

  test('Test de/serialization equality', () {
    expect(exampleCardContent, CardContent.fromJson(jsonDecode(jsonEncode(exampleCardContent))));
    expect(exampleFlashcard, Flashcard.fromJson(jsonDecode(jsonEncode(exampleFlashcard))));
    expect(exampleCardDeck, CardDeck.fromJson(jsonDecode(jsonEncode(exampleCardDeck))));
  });
}
