package com.alinkeji.bot.bot.handler;

import com.alibaba.fastjson.JSONObject;
import com.alinkeji.bot.bot.ApiEnum;
import com.alinkeji.bot.bot.ApiHandler;
import com.alinkeji.bot.utils.OkHttpClientUtil;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * API处理类
 */
@Slf4j
public class HttpApiHandler extends ApiHandler {

  private String botId;

  private List<String> botServerUrls;

  public HttpApiHandler(String botId, List<String> botServerUrls) {
    this.botId = botId;
    this.botServerUrls = botServerUrls;
  }

  @Override
  public JSONObject callApi(ApiEnum apiEnum, JSONObject params) {
    Stack<String> urlStack = botServerUrls.stream().collect(Collectors.toCollection(Stack::new));
    return callHttpApi(urlStack, apiEnum.getUrl(), params);
  }

  private JSONObject callHttpApi(Stack<String> urlStack, String action, JSONObject params) {
    String apiUrl = String.format(urlStack.pop(), botId, action);
    OkHttpClientUtil<JSONObject> callClient = OkHttpClientUtil.build(JSONObject.class);
    JSONObject apiResult;
    try {
      apiResult = callClient.post(apiUrl, params.toJSONString());
    } catch (Exception e) {
      e.printStackTrace();
      apiResult = new JSONObject();
      apiResult.put("status", "failed");
      apiResult.put("retcode", -1);
    }
    if (apiResult.containsKey("status") && apiResult.getString("status").equals("ok")) {
      return apiResult;
    }
    if (urlStack.isEmpty()) {
      return apiResult;
    }
    log.error("botId[{}] call api [{}] failed [{}]", botId, apiUrl, apiResult.toJSONString());
    return callHttpApi(urlStack, action, params);
  }
}
