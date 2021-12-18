package com.alinkeji.bot.bot;

import com.alibaba.fastjson.JSONObject;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;


/**
 * 事件处理器 先根据 post_type 分类，消息/通知/请求/元事件 然后交给对应的继续分类 职责链模式调用插件，返回MESSAGE_BLOCK停止
 */
@Slf4j
public class EventHandler {

  private ApplicationContext applicationContext;

  private BotPlugin defaultPlugin = new BotPlugin();

  public EventHandler(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  public void handle(Bot bot, JSONObject eventJson) {
    String postType = eventJson.getString("post_type");
    switch (postType) {
      case "message": {
        handleMessage(bot, eventJson);
        break;
      }
      case "notice": {
        handleNotice(bot, eventJson);
        break;
      }
      case "request": {
        handleRequest(bot, eventJson);
        break;
      }
      case "meta_event": {
        handleMeta(bot, eventJson);
        break;
      }
    }
  }

  private void handleMessage(Bot bot, JSONObject eventJson) {
    String messageType = eventJson.getString("message_type");
    switch (messageType) {
      case "private": {
        BotPrivateMessageEvent event = eventJson.toJavaObject(BotPrivateMessageEvent.class);
        for (Class<? extends BotPlugin> pluginClass : bot.getPluginList()) {
          if (getPlugin(pluginClass).onPrivateMessage(bot, event) == BotPlugin.MESSAGE_BLOCK) {
            break;
          }
        }
        break;
      }
      case "group": {
        BotGroupMessageEvent event = eventJson.toJavaObject(BotGroupMessageEvent.class);
        for (Class<? extends BotPlugin> pluginClass : bot.getPluginList()) {
          if (getPlugin(pluginClass).onGroupMessage(bot, event) == BotPlugin.MESSAGE_BLOCK) {
            break;
          }
        }
        break;
      }
      case "discuss": {
        BotDiscussMessageEvent event = eventJson.toJavaObject(BotDiscussMessageEvent.class);
        for (Class<? extends BotPlugin> pluginClass : bot.getPluginList()) {
          if (getPlugin(pluginClass).onDiscussMessage(bot, event) == BotPlugin.MESSAGE_BLOCK) {
            break;
          }
        }
        break;
      }
    }

  }

  private void handleNotice(Bot bot, JSONObject eventJson) {

    String noticeType = eventJson.getString("notice_type");

    switch (noticeType) {
      case "group_upload": {
        BotGroupUploadNoticeEvent event = eventJson.toJavaObject(BotGroupUploadNoticeEvent.class);
        for (Class<? extends BotPlugin> pluginClass : bot.getPluginList()) {
          if (getPlugin(pluginClass).onGroupUploadNotice(bot, event) == BotPlugin.MESSAGE_BLOCK) {
            break;
          }
        }
        break;
      }
      case "group_admin": {
        BotGroupAdminNoticeEvent event = eventJson.toJavaObject(BotGroupAdminNoticeEvent.class);
        for (Class<? extends BotPlugin> pluginClass : bot.getPluginList()) {
          if (getPlugin(pluginClass).onGroupAdminNotice(bot, event) == BotPlugin.MESSAGE_BLOCK) {
            break;
          }
        }
        break;
      }
      case "group_decrease": {
        BotGroupDecreaseNoticeEvent event = eventJson.toJavaObject(
          BotGroupDecreaseNoticeEvent.class);
        for (Class<? extends BotPlugin> pluginClass : bot.getPluginList()) {
          if (getPlugin(pluginClass).onGroupDecreaseNotice(bot, event) == BotPlugin.MESSAGE_BLOCK) {
            break;
          }
        }
        break;
      }
      case "group_increase": {
        BotGroupIncreaseNoticeEvent event = eventJson.toJavaObject(
          BotGroupIncreaseNoticeEvent.class);
        for (Class<? extends BotPlugin> pluginClass : bot.getPluginList()) {
          if (getPlugin(pluginClass).onGroupIncreaseNotice(bot, event) == BotPlugin.MESSAGE_BLOCK) {
            break;
          }
        }
        break;
      }
      case "group_ban": {
        BotGroupBanNoticeEvent event = eventJson.toJavaObject(BotGroupBanNoticeEvent.class);
        for (Class<? extends BotPlugin> pluginClass : bot.getPluginList()) {
          if (getPlugin(pluginClass).onGroupBanNotice(bot, event) == BotPlugin.MESSAGE_BLOCK) {
            break;
          }
        }
        break;
      }
      case "friend_add": {
        BotFriendAddNoticeEvent event = eventJson.toJavaObject(BotFriendAddNoticeEvent.class);
        for (Class<? extends BotPlugin> pluginClass : bot.getPluginList()) {
          if (getPlugin(pluginClass).onFriendAddNotice(bot, event) == BotPlugin.MESSAGE_BLOCK) {
            break;
          }
        }
        break;
      }
    }


  }

  private void handleRequest(Bot bot, JSONObject eventJson) {
    String requestType = eventJson.getString("request_type");
    switch (requestType) {
      case "friend": {
        BotFriendRequestEvent event = eventJson.toJavaObject(BotFriendRequestEvent.class);
        for (Class<? extends BotPlugin> pluginClass : bot.getPluginList()) {
          if (getPlugin(pluginClass).onFriendRequest(bot, event) == BotPlugin.MESSAGE_BLOCK) {
            break;
          }
        }
        break;
      }
      case "group": {
        BotGroupRequestEvent event = eventJson.toJavaObject(BotGroupRequestEvent.class);
        for (Class<? extends BotPlugin> pluginClass : bot.getPluginList()) {
          if (getPlugin(pluginClass).onGroupRequest(bot, event) == BotPlugin.MESSAGE_BLOCK) {
            break;
          }
        }
        break;
      }
    }
  }

  private void handleMeta(Bot bot, JSONObject eventJson) {
    String metaType = eventJson.getString("meta_event_type");
    switch (metaType) {
      case "heartbeat": {
        HeartBeatMetaEvent event = eventJson.toJavaObject(HeartBeatMetaEvent.class);
        for (Class<? extends BotPlugin> pluginClass : bot.getPluginList()) {
          if (getPlugin(pluginClass).onHeartBeatMeta(bot, event) == BotPlugin.MESSAGE_BLOCK) {
            break;
          }
        }
        break;
      }
      case "lifecycle": {
        LifecycleMetaEvent event = eventJson.toJavaObject(LifecycleMetaEvent.class);
        for (Class<? extends BotPlugin> pluginClass : bot.getPluginList()) {
          if (getPlugin(pluginClass).onLifecycleMeta(bot, event) == BotPlugin.MESSAGE_BLOCK) {
            break;
          }
        }
        break;
      }
    }
  }

  private BotPlugin getPlugin(Class<? extends BotPlugin> pluginClass) {
    try {
      return applicationContext.getBean(pluginClass);
    } catch (Exception e) {
      log.error("已跳过 {} ，请检查 @Component", pluginClass.getSimpleName());
      return defaultPlugin;
    }
  }
}
