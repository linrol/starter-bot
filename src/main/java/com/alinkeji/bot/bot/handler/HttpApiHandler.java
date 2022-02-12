package com.alinkeji.bot.bot.handler;

import com.alibaba.fastjson.JSONObject;
import com.alinkeji.bot.bot.ApiEnum;
import com.alinkeji.bot.bot.ApiHandler;
import com.alinkeji.bot.bot.ApiMethod;
import com.alinkeji.bot.bot.IApiRequest;
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

  class ApiRequest implements IApiRequest {

    @Override
    public String getApiUrl() {
      return null;
    }

    @Override
    public ApiMethod getApiMethod() {
      return null;
    }

    @Override
    public JSONObject getParams() {
      return null;
    }
  }

  @Override
  public JSONObject callApi(ApiEnum apiEnum, JSONObject params) {
    Stack<IApiRequest> requestStack = botServerUrls.stream().map(serverUrl -> {
      return new IApiRequest() {
        @Override
        public String getApiUrl() {
          return String.format(serverUrl, botId, apiEnum.getUrl());
        }

        @Override
        public ApiMethod getApiMethod() {
          return apiEnum.getApiMethod();
        }

        @Override
        public JSONObject getParams() {
          return params;
        }
      };
    }).collect(Collectors.toCollection(Stack::new));
    return callHttpApi(requestStack);
  }

  @Override
  public JSONObject callApi(IApiRequest apiRequest) {
    Stack<IApiRequest> requestStack = new Stack<>();
    requestStack.push(apiRequest);
    return callHttpApi(requestStack);
  }

  private JSONObject callHttpApi(Stack<IApiRequest> requestStack) {
    IApiRequest request = requestStack.pop();
    String apiUrl = request.getApiUrl();
    OkHttpClientUtil<JSONObject> callClient = OkHttpClientUtil.build(JSONObject.class);
    JSONObject apiResult;
    try {
      apiResult = callClient.post(apiUrl, request.getParams().toJSONString());
    } catch (Exception e) {
      e.printStackTrace();
      apiResult = new JSONObject();
      apiResult.put("status", "failed");
      apiResult.put("retcode", -1);
    }
    if (request.getApiResultPredicate().test(apiResult)) {
      return apiResult;
    }
    if (requestStack.isEmpty()) {
      return apiResult;
    }
    log.error("botId[{}] call api [{}] failed [{}]", botId, apiUrl, apiResult.toJSONString());
    return callHttpApi(requestStack);
  }
}
