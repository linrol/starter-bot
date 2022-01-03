package com.alinkeji.bot.retdata;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class MessageData {

  @JSONField(name = "message_id")
  private Object messageId;
}
