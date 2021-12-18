package com.alinkeji.bot.retdata;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class CookiesData {

  @JSONField(name = "cookies")
  private String cookies;
}
