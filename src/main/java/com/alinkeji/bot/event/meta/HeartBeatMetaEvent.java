package com.alinkeji.bot.event.meta;

import com.alibaba.fastjson.annotation.JSONField;
import com.alinkeji.bot.entity.BotStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 心跳
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HeartBeatMetaEvent extends MetaEvent {

  /**
   * 状态信息
   */
  @JSONField(name = "status")
  private BotStatus status;

  /**
   * 到下次心跳的间隔，单位毫秒
   */
  @JSONField(name = "interval")
  private Long interval;
}
