package com.msg91;

import com.mashape.unirest.http.JsonNode;

public class ApiResponse {

  private String raw;
  private JsonNode json;

  public ApiResponse(String rawResponse) {
    try {
      this.json = new JsonNode(rawResponse);
    } catch (RuntimeException ex) {
      this.raw = rawResponse.trim();
    }
  }

  public String getRaw() {
    return raw;
  }

  public JsonNode getJson() {
    return json;
  }

  public boolean isJson() {
    return this.json != null;
  }

  public boolean isRaw() {
    return this.raw != null;
  }
}
