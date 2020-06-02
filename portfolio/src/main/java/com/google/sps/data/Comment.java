package com.google.sps.data;
import java.time.Instant;

/** Class to store data associated with each value left on page */
public final class Comment {
  private String author;
  private String value;
  private Long timestamp;

  public Comment(String author, String value) {
    this.author = author;
    this.value = value;
    this.timestamp = Instant.now().toEpochMilli();
  }

  public String getAuthor() {
    return author;
  }

  public String getValue() {
    return value;
  }

  public long getTimestamp() {
    return timestamp;
  }
}