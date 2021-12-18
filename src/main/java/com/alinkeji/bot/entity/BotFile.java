package com.alinkeji.bot.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class BotFile {

  @JSONField(name = "id")
  private String id;
  @JSONField(name = "name")
  private String name;
  @JSONField(name = "size")
  private long size;
  @JSONField(name = "busid")
  private long busid;
}
