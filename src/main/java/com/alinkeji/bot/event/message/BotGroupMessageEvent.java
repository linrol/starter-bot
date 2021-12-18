package com.alinkeji.bot.event.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.alinkeji.bot.entity.BotGroupAnonymous;
import com.alinkeji.bot.entity.BotGroupUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 群消息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BotGroupMessageEvent extends BotMessageEvent {

  /**
   * 消息子类型 正常消息是 normal 匿名消息是 anonymous 系统提示（如「管理员已禁止群内匿名聊天」）是 notice
   */
  @JSONField(name = "sub_type")
  private String subType;
  /**
   * 群号
   */
  @JSONField(name = "group_id")
  private long groupId;
  /**
   * 匿名信息 如果不是匿名消息则为 null
   */
  @JSONField(name = "anonymous")
  private BotGroupAnonymous anonymous;
  /**
   * 发送人信息
   */
  @JSONField(name = "sender")
  private BotGroupUser sender;
}
