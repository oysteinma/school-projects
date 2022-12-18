/**
 * Module for all core logic.
 * 
 * Including datamodels and backend service workers.
 */
module it1901.groups2021.gr2141.core {
  requires java.prefs;

  requires transitive com.fasterxml.jackson.core;
  requires transitive com.fasterxml.jackson.databind;

  exports it1901.groups2021.gr2141.core.models;
  exports it1901.groups2021.gr2141.core.models.serializers;
  exports it1901.groups2021.gr2141.core.storage;
  exports it1901.groups2021.gr2141.core.domainlogic;

  // Opens and Exports core.model for accessing the object in the java classes 
  opens it1901.groups2021.gr2141.core.models;
}