{ pkgs ? import <nixpkgs> {} }:
{
  flashy = pkgs.callPackage ./.nix/flashy_client/shell.nix {};
  flashy-client = pkgs.callPackage ./.nix/flashy_client/shell.nix {};
}
