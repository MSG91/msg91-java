package com.msg91;

public enum MessageRoute {
  PROMOTIONAL(1), TRANSACTIONAL(4);

  private final int value;

  private MessageRoute(int value) {
    this.value = value;
  }

  public int getValue() {
    return this.value;
  }
}
