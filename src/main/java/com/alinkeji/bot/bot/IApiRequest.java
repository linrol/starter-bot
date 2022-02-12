package com.alinkeji.bot.bot;

import com.alibaba.fastjson.JSONObject;
import java.util.function.Predicate;

/**
 * 自定义API可以实现这个接口 使用bot.callCustomApi(IApiRequest apiRequest)
 */
public interface IApiRequest {

  String getApiAction();

  ApiMethod getApiMethod();

  JSONObject getParams();

  default Predicate<JSONObject> getApiResultPredicate() {
    return apiResult -> {
      return apiResult.containsKey("status") && apiResult.getString("status").equals("ok");
    };
  }
}
