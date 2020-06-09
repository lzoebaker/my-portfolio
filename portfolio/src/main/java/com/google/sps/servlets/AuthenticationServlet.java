package com.google.sps.servlets;

import com.google.sps.data.UserAuthentication;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that handles user authentication, so that users must log in to post comments */
@WebServlet("/login") 
public class AuthenticationServlet extends HttpServlet {
  //TODO: change to actual redirect page. currently just redirects to test logoff
  private final static String REDIRECT_URL = "/login.html"; 
  private static final String JSON_CONTENT_TYPE = "application/json;";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    Boolean isUserLoggedIn = userService.isUserLoggedIn();
    String userEmail;
    String url;
    if (isUserLoggedIn) {
        userEmail = userService.getCurrentUser().getEmail();
        url = userService.createLogoutURL(REDIRECT_URL);
    } else {
        userEmail = "";
        url = userService.createLoginURL(REDIRECT_URL);
    }
    UserAuthentication userAuthentication = new UserAuthentication(isUserLoggedIn, userEmail, url);
    Gson gson = new Gson();
    String userAuthenticationInfoJSON = gson.toJson(userAuthentication); 
    response.setContentType(JSON_CONTENT_TYPE);
    response.getWriter().println(userAuthenticationInfoJSON);
  }
}
