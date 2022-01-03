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
    try {
      return MVEL.evalToBoolean(key, this);
    } catch (Exception e) {
      return null;
    }
  }

  public Boolean getBooleanOrDefault(String key, boolean defaultValue) {
    Boolean value = getBoolean(key);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }

  public Integer getInteger(String key) {
    return get(key, Integer.class);
  }

  public String getString(String key) {
    try {
      return MVEL.evalToString(key, this);
    } catch (Exception e) {
      return null;
    }
  }

  public Object getObject(String key) {
    try {
      return MVEL.eval(key, this);
    } catch (Exception e) {
      return null;
    }
  }

  public <T> T get(String key, Class<T> clazz) {
    return MVEL.eval(key, this, clazz);
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
