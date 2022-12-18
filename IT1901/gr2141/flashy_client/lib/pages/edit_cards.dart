import 'package:flashy_client/models/card_content.dart';
import 'package:flutter/material.dart';
import 'dart:ui';

import 'package:flashy_client/models/flashcard.dart';

/// Creates state for [_EditCardsPageState].
class EditCardsPage extends StatefulWidget {
  final List<Flashcard> flashcards;
  final void Function(List<Flashcard>) updateCards;

  const EditCardsPage({
    required this.updateCards,
    required this.flashcards,
    Key? key,
  }) : super(key: key);

  @override
  State<EditCardsPage> createState() => _EditCardsPageState();
}

/// a [StatefulWidget] that handles adding, editing and deleting cards.
class _EditCardsPageState extends State<EditCardsPage> {
  final myControllerFront = TextEditingController();
  final myControllerBack = TextEditingController();

  /// Removes flashcard at index.
  void removeCard(int index) {
    widget.flashcards.removeAt(index);
    widget.updateCards(widget.flashcards);
    ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('FlashCard ${index + 1} was deleted!')));
  }

  /// Adds card with input text, either from edit or new card.
  void addCard() {
    setState(() {
      widget.flashcards.add(Flashcard.withText(
        front: myControllerFront.text,
        back: myControllerBack.text,
      ));
      widget.updateCards(widget.flashcards);
      myControllerFront.clear();
      myControllerBack.clear();
    });
    Navigator.of(context).pop();
  }

  // Updates flashcards.
  void updateCards() {
    setState(() {
      widget.updateCards(widget.flashcards);
    });
  }

  /// Display settings for a flashcards, deletes card on left swipe.
  Widget flashcardBuilder(BuildContext context, int index) {
    return Dismissible(
      direction: DismissDirection.endToStart,
      child: Column(
        children: [
          Text(
            'FlashCard ${index + 1}',
            style: const TextStyle(
              fontWeight: FontWeight.bold,
              color: Colors.black,
            ),
            key: ValueKey('cardIndex'),
          ),
          FlashcardUIElement(
            card: widget.flashcards[index],
            onEditingComplete: updateCards,
          ),
        ],
      ),
      key: ValueKey<FlashcardUIElement>(
        FlashcardUIElement(
          card: widget.flashcards[index],
          onEditingComplete: updateCards,
        ),
      ),
      background: Container(
        color: Colors.red,
        child: const Padding(
          padding: EdgeInsets.fromLTRB(320, 0, 0, 0),
          child: Icon(
            Icons.delete,
            color: Colors.white,
            size: 80.0,
            key: ValueKey('deleteIcon'),
          ),
        ),
      ),
      onDismissed: (_) => removeCard(index),
    );
  }

  /// Builds a [AlertDialog] when button [addCard] is pressed.
  Widget addCardDialog(context) {
    return AlertDialog(
      title: const Text('Add a new FlashCard'),
      content: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          TextField(
            controller: myControllerFront,
            style: TextStyle(),
            decoration: InputDecoration(
              label: Text('Term'),
            ),
          ),
          TextField(
            controller: myControllerBack,
            style: TextStyle(),
            decoration: InputDecoration(
              label: Text('Definition'),
            ),
          ),
        ],
      ),
      actions: <Widget>[
        TextButton(
          onPressed: addCard,
          child: const Text('Add FlashCard'),
          key: ValueKey('saveCard'),
        ),
        TextButton(
          key: ValueKey('cancelSaveCard'),
          onPressed: () {
            Navigator.of(context).pop();
          },
          child: const Text('Cancel'),
        ),
      ],
    );
  }

  /// Makes the page scrollable.
  /// and connects [addCardDialog] to button.
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: ListView.builder(
        shrinkWrap: true,
        itemCount: widget.flashcards.length,
        itemBuilder: flashcardBuilder,
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          showDialog(
            context: context,
            builder: addCardDialog,
          );
        },
        child: const Icon(
          Icons.add,
          key: ValueKey('addIcon'),
        ),
      ),
    );
  }
}

/// Creates a state for [_FlashcardUIElementState].
class FlashcardUIElement extends StatefulWidget {
  const FlashcardUIElement({
    required this.card,
    this.onEditingComplete,
    Key? key,
  }) : super(key: key);

  final Flashcard card;
  final void Function()? onEditingComplete;

  @override
  _FlashcardUIElementState createState() => _FlashcardUIElementState();
}

class _FlashcardUIElementState extends State<FlashcardUIElement> {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Container(
        padding: const EdgeInsets.fromLTRB(20.0, 5.0, 20.0, 20.0),
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(20),
          color: Colors.purple.shade300,
        ),
        margin: const EdgeInsets.all(10.0),
        child: Column(
          children: [
            CardContentField(
              key: ValueKey('cardContentFront'),
              side: widget.card.front.text,
              label: 'Term',
              onEditingComplete: (String newText) {
                widget.card.front = CardContent.withText(text: newText);
                if (widget.onEditingComplete != null) {
                  widget.onEditingComplete!();
                }
              },
            ),
            CardContentField(
              key: ValueKey('cardContetBack'),
              side: widget.card.back.text,
              label: 'Definition',
              onEditingComplete: (String newText) {
                widget.card.back = CardContent.withText(text: newText);
                if (widget.onEditingComplete != null) {
                  widget.onEditingComplete!();
                }
              },
            ),
          ],
        ),
      ),
    );
  }
}

/// creates state for [_CardContentState].
class CardContentField extends StatefulWidget {
  const CardContentField({
    Key? key,
    required this.side,
    this.onEditingComplete,
    this.label,
  }) : super(key: key);

  final String side;
  final String? label;
  final void Function(String)? onEditingComplete;

  @override
  _CardContentState createState() => _CardContentState();
}

/// updates [TextField] when user edits
class _CardContentState extends State<CardContentField> {
  TextEditingController controller = TextEditingController(
    text: '',
  );

  @override
  Widget build(BuildContext context) {
    controller.text = widget.side;
    return TextField(
      enabled: true,
      onEditingComplete: () {
        if (widget.onEditingComplete != null) {
          widget.onEditingComplete!(controller.text);
        }
      },
      controller: controller,
      style: const TextStyle(
        color: Colors.white,
      ),
      decoration: InputDecoration(
        contentPadding: const EdgeInsets.fromLTRB(0, 5, 0, 0),
        label: (widget.label != null) ? Text(widget.label!) : null,
        labelStyle: const TextStyle(
          color: Colors.black,
          fontWeight: FontWeight.bold,
        ),
      ),
    );
  }
}
