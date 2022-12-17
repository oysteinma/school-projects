import 'package:flutter/material.dart';
import 'package:flashy_client/models/flashcard.dart';
import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/widgets.dart';
import 'dart:math';

/// Creates a state for [_MemorizeCardsPageState].
class MemorizeCardsPage extends StatefulWidget {
  MemorizeCardsPage({
    Key? key,
    required this.flashcards,
    required this.isSelected,
  }) : super(key: key);

  final List<Flashcard> flashcards;
  final List<bool> isSelected;

  @override
  _MemorizeCardsPageState createState() => _MemorizeCardsPageState();
}

/// a [StatefulWidget] that displays card content and controls their behaviour-
class _MemorizeCardsPageState extends State<MemorizeCardsPage> {
  bool foran = true;
  int indeks = 0;
  late List<int> randomCardIndices;

  final CarouselController controller = CarouselController();

  bool get isRandomMode => widget.isSelected[0];
  bool get isFlippedMode => widget.isSelected[2];
  bool get showsFront => isFlippedMode ^ foran;

  /// A Collection of random numbers that decides next card.
  /// for when [isRandomMode] is toggled on
  static Iterable<int> randomCardNumbers(flashcards) sync* {
    int i = 0;
    int k = 0;
    Random r = Random();
    while (true) {
      k = i;
      i = r.nextInt(flashcards.length - 1);
      if (i >= k) {
        i++;
      }
      yield i;
    }
  }

  @override
  void initState() {
    randomCardIndices = randomCardNumbers(widget.flashcards).take(widget.flashcards.length).toList();
    super.initState();
  }

  /// Builds the Card area
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          Flexible(
            /// [CarouselSlider] is a imported controller that detects drag motions.
            /// And allows simple controlling.
            child: CarouselSlider.builder(
              carouselController: controller,
              itemCount: widget.flashcards.length,
              options: CarouselOptions(
                enableInfiniteScroll: widget.isSelected[1],
                onPageChanged: (i, _) => setState(() {
                  indeks = i;
                  foran = true;
                }),
              ),
              itemBuilder: (context, index, _) {
                return GestureDetector(
                  onTap: () {
                    setState(() {
                      foran = !foran;
                    });
                  },
                  child: Container(
                    key: ValueKey('cardArea'),
                    height: 300,
                    child: Center(
                      /// Display correct text dependent on [isRandomMode].
                      child: Text(
                        isRandomMode
                            ? (showsFront
                                ? widget.flashcards[randomCardIndices[index]].front.text
                                : widget.flashcards[randomCardIndices[index]].back.text)
                            : (showsFront
                                ? widget.flashcards[index].front.text
                                : widget.flashcards[index].back.text),
                        style: TextStyle(
                          color: Colors.white,
                          fontSize: 18,
                        ),
                      ),
                    ),
                    padding: EdgeInsets.all(20.0),
                    margin: EdgeInsets.all(20.0),
                    decoration: BoxDecoration(
                      color: Colors.purple[300],
                      borderRadius: BorderRadius.circular(10),
                    ),
                  ),
                );
              },
            ),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 10),
            child: Row(
              key: ValueKey('cardNavigation'),
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                    ElevatedButton(
                      onPressed: () {
                        controller.previousPage(
                            duration: Duration(milliseconds: 300),
                            curve: Curves.linear);
                      },
                      child: Text('<-'),
                    ),
                  ] +

                  /// a navigation bar that displays current card + next and last two cards
                  /// with two buttons for previous/next card
                  Iterable<int>.generate(widget.flashcards.length)
                      .where(
                          (i) => (i - indeks).abs() < (isRandomMode ? 1 : 3))
                      .map(
                        (int flashcardIndex) => Flexible(
                          child: ElevatedButton(
                            onPressed: () =>
                                controller.animateToPage(flashcardIndex),
                            child: Text(
                              '${(isRandomMode ? randomCardIndices[flashcardIndex] : flashcardIndex) + 1}',
                            ),
                          ),
                        ),
                      )
                      .toList() +
                  <Widget>[
                    ElevatedButton(
                      onPressed: () {
                        controller.nextPage(
                            duration: Duration(milliseconds: 300),
                            curve: Curves.linear);
                      },
                      child: Text('->'),
                    )
                  ],
            ),
          )
        ],
      ),
    );
  }
}
