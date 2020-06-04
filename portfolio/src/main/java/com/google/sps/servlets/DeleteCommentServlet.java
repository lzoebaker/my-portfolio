package com.google.sps.servlets;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.sps.data.Comment;
import com.google.sps.data.CommentDatabase;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that deletes a comment */
@WebServlet("/delete-comment")
public class DeleteCommentServlet extends HttpServlet {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    @Override
      public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Comment comment = new Comment("hardcoded", "hardcoded"); // replace with getting user input from clicking a delete button
        Key hardcodedKey = generateKey(comment);
        datastore.delete(hardcodedKey);
      }
      
      private Key generateKey(Comment comment) {
        String keyString = comment.getAuthor() + comment.getValue();
        return KeyFactory.createKey(CommentDatabase.COMMENT_QUERY_STRING, keyString);
      }
}