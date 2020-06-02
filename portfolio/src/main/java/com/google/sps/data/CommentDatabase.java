package com.google.sps.data;
import com.google.sps.data.Comment;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.ArrayList;

public final class CommentDatabase {
  public static final String COMMENT_FIELD = "Comment";
  public static final String AUTHOR_FIELD = "author";
  public static final String VALUE_FIELD = "value";
  public static final String TIME_FIELD = "timestamp";
  DatastoreService datastore;
  private ArrayList<Comment> comments;

  public CommentDatabase(DatastoreService datastore) {
    this.datastore = datastore;
  }

  /* public wrappper method to return all comments from datastore as an arraylist */
  public ArrayList<Comment> getCommentsAsArrayList(){
    this.getCommentsFromQuery();
    return this.comments;
  }

  private void getCommentsFromQuery() {
    Query commentQuery = new Query(COMMENT_FIELD).addSort(TIME_FIELD, SortDirection.ASCENDING);
    PreparedQuery storedCommentsQuery = datastore.prepare(commentQuery);
    this.comments = new ArrayList<Comment>();
    for (Entity entity : storedCommentsQuery.asIterable()) {
      Comment comment = new Comment((String) entity.getProperty(AUTHOR_FIELD), (String) entity.getProperty(VALUE_FIELD));
      this.comments.add(comment);
    }  
  }

  public void putCommentInDatabase(Comment comment) {
      Entity commentEntity = getDatastoreEntityFromComment(comment);
      datastore.put(commentEntity);
  } 

  private Entity getDatastoreEntityFromComment(Comment comment) throws RuntimeException {
    // store the comments in a datastore
    Entity commentEntity = new Entity(COMMENT_FIELD);
    commentEntity.setProperty(AUTHOR_FIELD, comment.getAuthor());
    commentEntity.setProperty(VALUE_FIELD, comment.getValue());
    commentEntity.setProperty(TIME_FIELD, comment.getTimestamp());
    return commentEntity;
  }

}