import 'package:flashy_client/pages/edit_cards.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flashy_client/home.dart';

void main() {
  testWidgets(
    'Test delete card swipe works',
    (WidgetTester tester) async {
      await tester.pumpWidget(MaterialApp(home: Home()));
      await tester.tap(find.byIcon(Icons.edit));

      final beforeDeleteIndex = flashcards.length;
      await tester.pumpAndSettle();
      int index = 0;
      await tester.drag(
          find.text('FlashCard ${index + 1}'), const Offset(-1000, 0.0));
      await tester.pumpAndSettle();
      final afterDeleteIndex = flashcards.length;
      expect(afterDeleteIndex, beforeDeleteIndex - 1);
    },
  );

  testWidgets(
    'Test add card button works',
    (WidgetTester tester) async {
      await tester.pumpWidget(MaterialApp(home: Home()));
      await tester.tap(find.byIcon(Icons.edit));
      await tester.pumpAndSettle();

      await tester.tap(find.byType(FloatingActionButton));
      final beforeAddIndex = flashcards.length;
      await tester.pumpAndSettle();
      await tester.enterText(find.byType(TextField).at(8), 'termTest');
      await tester.pumpAndSettle();
      await tester.enterText(find.byType(TextField).at(9), 'definitionTest');
      await tester.pumpAndSettle();
      await tester.tap(find.text('Add FlashCard'));
      await tester.pumpAndSettle();
      final afterAddIndex = flashcards.length;
      expect(afterAddIndex - 1, beforeAddIndex);
      final secondToLast = flashcards[flashcards.length - 2];
      expect(secondToLast.front.text, '8');
      final lastCard = flashcards.last;
      expect(lastCard.front.text, 'termTest');
      expect(lastCard.back.text, 'definitionTest');
    },
  );

  testWidgets(
    'Test possibility to edit existing card',
    (WidgetTester tester) async {
      await tester.pumpWidget(MaterialApp(home: Home()));
      await tester.tap(find.byIcon(Icons.edit));
      await tester.pumpAndSettle();

      await tester.enterText(
          find.byType(CardContentField).first, 'editTestfront');
      await tester.testTextInput.receiveAction(TextInputAction.done);
      await tester.pumpAndSettle();
      await tester.enterText(
          find.byType(CardContentField).at(1), 'editTestback');
      await tester.testTextInput.receiveAction(TextInputAction.done);
      await tester.pumpAndSettle();
      expect(flashcards.first.front.text, 'editTestfront');
      expect(flashcards.first.back.text, 'editTestback');
    },
  );

  testWidgets(
    'Cancel add card',
    (WidgetTester tester) async {
      await tester.pumpWidget(MaterialApp(home: Home()));
      await tester.tap(find.byIcon(Icons.edit));
      await tester.pumpAndSettle();

      final cancelTest = flashcards;
      await tester.tap(find.byType(FloatingActionButton));
      await tester.pumpAndSettle();
      await tester.enterText(find.byType(TextField).at(8), 'termTest');
      await tester.pumpAndSettle();
      await tester.enterText(find.byType(TextField).at(9), 'definitionTest');
      await tester.pumpAndSettle();
      await tester.tap(find.text('Cancel'));
      await tester.pumpAndSettle();
      expect(flashcards, cancelTest);
    },
  );
}

// TODO delete

//   testWidgets('widget testing for flashy_client', (WidgetTester tester) async {
//     await tester.pumpWidget(MaterialApp(
//       home: Home(),
//     ));

//     await tester.pump();

//     // test delete card
//     await tester.tap(find.byIcon(Icons.edit));
//     final beforeDeleteIndex = flashcards.length;
//     await tester.pumpAndSettle();
//     int index = 0;
//     await tester.drag(
//         find.text('FlashCard ${index + 1}'), const Offset(-1000, 0.0));
//     await tester.pumpAndSettle();
//     final afterDeleteIndex = flashcards.length;
//     expect(afterDeleteIndex, beforeDeleteIndex - 1);

//     // test add card

//     await tester.tap(find.byType(FloatingActionButton));
//     final beforeAddIndex = flashcards.length;
//     await tester.pumpAndSettle();
//     await tester.enterText(find.byType(TextField).at(8), 'termTest');
//     await tester.pumpAndSettle();
//     await tester.enterText(find.byType(TextField).at(9), 'definitionTest');
//     await tester.pumpAndSettle();
//     await tester.tap(find.text('Add FlashCard'));
//     await tester.pumpAndSettle();
//     final afterAddIndex = flashcards.length;
//     expect(afterAddIndex - 1, beforeAddIndex);
//     final secondToLast = flashcards[flashcards.length - 2];
//     expect(secondToLast.front, '8');
//     final lastCard = flashcards.last;
//     expect(lastCard.front, 'termTest');
//     expect(lastCard.back, 'definitionTest');

//     // test edit cards
//     await tester.enterText(
//         find.byType(CardContentField).first, 'editTestfront');
//     await tester.testTextInput.receiveAction(TextInputAction.done);
//     await tester.pumpAndSettle();
//     await tester.enterText(find.byType(CardContentField).at(1), 'editTestback');
//     await tester.testTextInput.receiveAction(TextInputAction.done);
//     await tester.pumpAndSettle();
//     expect(flashcards.first.front, 'editTestfront');
//     expect(flashcards.first.back, 'editTestback');

//     // test cancel card
//     final cancelTest = flashcards;
//     await tester.tap(find.byType(FloatingActionButton));
//     await tester.pumpAndSettle();
//     await tester.enterText(find.byType(TextField).at(8), 'termTest');
//     await tester.pumpAndSettle();
//     await tester.enterText(find.byType(TextField).at(9), 'definitionTest');
//     await tester.pumpAndSettle();
//     await tester.tap(find.text('Cancel'));
//     await tester.pumpAndSettle();
//     expect(flashcards, cancelTest);

