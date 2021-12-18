package com.alinkeji.bot.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BotGroupUser extends BotUser {

  @JSONField(name = "card")
  private String card;
  @JSONField(name = "area")
  private String area;
  @JSONField(name = "level")
  private String level;
  @JSONField(name = "role")
  private String role;
  @JSONField(name = "title")
  private String title;
}
