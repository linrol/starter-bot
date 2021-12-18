package com.alinkeji.bot.event.meta;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 生命周期
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LifecycleMetaEvent extends MetaEvent {

  /**
   * 事件子类型 enable、disable、connect分别表示插件启用、插件停用、WebSocket 连接成功
   */
  @JSONField(name = "sub_type")
  private String subType;
}
