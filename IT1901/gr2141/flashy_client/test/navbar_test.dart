import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flashy_client/home.dart';

void main() {
  testWidgets(
    'Test swap button works',
    (WidgetTester tester) async {
      await tester.pumpWidget(MaterialApp(home: Home()));
      await tester.tap(find.byIcon(Icons.swap_horiz));
      await tester.pumpAndSettle();

      expect((find.byType(Text).evaluate().first.widget as Text).data, '1.2');
    },
  );

  testWidgets(
    'Test random button works',
    (WidgetTester tester) async {
      await tester.pumpWidget(MaterialApp(home: Home()));
      await tester.tap(find.byIcon(Icons.shuffle_rounded));
      await tester.pumpAndSettle();

      await tester.drag(find.byType(Center).first, const Offset(-500, 0.0));
      await tester.pumpAndSettle();
      expect((find.byType(Text).evaluate().first.widget as Text).data != ('1'),
          true);
      // test random mode flip card works as intended
      var card = find.byType(Center).first;

      await tester.tap(card);
      await tester.pump();
      expect(
          (find.byType(Text).evaluate().first.widget as Text).data != ('1.2'),
          true);
    },
  );

  testWidgets(
    'Test wrap around button works',
    (WidgetTester tester) async {
      await tester.pumpWidget(MaterialApp(home: Home()));
      await tester.tap(find.byIcon(Icons.repeat));
      await tester.pumpAndSettle();

      final loopCard1 =
          (find.byType(Text).evaluate().first.widget as Text).data;

      for (var i = 0; i < flashcards.length + 1; i++) {
        await tester.drag(find.byType(Center).first, const Offset(-600, 0.0));
        await tester.pumpAndSettle();
      }

      final loopcard2 =
          (find.byType(Text).evaluate().first.widget as Text).data;
      expect(loopcard2, loopCard1);
    },
  );
}
