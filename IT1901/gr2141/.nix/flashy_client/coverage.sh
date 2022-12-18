#!/usr/bin/env nix-shell
#!nix-shell -i sh --pure
flutter test --coverage
genhtml coverage/lcov.info --output-directory=coverage
lcov --list coverage/lcov.info