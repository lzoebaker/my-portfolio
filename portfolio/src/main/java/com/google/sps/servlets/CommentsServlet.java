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
  private static final int MAX_COMMENT_DEFAULT = 20;
  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  CommentDatabase commentDatabase = new CommentDatabase(datastore);

  /* responds with a JSON string containing authors and comments*/
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Query datastore for all comments
    int maxComments = MAX_COMMENT_DEFAULT;
    try {
        maxComments = getMaxCommentsToDisplay(request);
    } catch (IllegalArgumentException e) {
        maxComments = MAX_COMMENT_DEFAULT;
        System.out.println(e.getMessage());
    } 
    ArrayList<Comment> comments = commentDatabase.getComments(maxComments);
    // Convert the ArrayLists to JSON
    Gson gson = new Gson();
    String commentsJson = gson.toJson(comments);
    // Send the JSON as the response
    response.setContentType(JSON_CONTENT_TYPE);
    response.getWriter().println(commentsJson);
  }

  /* when the submit button on the values page is hit, doPost requests the text in the author and commment field and stores them*/ 
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the comments form
    try {
      String authorText = getParameter(request, commentDatabase.AUTHOR_QUERY_STRING);
      String valueText = getParameter(request, commentDatabase.VALUE_QUERY_STRING);
      Comment comment = new Comment(authorText, valueText);
      commentDatabase.putCommentInDatabase(comment);   
    } catch (RuntimeException e){ 
        e.printStackTrace();
        System.out.println("Comment entered is not a valid value.");
    }
    // redirect back to comments page
    response.sendRedirect(COMMENTS_PAGE_URL);
  }
   
  /* wrapper for the servlet request method, with safety default value*/
  private String getParameter(HttpServletRequest request, String name) {
    String value = request.getParameter(name);
    if (value == null) {
      return "";
    }
    return value;
  }

  private int getMaxCommentsToDisplay(HttpServletRequest request) throws IllegalArgumentException {
    String maxCommentsToDisplayString = "3"; //temporary hardcoded value, will get replaced with user input

    // Convert the input to an int.
    int maxCommentsToDisplay;
    try {
        maxCommentsToDisplay = Integer.parseInt(maxCommentsToDisplayString);
    } catch (NumberFormatException e) {
        throw new NumberFormatException("Max comments cannot be converted to string: " + maxCommentsToDisplayString);
    }
    // Check that the input is greater than 0
    if (maxCommentsToDisplay < 0) {
      throw new IllegalArgumentException("Max comments to display cannot be negative: " + maxCommentsToDisplayString);
    }
    return maxCommentsToDisplay;
  }
  
}