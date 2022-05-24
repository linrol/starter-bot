package com.alinkeji.bot.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import org.springframework.util.StringUtils;

@Slf4j
public class OkHttpClientUtil<R> extends OkHttpClient {

  R r;

  Type type = new TypeReference<MapEx<String, Object>>() {
  }.getType();

  private static final MediaType JSON_MEDIA_TYPE = MediaType
    .parse("application/json; charset=utf-8");

  public OkHttpClientUtil() {
  }

  public OkHttpClientUtil(Type type) {
    this.type = type;
  }

  public static <R> OkHttpClientUtil<R> build(Type type) {
    return new OkHttpClientUtil<>(type);
  }

  public static <R> OkHttpClientUtil<R> build() {
    return new OkHttpClientUtil<>();
  }

  public R get(String url) {
    OkHttpClient client = new OkHttpClient().newBuilder().hostnameVerifier((hostname, session) -> {
      //强行返回true 即验证成功
      return true;
    }).build();
    Request request = new Request.Builder()
      .url(url)
      .get()
      .build();

    R r = request(client, request);
    log.debug("OkHttpClientUtil#get url[{}] result[{}]", url, r.toString());
    return r;
  }

  public R get(String url, JSONObject params) {
    if (params != null) {
      url += "?" + params.entrySet().stream().map(m -> m.getKey() + "=" + m.getValue())
        .collect(Collectors.joining("&"));
    }
    return get(url);
  }

  public R post(String url, String json) {
    OkHttpClient client = new OkHttpClient().newBuilder().hostnameVerifier((hostname, session) -> {
      //强行返回true 即验证成功
      return true;
    }).build();
    RequestBody requestBody = Util.EMPTY_REQUEST;
    if (!StringUtils.isEmpty(json)) {
      requestBody = RequestBody.create(JSON_MEDIA_TYPE, json);
    }
    Request request = new Request.Builder()
      .url(url)
      .post(requestBody)
      .build();

    log.debug("OkHttpClientUtil#post url[{}] body[{}]", url, json);
    R r = request(client, request);
    log.debug("OkHttpClientUtil#post url[{}] body[{}] result[{}]", url, json, r.toString());
    return r;
  }

  public R postForm(String url, Map<String, String> formData) {
    OkHttpClient client = new OkHttpClient().newBuilder().hostnameVerifier((hostname, session) -> {
      //强行返回true 即验证成功
      return true;
    }).build();
    FormBody.Builder builder = new FormBody.Builder();
    formData.forEach(builder::add);
    FormBody formBody = builder.build();
    Request request = new Request.Builder()
      .url(url)
      .post(formBody)
      .build();

    R r = request(client, request);
    log.debug("OkHttpClientUtil#postForm url[{}] body[{}] result[{}]", url, formData, r.toString());
    return r;
  }

  public R request(OkHttpClient client, Request request) {
    Response response = null;
    try {
      response = client.newCall(request).execute();
      if (response.isSuccessful()) {
        assert response.body() != null;
        return JSON.parseObject(response.body().string(), type);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("OkHttpClientUtil#request exception:" + e.getMessage());
    }
    throw new RuntimeException("OkHttpClientUtil#request status code:" + response.code());
  }
}
