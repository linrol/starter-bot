package com.alinkeji.bot.event.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.alinkeji.bot.entity.BotUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 讨论组消息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BotDiscussMessageEvent extends BotMessageEvent {

  /**
   * 讨论组 ID
   */
  @JSONField(name = "discuss_id")
  private long discussId;
  /**
   * 发送人信息
   */
  @JSONField(name = "sender")
  private BotUser sender;
}
