package com.alinkeji.bot.utils;

import com.alibaba.fastjson.JSON;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.mvel2.MVEL;
import org.mvel2.PropertyAccessException;

public class MapEx<K, V> extends HashMap<K, V> {

  public Boolean getBoolean(String key) {
    return get(key, Boolean.class);
  }

  public Boolean getBooleanOrDefault(String key, boolean defaultValue) {
    return get(key, Boolean.class, defaultValue);
  }

  public Integer getInteger(String key) {
    return get(key, Integer.class);
  }

  public String getString(String key) {
    return get(key, String.class);
  }

  public Object getObject(String key) {
    return get(key, Object.class);
  }

  public <T> T get(String key, Class<T> clazz) {
    return get(key, clazz, null);
  }

  public <T> T get(String key, Class<T> clazz, T defaultValue) {
    try {
      return MVEL.eval(key, this, clazz);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  public <T> List<T> getList(String key, Type type) {
    List<Object> list = (List<Object>) super.get(key);
    if (list == null) {
      return Collections.emptyList();
    }
    return list.stream().map(m -> {
      return JSON.<T>parseObject(JSON.toJSONString(m), type);
    }).collect(Collectors.toList());
  }

  public boolean containsKey(String key) {
    try {
      MVEL.eval(key, this);
      return true;
    } catch (PropertyAccessException e) {
      return false;
    }
  }

}
