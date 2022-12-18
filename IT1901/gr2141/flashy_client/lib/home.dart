import 'package:flashy_client/models/card_deck.dart';
import 'package:flashy_client/pages/edit_connection.dart';
import 'package:flashy_client/remote/remote_storage.dart';
import 'package:flutter/material.dart';
import 'package:flashy_client/pages/edit_cards.dart';
import 'package:flashy_client/pages/memorize_cards.dart';
import 'package:flashy_client/models/flashcard.dart';
import 'package:shared_preferences/shared_preferences.dart';

import 'components/appbars/memorization_appbar.dart';

/// Creates a state for [HomeState].
class Home extends StatefulWidget {
  SharedPreferences? prefs;

  Home({
    this.prefs,
    Key? key,
  }) : super(key: key);

  @override
  HomeState createState() => HomeState();
}

/// [StatefulWidget] Home, rebuilds when [setState] is called.
class HomeState extends State<Home> {
  int currentIndex = 0;
  List<Flashcard> flashCardList = flashcards;
  List<bool> isSelected = [false, false, false];
  String localIp = '';
  String localPort = '';

  String get ip => widget.prefs?.getString('ip') ?? localIp;
  String get port => widget.prefs?.getString('port') ?? localPort;

  set ip(String ip) =>
      (widget.prefs != null) ? widget.prefs!.setString('ip', ip) : localIp = ip;
  set port(String port) => (widget.prefs != null)
      ? widget.prefs!.setString('port', port)
      : localPort = port;

  @override
  void initState() {
    if (widget.prefs == null) {
      debugPrint('Application launched without loading preferences');
    } else {
      getCardsFromServer();
    }

    super.initState();
  }

  /// Reads deck from server.
  void getCardsFromServer() async {
    CardDeck deck;
    try {
      deck = await RemoteStorage.readDeck(
        baseApiUri: Uri.http('$ip:$port', ''),
        id: 0,
      );
    } catch (_) {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(
          content: Text(
              'Could not read data from storage. Are you connected properly?')));
      return;
    }

    /// Updates deck of flashcards.
    setState(() {
      flashCardList = deck.flashcards;
    });
  }

  /// Updates cards in deck.
  void updateCards(List<Flashcard> cards) async {
    setState(() {
      flashCardList = cards;
    });

    bool wasWritten = await RemoteStorage.writeDeck(
      deck: CardDeck(flashcards: cards),
      baseApiUri: Uri.http('$ip:$port', ''),
      id: 0,
    );

    if (!wasWritten) {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(
          content: Text(
              'Could not write data to storage. Are you connected properly?')));
    }
  }

  /// Builds Page.
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      appBar: pages[currentIndex].appBar,
      body: pages[currentIndex].body,
      bottomNavigationBar: BottomNavigationBar(
          backgroundColor: Colors.purple,
          currentIndex: currentIndex,
          selectedItemColor: Colors.white,
          onTap: (index) => setState(() => currentIndex = index),
          items: pages.map((p) => p.navigationItem).toList()),
    );
  }

  /// Sets a template for which widgets to load in [Page].
  List<Page> get pages => [
        Page(
          appBar: MemorizationPageNavBar(
            onModeChanged: (newIsSelected) {
              setState(() {
                isSelected = newIsSelected;
              });
            },
            isSelected: isSelected,
          ),
          body: MemorizeCardsPage(
            flashcards: flashCardList,
            isSelected: isSelected,
          ),
          navigationItem: BottomNavigationBarItem(
            icon: Icon(
              Icons.home,
              key: ValueKey('homeIcon'),
            ),
            label: 'Home',
          ),
        ),
        Page(
          appBar: AppBar(title: Text('Edit Cards')),
          body: EditCardsPage(
            flashcards: flashCardList,
            updateCards: updateCards,
          ),
          navigationItem: BottomNavigationBarItem(
            icon: Icon(
              Icons.edit,
              key: ValueKey('editIcon'),
            ),
            label: 'Edit',
          ),
        ),
        Page(
          appBar: AppBar(title: Text('Edit Connection Details')),
          body: EditConnectionPage(
            ip: ip,
            port: port,
            onChanged: (ip, port) {
              setState(() {
                this.ip = ip;
                this.port = port;
              });

              getCardsFromServer();
            },
          ),
          navigationItem: BottomNavigationBarItem(
            icon: Icon(Icons.swap_vert),
            label: 'Connection',
          ),
        )
      ];
}

/// Specifies widgets in [Page].
class Page {
  final PreferredSizeWidget appBar;
  final Widget body;
  final BottomNavigationBarItem navigationItem;

  const Page({
    required this.appBar,
    required this.body,
    required this.navigationItem,
  });
}

/// List of flashcards.
List<Flashcard> flashcards = [
  Flashcard.withText(
    front: '1',
    back: '1.2',
  ),
  Flashcard.withText(
    front: '2',
    back: '2.2',
  ),
  Flashcard.withText(
    front: '3',
    back: '3.2',
  ),
  Flashcard.withText(
    front: '4',
    back: '4.2',
  ),
  Flashcard.withText(
    front: '5',
    back: '5.2',
  ),
  Flashcard.withText(
    front: '6',
    back: '6.2',
  ),
  Flashcard.withText(
    front: '7',
    back: '7.2',
  ),
  Flashcard.withText(
    front: '8',
    back: '8.2',
  ),
];
