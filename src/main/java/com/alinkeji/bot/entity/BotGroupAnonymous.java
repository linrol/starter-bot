package com.alinkeji.bot.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class BotGroupAnonymous {

  @JSONField(name = "id")
  private long id;
  @JSONField(name = "name")
  private String name;
  @JSONField(name = "flag")
  private String flag;
}