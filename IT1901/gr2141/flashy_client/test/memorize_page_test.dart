import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flashy_client/home.dart';

void main() {
  testWidgets(
    'Test when you tap a card the content flips',
    (WidgetTester tester) async {
      await tester.pumpWidget(MaterialApp(home: Home()));

      var card = find.byType(Center).first;

      await tester.tap(card);
      await tester.pump();
      expect((find.byType(Text).evaluate().first.widget as Text).data, '1.2');

      await tester.tap(card);
      await tester.pump();
      expect((find.byType(Text).evaluate().first.widget as Text).data, '1');
    },
  );

  testWidgets(
    'Test sliding',
    (WidgetTester tester) async {
      await tester.pumpWidget(MaterialApp(home: Home()));

      await tester.drag(find.byType(Center).first, const Offset(-1000, 0.0));
      await tester.pump();
      expect((find.byType(Text).evaluate().first.widget as Text).data, '2');

      await tester.drag(find.byType(Center).first, const Offset(1000, 0.0));
      await tester.pumpAndSettle();
      expect((find.byType(Text).evaluate().first.widget as Text).data, '1');
    },
  );

  testWidgets(
    'Test buttons right',
    (WidgetTester tester) async {
      await tester.pumpWidget(MaterialApp(home: Home()));

      await tester.tap(find.byType(ElevatedButton).at(3));
      await tester.pumpAndSettle();
      expect((find.byType(Text).evaluate().first.widget as Text).data, '2');
      expect(find.byType(ElevatedButton).at(2), findsOneWidget);
    },
  );

  testWidgets(
    'Test button left',
    (WidgetTester tester) async {
      await tester.pumpWidget(MaterialApp(home: Home()));

      // slide to right first to check left button works
      await tester.drag(find.byType(Center).first, const Offset(-1000, 0.0));
      await tester.pump();

      await tester.tap(find.byType(ElevatedButton).at(2));
      await tester.pumpAndSettle();
      expect((find.byType(Text).evaluate().first.widget as Text).data, '1');
      expect(find.byType(ElevatedButton).at(2), findsOneWidget);
    },
  );
}
