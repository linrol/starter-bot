package com.alinkeji.bot.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class BotUser {

  @JSONField(name = "user_id")
  private long userId;
  @JSONField(name = "nickname")
  private String nickname;
  @JSONField(name = "sex")
  private String sex;
  @JSONField(name = "age")
  private int age;
}
