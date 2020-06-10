package com.google.sps.data;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.DatastoreService;

public final class UserDatabase {
  private DatastoreService datastore;
  private static final String DISPLAY_NAME_QUERY_STRING = "display-name";
  private static final String USER_ENTITY_QUERY_STRING = "UserInfo";
  private static final String ID_QUERY_STRING = "id";

  public UserDatabase(DatastoreService datastore) {
    this.datastore = datastore;
  }

  public String getDisplayName(String userId) {
    Query query = new Query(USER_ENTITY_QUERY_STRING)
        .setFilter(
            new Query.FilterPredicate(ID_QUERY_STRING, Query.FilterOperator.EQUAL, userId));
    PreparedQuery results = datastore.prepare(query);
    Entity entity = results.asSingleEntity();
    if (entity == null) {
      return "";
    }
    return entity.getProperty(DISPLAY_NAME_QUERY_STRING).toString();
  }

  public void setDisplayName(String userId, String displayName) {
    Entity entity = createUserEntity(userId, displayName);
    this.datastore.put(entity);
  }

  private Entity createUserEntity(String userId, String displayName) {
    Entity entity = new Entity(USER_ENTITY_QUERY_STRING, userId);
    entity.setProperty(ID_QUERY_STRING, userId);
    entity.setProperty(DISPLAY_NAME_QUERY_STRING, displayName);
    return entity;
  }

}