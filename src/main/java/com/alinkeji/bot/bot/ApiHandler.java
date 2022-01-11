package com.alinkeji.bot.bot;

import com.alibaba.fastjson.JSONObject;

/**
 * API处理类
 */
public abstract class ApiHandler {

  public abstract JSONObject callApi(ApiEnum apiEnum, JSONObject params);

  public JSONObject callApi(IApiRequest apiRequest) {
    throw new UnsupportedOperationException("unsupported call api");
  }

  public void onReceiveApiMessage(JSONObject message) {
    throw new UnsupportedOperationException("unsupported receive api");
  }
}
