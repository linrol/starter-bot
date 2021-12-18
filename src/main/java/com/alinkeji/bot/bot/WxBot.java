package com.alinkeji.bot.bot;

import com.alinkeji.bot.retdata.ApiData;
import com.alinkeji.bot.retdata.MessageData;
import lombok.Getter;
import lombok.Setter;
import org.java_websocket.client.WebSocketClient;

/**
 * @Description
 * @ClassName WxBot
 * @Author linrol
 * @date 2021年12月18日 14:22 Copyright (c) 2020, linrol@77hub.com All Rights Reserved.
 */
public class WxBot extends Bot {

  @Getter
  @Setter
  private WebSocketClient botClient;

  public WxBot(WebSocketClient webSocketClient) {
    super(0L, null, null, null);
    this.botClient = webSocketClient;
  }


}
