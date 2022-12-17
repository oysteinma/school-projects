import 'package:flutter/material.dart';

class MemorizationPageNavBar extends StatefulWidget
    implements PreferredSizeWidget {
  MemorizationPageNavBar({
    Key? key,
    required this.isSelected,
    required this.onModeChanged,
  })  : preferredSize = const Size.fromHeight(56.0),
        super(key: key);

  List<bool> isSelected;
  final void Function(List<bool>) onModeChanged;

  @override
  final Size preferredSize;

  @override
  _MemorizationPageNavBarState createState() => _MemorizationPageNavBarState();
}

class _MemorizationPageNavBarState extends State<MemorizationPageNavBar> {
  @override
  Widget build(BuildContext context) {
    return AppBar(
      title: Text(
        'Flashy',
        key: ValueKey('testexample'),
      ),
      centerTitle: true,
      actions: <Widget>[
        ToggleButtons(
          selectedColor: Colors.white,
          borderRadius: BorderRadius.circular(0),
          children: const <Widget>[
            Icon(Icons.shuffle_rounded),
            Icon(Icons.repeat),
            Icon(Icons.swap_horiz),
          ],
          onPressed: (int index) {
            widget.isSelected[index] = !widget.isSelected[index];

            widget.onModeChanged(widget.isSelected);
          },
          isSelected: widget.isSelected,
        ),
      ],
    );
  }
}
