{ mkShell, jdk, maven, scenebuilder, nodePackages }:
mkShell {
  packages = [
    jdk
    maven
    scenebuilder
    nodePackages.live-server
  ];
  shellHook = ''
    cd flashy
    mvn install -Dmaven.test.skip=true
  '';
}
