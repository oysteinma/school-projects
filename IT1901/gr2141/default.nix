{ pkgs ? import <nixpkgs> {} }:
{
  flashy = pkgs.callPackage ./.nix/flashy {};
  flashy_client = pkgs.callPackage ./.nix/flashy_client {};
}
