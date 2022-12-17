{
  pkgs ? import <nixpkgs> {
    config = {
      android_sdk.accept_license = true;
      allowUnfree = true;
    };
  }
}:
let
  inherit (pkgs) mkShell androidenv google-chrome jdk8 callPackage flutterPackages fetchurl;
  inherit (callPackage ./flutter.nix {}) flutter dart;

  buildToolsVersion = "30.0.0";
  androidSDK = (androidenv.composeAndroidPackages {
    buildToolsVersions = [ buildToolsVersion "29.0.2" "28.0.3" ];
    platformVersions = [ "30" "29" "28" ];
    abiVersions = [ "x86" "x86_64"];
  }).androidsdk;
in
mkShell rec {
  ANDROID_JAVA_HOME="${jdk8.home}";
  ANDROID_SDK_ROOT = "${androidSDK}/libexec/android-sdk";
  CHROME_EXECUTABLE = "${google-chrome}/bin/google-chrome-stable";
  FLUTTER_SDK="${flutter.unwrapped}";
  GRADLE_OPTS = "-Dorg.gradle.project.android.aapt2FromMavenOverride=${ANDROID_SDK_ROOT}/build-tools/${buildToolsVersion}/aapt2";
  JAVA_HOME="${ANDROID_JAVA_HOME}";
  USE_CCACHE=0;

  buildInputs = with pkgs; [
    androidSDK
    flutter
    dart
    jdk8
    git
    lcov
  ];

  shellHook=''
    export PATH="$HOME/.pub-cache/bin:$PATH"
    cd flashy_client
  '';
}
