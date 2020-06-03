package com.google.sps.data;
import com.google.sps.data.Comment;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.ArrayList;

/* purpose: to manage the interface of the Datastore database used to store comments */
public final class CommentDatabase {
  public static final String COMMENT_QUERY_STRING = "Comment";
  public static final String AUTHOR_QUERY_STRING = "author";
  public static final String VALUE_QUERY_STRING = "value";
  public static final String TIME_QUERY_STRING = "timestamp";
  private static final Query COMMENT_QUERY = new Query(COMMENT_QUERY_STRING).addSort(TIME_QUERY_STRING, SortDirection.ASCENDING);
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
    PreparedQuery storedCommentsQuery = datastore.prepare(COMMENT_QUERY);
    this.comments = new ArrayList<Comment>();
    for (Entity entity : storedCommentsQuery.asIterable()) {
      Comment comment = new Comment((String) entity.getProperty(AUTHOR_QUERY_STRING), (String) entity.getProperty(VALUE_QUERY_STRING));
      this.comments.add(comment);
    }  
  }

  public void putCommentInDatabase(Comment comment) {
      Entity commentEntity = getDatastoreEntityFromComment(comment);
      datastore.put(commentEntity);
  } 

  private Entity getDatastoreEntityFromComment(Comment comment) throws RuntimeException {
    // store the comments in a datastore
    Entity commentEntity = new Entity(COMMENT_QUERY_STRING);
    commentEntity.setProperty(AUTHOR_QUERY_STRING, comment.getAuthor());
    commentEntity.setProperty(VALUE_QUERY_STRING, comment.getValue());
    commentEntity.setProperty(TIME_QUERY_STRING, comment.getTimestamp());
    return commentEntity;
  }

}