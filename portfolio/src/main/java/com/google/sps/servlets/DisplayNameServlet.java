package com.google.sps.servlets;

import com.google.sps.data.UserAuthentication;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.sps.data.UserDatabase;
import com.google.sps.servlets.ParameterGetter;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Servlet that allows user to set their display name, updates datastore */
public class DisplayNameServlet extends HttpServlet {
  private static final String DISPLAY_NAME_QUERY_STRING = "name";
  private static final String COMMENTS_PAGE_URL = "/comments-page.html";
  private final DatastoreService datastore;
  private final UserDatabase userDatabase;
  private final UserService userService;

  public DisplayNameServlet(DatastoreService datastore, UserService userService, UserDatabase userDatabase) {
     this.datastore = datastore;
     this.userService = userService;
     this.userDatabase = userDatabase;
   }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String displayName = ParameterGetter.getParameter(request, DISPLAY_NAME_QUERY_STRING);
    String id = this.userService.getCurrentUser().getUserId();
    this.userDatabase.setDisplayName(id, displayName);
    response.sendRedirect(COMMENTS_PAGE_URL);
  }
}