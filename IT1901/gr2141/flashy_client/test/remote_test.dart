import 'dart:convert';

import 'package:flashy_client/models/card_content.dart';
import 'package:flashy_client/models/card_deck.dart';
import 'package:flashy_client/models/flashcard.dart';
import 'package:flashy_client/remote/remote_storage.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:dio/dio.dart';
import 'package:http_mock_adapter/http_mock_adapter.dart';
import 'package:flutter_driver/driver_extension.dart';

late List<String> cardContent;
late CardContent cc1;
late CardContent cc2;
late Flashcard fc1;
late Flashcard fc2;
late List<Flashcard> listFlashcards;
late CardDeck testCardDeck;

void main() {
  enableFlutterDriverExtension();
  final dio = Dio(BaseOptions());
  final dioAdapter = DioAdapter(dio: dio);
  const String serverAddress = '10.0.2.2:8090';

  final cardContent = ['front', 'back'];
  final cc1 = CardContent(lines: cardContent);
  final cc2 = CardContent(lines: cardContent);
  final fc1 = Flashcard(front: cc1, back: cc2);
  final fc2 = Flashcard(front: cc1, back: cc2);
  final listFlashcards = [fc1, fc2];
  final testCardDeck = CardDeck(flashcards: listFlashcards);
  final testCardDecks = [
    testCardDeck,
    testCardDeck
      ..flashcards.add(Flashcard.withText(front: 'Hi', back: 'Hello')),
  ];

  test('Test reading a single CardDeck', () async {
    dioAdapter.onGet(
      'http://$serverAddress/flashy/deck/0',
      (server) => server.reply(200, jsonEncode(testCardDeck)),
    );

    CardDeck cardDeck = await RemoteStorage.readDeck(
      baseApiUri: Uri.http(serverAddress, ''),
      id: 0,
      http_client: dioAdapter.dio,
    );

    expect(cardDeck, testCardDeck);
  });

  test('Test reading several CardDecks', () async {
    dioAdapter.onGet(
      'http://$serverAddress/flashy/deck',
      (server) => server.reply(200, jsonEncode(testCardDecks)),
    );

    List<CardDeck> cardDecks = await RemoteStorage.readAllDecks(
      baseApiUri: Uri.http(serverAddress, ''),
      http_client: dioAdapter.dio,
    );

    expect(cardDecks, testCardDecks);
  });

  test('Test writing a CardDeck', () async {
    dioAdapter.onPost(
      'http://$serverAddress/flashy/deck/0',
      (server) => server.reply(200, true),
      data: testCardDeck,
    );

    bool didWrite = await RemoteStorage.writeDeck(
      deck: testCardDeck,
      id: 0,
      baseApiUri: Uri.http(serverAddress, ''),
      http_client: dioAdapter.dio,
    );

    expect(didWrite, true);
  });

  test('Test adding a new CardDeck', () async {
    dioAdapter.onPut(
      'http://$serverAddress/flashy/deck/',
      (server) => server.reply(200, true),
      data: testCardDeck
    );

    bool didWrite = await RemoteStorage.writeDeck(
      deck: testCardDeck,
      baseApiUri: Uri.http(serverAddress, ''),
      http_client: dioAdapter.dio,
    );

    expect(didWrite, true);
  });
}
