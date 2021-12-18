package com.alinkeji.bot.bot;

import com.alinkeji.bot.event.message.BotDiscussMessageEvent;
import com.alinkeji.bot.event.message.BotGroupMessageEvent;
import com.alinkeji.bot.event.message.BotPrivateMessageEvent;
import com.alinkeji.bot.event.meta.HeartBeatMetaEvent;
import com.alinkeji.bot.event.meta.LifecycleMetaEvent;
import com.alinkeji.bot.event.notice.BotFriendAddNoticeEvent;
import com.alinkeji.bot.event.notice.BotGroupAdminNoticeEvent;
import com.alinkeji.bot.event.notice.BotGroupBanNoticeEvent;
import com.alinkeji.bot.event.notice.BotGroupDecreaseNoticeEvent;
import com.alinkeji.bot.event.notice.BotGroupIncreaseNoticeEvent;
import com.alinkeji.bot.event.notice.BotGroupUploadNoticeEvent;
import com.alinkeji.bot.event.request.BotFriendRequestEvent;
import com.alinkeji.bot.event.request.BotGroupRequestEvent;

public class BotPlugin {

  public static final int MESSAGE_BLOCK = 1;
  public static final int MESSAGE_IGNORE = 0;

  /**
   * 收到私聊消息时调用此方法
   *
   * @param bot   机器人对象
   * @param event 事件内容
   * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
   */
  public int onPrivateMessage(Bot bot, BotPrivateMessageEvent event) {
    return MESSAGE_IGNORE;
  }

  /**
   * 收到群消息时调用此方法
   *
   * @param bot   机器人对象
   * @param event 事件内容
   * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
   */
  public int onGroupMessage(Bot bot, BotGroupMessageEvent event) {
    return MESSAGE_IGNORE;
  }

  /**
   * 收到讨论组消息时调用此方法
   *
   * @param bot   机器人对象
   * @param event 事件内容
   * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
   */
  public int onDiscussMessage(Bot bot, BotDiscussMessageEvent event) {
    return MESSAGE_IGNORE;
  }

  /**
   * 群内有文件上传时调用此方法 仅群文件上传表现为事件，好友发送文件在 酷Q 中没有独立的事件，而是直接表现为好友消息，请注意在编写业务逻辑时进行判断。
   *
   * @param bot   机器人对象
   * @param event 事件内容
   * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
   */
  public int onGroupUploadNotice(Bot bot, BotGroupUploadNoticeEvent event) {
    return MESSAGE_IGNORE;
  }

  /**
   * 群管理员变动时调用此方法
   *
   * @param bot   机器人对象
   * @param event 事件内容
   * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
   */
  public int onGroupAdminNotice(Bot bot, BotGroupAdminNoticeEvent event) {
    return MESSAGE_IGNORE;
  }

  /**
   * 群成员减少时调用此方法
   *
   * @param bot   机器人对象
   * @param event 事件内容
   * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
   */
  public int onGroupDecreaseNotice(Bot bot, BotGroupDecreaseNoticeEvent event) {
    return MESSAGE_IGNORE;
  }

  /**
   * 群成员增加时调用此方法
   *
   * @param bot   机器人对象
   * @param event 事件内容
   * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
   */
  public int onGroupIncreaseNotice(Bot bot, BotGroupIncreaseNoticeEvent event) {
    return MESSAGE_IGNORE;
  }

  /**
   * 群禁言时调用此方法
   *
   * @param bot   机器人对象
   * @param event 事件内容
   * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
   */
  public int onGroupBanNotice(Bot bot, BotGroupBanNoticeEvent event) {
    return MESSAGE_IGNORE;
  }

  /**
   * 好友添加时调用此方法
   *
   * @param bot   机器人对象
   * @param event 事件内容
   * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
   */
  public int onFriendAddNotice(Bot bot, BotFriendAddNoticeEvent event) {
    return MESSAGE_IGNORE;
  }

  /**
   * 加好友请求时调用此方法
   *
   * @param bot   机器人对象
   * @param event 事件内容
   * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
   */
  public int onFriendRequest(Bot bot, BotFriendRequestEvent event) {
    return MESSAGE_IGNORE;
  }

  /**
   * 加群请求/邀请时调用此方法
   *
   * @param bot   机器人对象
   * @param event 事件内容
   * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
   */
  public int onGroupRequest(Bot bot, BotGroupRequestEvent event) {
    return MESSAGE_IGNORE;
  }

  /**
   * 收到心跳包时调用此方法 心跳类型的元事件需要通过设置配置项 enable_heartbeat 为 true 开启，并可通过 heartbeat_interval 配置心跳间隔（单位毫秒）。
   *
   * @param bot   机器人对象
   * @param event 事件内容
   * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
   */
  public int onHeartBeatMeta(Bot bot, HeartBeatMetaEvent event) {
    return MESSAGE_IGNORE;
  }

  public int onLifecycleMeta(Bot bot, LifecycleMetaEvent event) {
    return MESSAGE_IGNORE;
  }
}
