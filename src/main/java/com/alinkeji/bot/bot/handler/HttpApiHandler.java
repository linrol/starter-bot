package com.alinkeji.bot.bot.handler;

import com.alibaba.fastjson.JSONObject;
import com.alinkeji.bot.bot.ApiHandler;
import com.alinkeji.bot.bot.IApiRequest;
import com.alinkeji.bot.utils.OkHttpClientUtil;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * API处理类
 */
@Slf4j
public class HttpApiHandler extends ApiHandler {

  private String botId;

  private Set<String> botServerUrls;

  public HttpApiHandler(String botId) {
    this.botId = botId;
    this.botServerUrls = new HashSet<>();
  }

  public HttpApiHandler(String botId, Set<String> serverUrls) {
    this.botId = botId;
    this.botServerUrls = serverUrls;
  }

  @Override
  public ApiHandler addServerUrls(String... serverUrl) {
    botServerUrls.addAll(Arrays.asList(serverUrl));
    return this;
  }

  @Override
  public JSONObject callApi(IApiRequest request) {
    Stack<String> serverUrlStack = botServerUrls.stream().collect(Collectors.toCollection(Stack::new));
    return callHttpApi(serverUrlStack, request);
  }

  private JSONObject callHttpApi(Stack<String> serverUrlStack, IApiRequest request) {
    String serverUrl = serverUrlStack.pop();
    String apiUrl = serverUrl + StringUtils.defaultIfBlank(request.getApiAction(), "");
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
    if (serverUrlStack.isEmpty()) {
      return apiResult;
    }
    log.error("botId[{}] call api [{}] failed [{}]", botId, apiUrl, apiResult.toJSONString());
    return callHttpApi(serverUrlStack, request);
  }
}
