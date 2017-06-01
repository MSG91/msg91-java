package com.msg91.messages;

import com.msg91.ApiClient;

class Message {

  private ApiClient apiClient;

  public Message(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return this.apiClient;
  }

}
