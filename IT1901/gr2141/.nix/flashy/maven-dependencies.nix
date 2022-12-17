# http://ryantm.github.io/nixpkgs/languages-frameworks/maven/

{ maven, stdenv }:
stdenv.mkDerivation {
  name = "maven-repository";
  buildInputs = [ maven ];
  src = ../../flashy;
  buildPhase = ''
    mvn package -Dmaven.repo.local=$out -Dmaven.test.skip=true
  '';

  installPhase = ''
    find $out -type f \
      -name \*.lastUpdated -or \
      -name resolver-status.properties -or \
      -name _remote.repositories \
      -delete
  '';

  dontFixup = true;
  outputHashAlgo = "sha256";
  outputHashMode = "recursive";
  outputHash = "UubOROvw36j4H7W/KG6WYpTgyzj+zsu3mWMap3++4Ic=";
}