/**
 * Module for packaging the application into a cross-platform JAR file.
 * 
 * This exists in order to package the application to a cross-platform JAR file,
 * without creating a mess for the other build tools in maven. Including several
 * graphics dependencies for several platforms does not work well together with
 * javafx-maven-plugin.
 */
module it1901.groups2021.gr2141.packaging {
  requires it1901.groups2021.gr2141.fxui;
  requires it1901.groups2021.gr2141.rest;

  exports it1901.groups2021.gr2141;
}