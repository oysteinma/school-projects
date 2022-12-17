import 'package:flashy_client/home.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  testWidgets(
    'Test fill out ip/port',
    (WidgetTester tester) async {
      await tester.pumpWidget(MaterialApp(home: Home()));
      await tester.tap(find.byIcon(Icons.swap_vert));
      await tester.pumpAndSettle();

      const ip = 'testip.com';
      const port = '12345';

      await tester.enterText(find.byType(TextField).at(0), ip);
      await tester.enterText(find.byType(TextField).at(1), port);
      await tester.pumpAndSettle();

      await tester.tap(find.byType(ElevatedButton));
      await tester.pumpAndSettle();

      expect(tester.widget<TextField>(find.byType(TextField).at(0)).controller?.text, ip);
      expect(tester.widget<TextField>(find.byType(TextField).at(1)).controller?.text, port);
      expect(tester.state<HomeState>(find.byType(Home)).ip, ip);
      expect(tester.state<HomeState>(find.byType(Home)).port, port);
  },
  );
}
