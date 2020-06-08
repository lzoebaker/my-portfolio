// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.sps.data.Comment;
import com.google.sps.data.CommentDatabase;
import com.google.sps.servlets.ParameterGetter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that handles comments data */
@WebServlet("/comments")
public class CommentsServlet extends HttpServlet {
  private static final String JSON_CONTENT_TYPE = "application/json;";
  private static final String COMMENTS_PAGE_URL = "/comments-page.html";
  private static final String MAX_COMMENTS_QUERY_STRING = "max-comments";
  private static final String COMMENT_FORM_SUBMIT = "comment-submit";
  private static final String MAX_FORM_SUBMIT = "max-comment-submit";
  private static final int MAX_COMMENT_DEFAULT = 12;
  int maxCommentsToDisplay;
  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  CommentDatabase commentDatabase = new CommentDatabase(datastore);

  @Override
   public void init() {
     maxCommentsToDisplay = MAX_COMMENT_DEFAULT;
   }

  /* responds with a JSON string containing authors and comments*/
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ArrayList<Comment> comments = commentDatabase.getComments(maxCommentsToDisplay);
    // Convert the ArrayLists to JSON
    Gson gson = new Gson();
    String commentsJson = gson.toJson(comments);
    // Send the JSON as the response
    response.setContentType(JSON_CONTENT_TYPE);
    response.getWriter().println(commentsJson);
  }
  
  /* responds to comments and max comment limit set by user */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // if max comments submit button clicked, get input from max comments form 
    if (!ParameterGetter.getParameter(request, MAX_FORM_SUBMIT).isEmpty()) {
      handleMaxCommentRequest(request);
    }
    // if comment submit button clicked, get author and value from comments form
    if (!ParameterGetter.getParameter(request, COMMENT_FORM_SUBMIT).isEmpty()) {
      handleCommentFormRequest(request);
    }
    // redirect back to comments page
    response.sendRedirect(COMMENTS_PAGE_URL);
  }

  private void handleMaxCommentRequest(HttpServletRequest request) {
    try {
        setMaxCommentsToDisplay(request);
      } catch (IllegalArgumentException e) {
        maxCommentsToDisplay = MAX_COMMENT_DEFAULT;
        System.out.println(e.getMessage());
      } 
  }

  private void handleCommentFormRequest(HttpServletRequest request) {
    try {
        String authorText = ParameterGetter.getParameter(request, commentDatabase.AUTHOR_QUERY_STRING);
        String valueText = ParameterGetter.getParameter(request, commentDatabase.VALUE_QUERY_STRING);
        Comment comment = new Comment(authorText, valueText);
        commentDatabase.putCommentInDatabase(comment);   
      } catch (RuntimeException e) { 
        e.printStackTrace();
        System.out.println("Comment entered is not a valid value.");
      }
  }

  private void setMaxCommentsToDisplay(HttpServletRequest request) throws IllegalArgumentException {
    String maxCommentsToDisplayString = ParameterGetter.getParameter(request, MAX_COMMENTS_QUERY_STRING);
    // Convert the input to an int.
    try {
        maxCommentsToDisplay = Integer.parseInt(maxCommentsToDisplayString);
    } catch (NumberFormatException e) {
        throw new NumberFormatException("Max comments cannot be converted to int: " + maxCommentsToDisplayString);
    }
    // Check that the input is greater than 0
    if (maxCommentsToDisplay < 0) {
      throw new IllegalArgumentException("Max comments to display cannot be negative: " + maxCommentsToDisplayString);
  } 
  }
  
}