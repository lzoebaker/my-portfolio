package com.google.sps.data;

/** Class to store data associated with each value left on page */
public final class Comment {
  private String author;
  private String value;
  private long timestamp;

  public Comment(String author, String value) {
    this.author = author;
    this.value = value;
    this.timestamp = System.currentTimeMillis();
  }

  public String getAuthor() {
    return author;
  }

  public String getValue() {
    return value;
  }

  public long getTimestamp(){
    return timestamp;
  }
}