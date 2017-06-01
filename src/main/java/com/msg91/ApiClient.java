package com.msg91;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.msg91.exceptions.InvalidRequestException;
import com.msg91.exceptions.Msg91Exception;

public class ApiClient {

  private String authkey;
  private static final String API_BASE_PATH = "http://test.panel.msg91.com/api";
  private MessageFactory messageFactory;

  public String getAuthkey() {
    return authkey;
  }

  public ApiClient(String authkey) {
    this.authkey = authkey;
    this.messageFactory = MessageFactory.getInstance(this);
  }

  public Long getRouteBalance(MessageRoute route) throws Msg91Exception {
    Map<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("type", route.getValue());
    ApiResponse response = this.request("balance.php", parameters);

    if (response.isRaw()) {
      return new Long(response.getRaw());
    }

    throw new InvalidRequestException(response.getJson().getObject().getString("msg"));
  }

  public Boolean isValid() throws Msg91Exception {
    ApiResponse response = this.request("validate.php");

    if (response.isJson()) {
      JSONObject responseObj = response.getJson().getObject();
      return "Valid".equals(responseObj.getString("message"));
    }

    throw new InvalidRequestException(response.getRaw());
  }

  public ApiResponse request(String endpoint) throws Msg91Exception {
    Map<String, Object> parameters = new HashMap<String, Object>();
    return this.request(endpoint, parameters);
  }

  public ApiResponse request(String endpoint, Map<String, Object> parameters) throws Msg91Exception {
    String url = API_BASE_PATH + "/" + endpoint;

    try {
      HttpResponse<String> response = Unirest.get(url).queryString("response", "json")
          .queryString("authkey", this.getAuthkey()).queryString(parameters).asString();

      return new ApiResponse(response.getBody());
    } catch (UnirestException e) {
      throw new Msg91Exception(e.getMessage());
    }
  }

  public MessageFactory messages() {
    return this.messageFactory;
  }

  public static String version() {
    return "0.0.1-SNAPSHOT";
  }

}
