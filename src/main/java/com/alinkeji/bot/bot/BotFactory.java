package com.alinkeji.bot.bot;

import com.alinkeji.bot.boot.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

/**
 * 工厂类
 */
@Service
public class BotFactory {

  @Autowired
  private ApiHandler apiHandler;
  @Autowired
  private Properties properties;


  /**
   * 创建一个Bot对象 把spring容器中的apiHandler放入对象
   *
   * @param selfId     机器人自己的QQ号
   * @param botSession ws的session
   * @return Bot对象
   */
  public Bot createBot(Long selfId, WebSocketSession botSession) {
    return new Bot(selfId, botSession, apiHandler, properties.getPluginList());
  }
}
