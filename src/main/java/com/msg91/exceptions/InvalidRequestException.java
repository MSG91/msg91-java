package com.msg91.exceptions;

public class InvalidRequestException extends Msg91Exception {

  private static final long serialVersionUID = -6698414974591729188L;

  public InvalidRequestException(String message) {
    super(message);
  }

}
