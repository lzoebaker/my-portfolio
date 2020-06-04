package com.google.sps.servlets;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.sps.data.Comment;
import com.google.sps.data.CommentDatabase;
import com.google.sps.servlets.ParameterGetter;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that deletes a comment */
@WebServlet("/delete-comment")
public class DeleteCommentServlet extends HttpServlet {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private static final String KEY_VALUE_TO_DELETE = "key-value";
    private static final String COMMENTS_PAGE_URL = "/comments-page.html";
    private static final String AUTHOR_VALUE_DELINEATOR = ":";
    
    @Override
      public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String displayedComment = ParameterGetter.getParameter(request, KEY_VALUE_TO_DELETE);
        Key commentKey = extractKeyFromDisplayedComment(displayedComment);
        datastore.delete(commentKey);
        response.sendRedirect(COMMENTS_PAGE_URL);
      }

      private Key extractKeyFromDisplayedComment(String displayedComment) {
        String[] authorAndValue = displayedComment.split(AUTHOR_VALUE_DELINEATOR, 2);
        String author = authorAndValue[0].trim();  
        String value = authorAndValue[1].trim();
        return generateKeyFromAuthorValue(author, value);
      }

      private Key generateKeyFromAuthorValue(String author, String value) {
        String keyString = author + value;
        return KeyFactory.createKey(CommentDatabase.COMMENT_QUERY_STRING, keyString);
      }
}