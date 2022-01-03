package com.alinkeji.bot.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiEnum {
  SEND_PRIVATE_MSG("send_private_msg", "ws_reverse", "发送私聊消息"),
  SEND_GROUP_MSG("send_group_msg", "ws_reverse", "发送群消息"),
  SEND_DISCUSS_MSG("send_discuss_msg", "ws_reverse", "发送讨论组消息"),
  SEND_MSG("send_msg", "ws_reverse", "发送消息"),
  DELETE_MSG("delete_msg", "ws_reverse", "撤回消息"),
  SEND_LIKE("send_like", "ws_reverse", "发送好友赞"),
  SET_GROUP_KICK("set_group_kick", "ws_reverse", "群组踢人"),
  SET_GROUP_BAN("set_group_ban", "ws_reverse", "群组单人禁言"),
  SET_GROUP_ANONYMOUS_BAN("set_group_anonymous_ban", "ws_reverse", "群组匿名用户禁言"),
  SET_GROUP_WHOLE_BAN("set_group_whole_ban", "ws_reverse", "群组全员禁言"),
  SET_GROUP_ADMIN("set_group_admin", "ws_reverse", "群组设置管理员"),
  SET_GROUP_ANONYMOUS("set_group_anonymous", "ws_reverse", "群组匿名"),
  SET_GROUP_CARD("set_group_card", "ws_reverse", "设置群名片（群备注）"),
  SET_GROUP_LEAVE("set_group_leave", "ws_reverse", "退出群组"),
  SET_GROUP_SPECIAL_TITLE("set_group_special_title", "ws_reverse", "设置群组专属头衔"),
  SET_DISCUSS_LEAVE("set_discuss_leave", "ws_reverse", "退出讨论组"),
  SET_FRIEND_ADD_REQUEST("set_friend_add_request", "ws_reverse", "处理加好友请求"),
  SET_GROUP_ADD_REQUEST("set_group_add_request", "ws_reverse", "处理加群请求／邀请"),
  GET_LOGIN_INFO("get_login_info", "ws_reverse", "获取登录号信息"),
  GET_STRANGER_INFO("get_stranger_info", "ws_reverse", "获取陌生人信息"),
  GET_FRIEND_LIST("get_friend_list", "ws_reverse", "获取好友列表"),
  GET_GROUP_LIST("get_group_list", "ws_reverse", "获取群列表"),
  GET_GROUP_INFO("get_group_info", "ws_reverse", "获取群信息"),
  GET_GROUP_MEMBER_INFO("get_group_member_info", "ws_reverse", "获取群成员信息"),
  GET_GROUP_MEMBER_LIST("get_group_member_list", "ws_reverse", "获取群成员列表"),
  GET_COOKIES("get_cookies", "ws_reverse", "获取 Cookies"),
  GET_CSRF_TOKEN("get_csrf_token", "ws_reverse", "获取 CSRF Token"),
  GET_CREDENTIALS("get_credentials", "ws_reverse", "获取 QQ 相关接口凭证"),
  GET_RECORD("get_record", "ws_reverse", "获取语音"),
  GET_IMAGE("get_image", "ws_reverse", "获取图片"),
  CAN_SEND_IMAGE("can_send_image", "ws_reverse", "检查是否可以发送图片"),
  CAN_SEND_RECORD("can_send_record", "ws_reverse", "检查是否可以发送语音"),
  GET_STATUS("get_status", "ws_reverse", "获取插件运行状态"),
  GET_VERSION_INFO("get_version_info", "ws_reverse", "获取 酷Q 及 HTTP API 插件的版本信息"),
  SET_RESTART_PLUGIN("set_restart_plugin", "ws_reverse", "重启 HTTP API 插件"),
  CLEAN_DATA_DIR("clean_data_dir", "ws_reverse", "清理数据目录"),
  CLEAN_PLUGIN_LOG("clean_plugin_log", "ws_reverse", "清理插件日志");

  private String url;
  private String method;
  private String desc;
}
