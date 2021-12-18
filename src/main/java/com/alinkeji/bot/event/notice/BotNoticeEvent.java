package com.alinkeji.bot.event.notice;

import com.alibaba.fastjson.annotation.JSONField;
import com.alinkeji.bot.event.BotEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BotNoticeEvent extends BotEvent {

  /**
   * 通知类型
   */
  @JSONField(name = "notice_type")
  private String noticeType;
  /**
   * 上传文件者QQ 被任命管理员QQ 进群/离开者QQ 被禁言QQ 新添加好友 QQ 号
   */
  @JSONField(name = "user_id")
  private long userId;
}
