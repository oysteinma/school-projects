import 'package:flutter/material.dart';

/// A [StatelessWidget] that allows for server config in the client.
class EditConnectionPage extends StatelessWidget {
  final TextEditingController ipTextController;
  final TextEditingController portTextController;

  final String ip;
  final String port;

  final void Function(String, String) onChanged;

  EditConnectionPage({
    required this.ip,
    required this.port,
    required this.onChanged,
    Key? key,
  })  : ipTextController = TextEditingController(text: ip),
        portTextController = TextEditingController(text: port),
        super(key: key);

  void callBack() => onChanged(ipTextController.text, portTextController.text);

  @override
  Widget build(BuildContext context) {
    return Container(
      alignment: Alignment.center,
      child: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            TextField(
              controller: ipTextController,
              onEditingComplete: callBack,
              decoration: InputDecoration(
                  label: Text('IP'), hintText: 'ex. 192.168.0.5'),
            ),
            TextField(
              controller: portTextController,
              onEditingComplete: callBack,
              decoration:
                  InputDecoration(label: Text('Port'), hintText: 'ex. 26823'),
            ),
            SizedBox.fromSize(
              size: Size.fromHeight(20),
            ),
            ElevatedButton(
              onPressed: callBack,
              child: Text('Reconnect'),
            ),
          ],
        ),
      ),
    );
  }
}
