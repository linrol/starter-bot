package com.alinkeji.bot.event.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.alinkeji.bot.event.BotEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BotMessageEvent extends BotEvent {

  /**
   * 消息类型
   */
  @JSONField(name = "message_type")
  private String messageType;
  /**
   * 消息 ID
   */
  @JSONField(name = "message_id")
  private int messageId;
  /**
   * 发送者 QQ 号
   */
  @JSONField(name = "user_id")
  private long userId;
  /**
   * 消息内容
   */
  @JSONField(name = "message")
  private String message;
  /**
   * 原始消息内容
   */
  @JSONField(name = "raw_message")
  private String rawMessage;
  /**
   * 字体
   */
  @JSONField(name = "font")
  private int font;
}
