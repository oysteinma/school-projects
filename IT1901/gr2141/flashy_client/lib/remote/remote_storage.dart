import 'dart:convert';

import 'package:flashy_client/models/card_deck.dart';
import 'package:flutter/foundation.dart';
import 'package:dio/dio.dart';

class RemoteStorage {
  static Future<bool> writeDeck({
    required CardDeck deck,
    required Uri baseApiUri,
    int? id,
    Dio? http_client,
  }) async {
    var uri = baseApiUri.resolve('/flashy/deck/');
    Response<dynamic> response;
    http_client ??= Dio();

    try {
      if (id == null) {
        debugPrint('[PUT] $uri');
        response = await http_client.put(
          uri.toString(),
          data: deck,
        );
      } else {
        uri = uri.resolve('$id');
        debugPrint('[POST] $uri');
        response = await http_client.post(
          uri.toString(),
          data: deck,
        );
      }
    } catch (e) {
      debugPrint('[ERROR] Did not recieve the data properly.\n' + e.toString());
      return false;
    }

    return response.statusCode == 200;
  }

  /// Read deck and store in server.
  static Future<CardDeck> readDeck({
    required int id,
    required Uri baseApiUri,
    Dio? http_client,
  }) async {
    var uri = baseApiUri.resolve('/flashy/deck/$id');
    http_client ??= Dio();

    debugPrint('[GET] $uri');
    var response = await http_client.get(uri.toString());

    return CardDeck.fromJson(jsonDecode(response.data));
  }

  /// Reads all decks and stores in server.
  static Future<List<CardDeck>> readAllDecks({
    required Uri baseApiUri,
    Dio? http_client,
  }) async {
    var uri = baseApiUri.resolve('/flashy/deck');
    http_client ??= Dio();

    debugPrint('[GET] $uri');
    var response = await http_client.get(uri.toString());

    return (jsonDecode(response.data) as List)
        .map((cardDeck) => CardDeck.fromJson(cardDeck))
        .toList();
  }
}
