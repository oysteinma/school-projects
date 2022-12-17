{ makeDesktopItem, stdenv, maven, makeWrapper, callPackage, jre }:
let

  mavenRepo = callPackage ./maven-dependencies.nix {};

  desktop-item = makeDesktopItem {
    name = "Flashy";
    desktopName = "Flashy";
    exec = "flashy";
    icon = "flashy";
    type = "Application";
    terminal = false;
    categories = "Game;Education;";
  };
in stdenv.mkDerivation rec {
  pname = "flashy";
  version = "1.0.0";

  src = ../../flashy;

  buildInputs = [
    maven
    makeWrapper
  ];

  buildPhase = ''
    echo "Building with maven repository ${mavenRepo}"
    mvn package --offline -Dmaven.test.skip=true -Dmaven.repo.local=${mavenRepo}
  '';


  installPhase = let
    jarDir = "$out/share/java";

    iconDir = "$out/share/icons/hicolor/128x128/apps";
    srcIcon = "fxui/src/main/resources/it1901/groups2021/gr2141/ui/graphics/logo.png"; 
  in ''
    mkdir -p ${jarDir}
    mv target/${pname}.jar ${jarDir}

    mkdir -p ${iconDir}
    mv ${srcIcon} ${iconDir}/flashy.png

    mkdir -p $out/share/applications
    cp -r ${desktop-item}/* $out

    ln -s ${mavenRepo} $out/repository

    mkdir -p $out/bin
    makeWrapper ${jre}/bin/java $out/bin/${pname} \
      --add-flags "-jar ${jarDir}/${pname}.jar" \
  '';
}
