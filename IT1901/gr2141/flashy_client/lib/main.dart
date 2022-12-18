import 'package:flutter/material.dart';
import 'package:flashy_client/home.dart';
import 'package:shared_preferences/shared_preferences.dart';

////Launcher for the flashy_client.
void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  SharedPreferences prefs = await SharedPreferences.getInstance();
  runApp(App(prefs: prefs));
}

class App extends StatelessWidget {
  final SharedPreferences prefs;

  const App({required this.prefs, Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(
        primarySwatch: Colors.purple,
        scaffoldBackgroundColor: Colors.purple.shade100,
      ),
      home: Home(prefs: prefs),
    );
  }
}
