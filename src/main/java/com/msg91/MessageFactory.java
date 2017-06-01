package com.msg91;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.msg91.exceptions.MessageException;
import com.msg91.exceptions.Msg91Exception;

final class MessageFactory {

  private ApiClient apiClient;
  private static MessageFactory instance;

  private MessageFactory(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public static MessageFactory getInstance(ApiClient apiClient) {
    if (MessageFactory.instance == null) {
      MessageFactory.instance = new MessageFactory(apiClient);
    }

    return MessageFactory.instance;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public Message createNew() {
    return new Message();
  }

  class Message {

    private String id;
    private String[] mobiles;
    private String message;
    private MessageRoute route;
    private String country;
    private Boolean flash = Boolean.FALSE;
    private Boolean unicode = Boolean.FALSE;
    private String schedule;
    private Integer afterMinutes;
    private String campaign;
    private String sender;

    private Map<String, Object> params = new HashMap<String, Object>();

    public Message send() throws MessageException {
      if (this.id != null) {
        throw new MessageException("Already sent");
      }

      ApiResponse response = null;

      try {
        response = apiClient.request("sendhttp.php", this.params);
      } catch (Msg91Exception e) {
        throw new MessageException(e.getMessage());
      }

      if (response.isJson()) {
        JSONObject responseObj = response.getJson().getObject();

        if ("success".equals(responseObj.getString("type"))) {
          this.id = responseObj.getString("message");
          return this;
        }

        throw new MessageException(responseObj.getString("message"));
      }

      throw new MessageException(response.getRaw());
    }

    public String getId() {
      return id;
    }

    public String[] getMobiles() {
      return mobiles;
    }

    public Message mobiles(String[] mobiles) {
      this.mobiles = mobiles;

      if (this.mobiles.length > 0) {
        this.params.put("mobiles", String.join(",", this.mobiles));
      }

      return this;
    }

    public String getMessage() {
      return message;
    }

    public Message message(String message) {
      this.message = message;

      if (this.message.trim().length() > 0) {
        this.params.put("message", this.message);
      }

      return this;
    }

    public MessageRoute getRoute() {
      return route;
    }

    public Message route(MessageRoute route) {
      this.route = route;
      this.params.put("route", this.route.getValue());
      return this;
    }

    public String getCountry() {
      return country;
    }

    public Message country(String country) {
      this.country = country;

      if (this.country.trim().length() > 0) {
        this.params.put("country", this.country);
      }

      return this;
    }

    public Boolean getFlash() {
      return flash;
    }

    public Message flash(Boolean flash) {
      this.flash = flash;
      this.params.put("flash", this.flash ? 1 : 0);
      return this;
    }

    public Boolean getUnicode() {
      return unicode;
    }

    public Message unicode(Boolean unicode) {
      this.unicode = unicode;
      this.params.put("unicode", this.unicode ? 1 : 0);
      return this;
    }

    public String getSchedule() {
      return schedule;
    }

    public Message schedule(String schedule) {
      this.schedule = schedule;

      if (this.schedule.trim().length() > 0) {
        this.params.put("schtime", this.schedule);
      }

      return this;
    }

    public Integer getAfterMinutes() {
      return afterMinutes;
    }

    public Message afterMinutes(Integer afterMinutes) {
      this.afterMinutes = afterMinutes;

      if (this.afterMinutes > 0) {
        this.params.put("afterminutes", this.afterMinutes);
      }

      return this;
    }

    public String getCampaign() {
      return campaign;
    }

    public Message campaign(String campaign) {
      this.campaign = campaign;

      if (this.campaign.trim().length() > 0) {
        this.params.put("campaign", this.campaign);
      }

      return this;
    }

    public String getSender() {
      return sender;
    }

    public Message sender(String sender) {
      this.sender = sender;

      if (this.sender.trim().length() > 0) {
        this.params.put("sender", this.sender);
      }

      return this;
    }

  }

}
