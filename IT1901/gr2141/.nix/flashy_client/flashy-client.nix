{ stdenv, flutter }:
stdenv.mkDerivation {
  pname = "flashy_client";
  version = "1.0.0";
  buildInputs = [
    flutter
  ];
  # TODO: write installPhase for flutter project
}