package com.google.sps.data;
import com.google.sps.data.Comment;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.FetchOptions;
import java.util.ArrayList;
import java.util.stream.StreamSupport;
import java.util.stream.Collectors;
import java.util.Collections;

/* purpose: to manage the interface of the Datastore database used to store comments */
public final class CommentDatabase {
  public static final String COMMENT_QUERY_STRING = "Comment";
  public static final String AUTHOR_QUERY_STRING = "author";
  public static final String VALUE_QUERY_STRING = "value";
  public static final String TIME_QUERY_STRING = "timestamp";
  private static final Query COMMENT_QUERY = new Query(COMMENT_QUERY_STRING).addSort(TIME_QUERY_STRING, SortDirection.DESCENDING);
  DatastoreService datastore;
  private ArrayList<Comment> comments;

  public CommentDatabase(DatastoreService datastore) {
    this.datastore = datastore;
  }

  /* public wrappper method to return all comments from datastore as an arraylist */
  public ArrayList<Comment> getComments(int maxCommentsToDisplay) {
    this.queryForComments(maxCommentsToDisplay);
    // reverse so that comments are displayed from oldest to newest
    Collections.reverse(this.comments);
    return this.comments;
  }

  private void queryForComments(int maxCommentsToDisplay) {
    PreparedQuery storedCommentsQuery = datastore.prepare(COMMENT_QUERY);
    // grab maxCommentsToDisplay newest comments
    this.comments = 
        StreamSupport
            .stream(
                storedCommentsQuery.asIterable(
                    FetchOptions.Builder.withLimit(maxCommentsToDisplay)).spliterator(),  
                    /*sequential execution*/ false)
            .map(this::commentFromEntity)
            .collect(Collectors.toCollection(ArrayList::new));
  }

  private Comment commentFromEntity(Entity entity) {
      return new Comment(
                 entity.getProperty(AUTHOR_QUERY_STRING).toString(), 
                 entity.getProperty(VALUE_QUERY_STRING).toString());
  } 

  public void putCommentInDatabase(Comment comment) {
      Entity commentEntity = getDatastoreEntityFromComment(comment);
      datastore.put(commentEntity);
  } 

  private Entity getDatastoreEntityFromComment(Comment comment) throws RuntimeException {
    // store the comments in a datastore
    String key = comment.getAuthor() + comment.getValue();
    Entity commentEntity = new Entity(COMMENT_QUERY_STRING, key);
    commentEntity.setProperty(AUTHOR_QUERY_STRING, comment.getAuthor());
    commentEntity.setProperty(VALUE_QUERY_STRING, comment.getValue());
    commentEntity.setProperty(TIME_QUERY_STRING, comment.getTimestamp());
    return commentEntity;
  }

}