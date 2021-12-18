package com.alinkeji.bot.event.meta;

import com.alibaba.fastjson.annotation.JSONField;
import com.alinkeji.bot.event.BotEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MetaEvent extends BotEvent {

  /**
   * heartbeat	元事件类型
   */
  @JSONField(name = "meta_event_type")
  private String metaEventType;
}
