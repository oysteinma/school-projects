{ flutterPackages, fetchurl, dart }:
let
  dartVersion = "2.14.2";
  flutterVersion = "2.5.1";

  channel = "stable";
  filename = "flutter_linux_${flutterVersion}-${channel}.tar.xz";
  dartSourceBase = "https://storage.googleapis.com/dart-archive/channels";

  newDart = dart.override {
    version = dartVersion;
    sources = {
      "${dartVersion}-x86_64-linux" = fetchurl {
        url = "${dartSourceBase}/stable/release/${dartVersion}/sdk/dartsdk-linux-x64-release.zip";
        sha256 = "1gr2dr683kz0a0k6rcn4jcbxf9fr2xlzi5fcgn1lzrrxvys2lddx";
      };
    };
  };

  getPatches = dir:
    let files = builtins.attrNames (builtins.readDir dir);
    in map (f: dir + ("/" + f)) files;
in
{
  flutter = flutterPackages.mkFlutter {
    version = flutterVersion;
    dart = newDart;
    pname = "flutter";
    src = fetchurl {
      url = "https://storage.googleapis.com/flutter_infra_release/releases/${channel}/linux/${filename}";
      sha256 = "Yme2htjRySlyZajrN4j6I0pPqaEl5W9YuollreL5zIs=";
    };
    patches = getPatches ./flutter-patches;
  };

  dart = newDart;
}