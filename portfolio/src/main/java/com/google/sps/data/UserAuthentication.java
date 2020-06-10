package com.google.sps.data;

/* Purpose: stores information from UserService to deliver to comment_authentication.js to display */
public final class UserAuthentication {
  private Boolean isLoggedIn;
  private String userEmail;
  private String authenticationUrl;
  private String displayName;

  public UserAuthentication(Boolean isLoggedIn, String userEmail, String displayName, String authenticationUrl) {
    this.isLoggedIn = isLoggedIn;
    this.userEmail = userEmail;
    this.authenticationUrl = authenticationUrl;
    this.displayName = displayName;
  }
}