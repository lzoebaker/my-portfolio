package com.google.sps.data;

/** Class to store data associated with each value left on page */
public final class Comment {
  private String author;
  private String value;

  public Comment(String author, String value) {
    this.author = author;
    this.value = value;
  }

  public String getAuthor() {
    return author;
  }

  public String getValue() {
    return value;
  }
}