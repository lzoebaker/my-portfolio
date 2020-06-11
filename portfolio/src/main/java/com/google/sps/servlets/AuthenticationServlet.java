package com.google.sps.servlets;

import com.google.sps.data.UserAuthentication;
import com.google.sps.data.UserDatabase;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that handles user authentication, so that users must log in to post comments */ 
public class AuthenticationServlet extends HttpServlet {
  private final static String DISPLAY_NAME_URL = "/displayname.html";
  private final static String REDIRECT_LOGOFF_URL = "/comments-page.html";
  private final static String JSON_CONTENT_TYPE = "application/json";
  private final DatastoreService datastore;
  private final UserService userService;
  private final UserDatabase userDatabase;

  public AuthenticationServlet(DatastoreService datastore, UserService userService, UserDatabase userDatabase) {
     this.datastore = datastore;
     this.userService = userService;
     this.userDatabase = userDatabase;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    boolean isUserLoggedIn = userService.isUserLoggedIn();
    UserAuthentication userAuthentication;

    if (isUserLoggedIn) {
        userAuthentication = processLoggedInUser(userService);
    } else {
        userAuthentication = processNotLoggedInUser(userService);
    }

    Gson gson = new Gson();
    String userAuthenticationInfoJSON = gson.toJson(userAuthentication); 
    response.setContentType(JSON_CONTENT_TYPE);
    response.getWriter().println(userAuthenticationInfoJSON);
  }

  private UserAuthentication processNotLoggedInUser(UserService userService) {
    boolean isUserLoggedIn = false;
    String url = userService.createLoginURL(DISPLAY_NAME_URL);
    return new UserAuthentication(isUserLoggedIn, "", "", url);
  }
  
  private UserAuthentication processLoggedInUser(UserService userService) {
    boolean isUserLoggedIn = true;
    String userEmail = userService.getCurrentUser().getEmail();
    String userId = userService.getCurrentUser().getUserId();
    String displayName = userDatabase.getDisplayName(userId);
    if (displayName == null || displayName.isEmpty()){
        displayName = "";
    }
    String url = userService.createLogoutURL(REDIRECT_LOGOFF_URL);
    return new UserAuthentication(isUserLoggedIn, userEmail, displayName, url);   
  } 

}
