package com.alinkeji.bot.bot;

import com.alibaba.fastjson.JSONObject;

/**
 * 自定义API可以实现这个接口 使用bot.callCustomApi(IApiRequest apiRequest)
 */
public interface IApiRequest {

  String getUrl();

  JSONObject getParams();
}
