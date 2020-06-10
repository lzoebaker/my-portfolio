package com.google.sps.data;
import com.google.appengine.api.datastore.DatastoreService;

public final class UserDatabase {
  private final DatastoreService datastore;
  private String displayName;

  public UserDatabase(DatastoreService datastore) {
    this.datastore = datastore;
  }

  public String getDisplayName(String userId) {
      // todo: fetch correct entity from datastore, return that display name
      return displayName;
  }

  public void setDisplayName(String userId, String displayName) {
    this.displayName = displayName;
  }
}