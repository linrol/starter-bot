package com.alinkeji.bot;

import com.alinkeji.bot.bot.Bot;
import com.alinkeji.bot.bot.BotFactory;
import com.alinkeji.bot.utils.SpringContextUtils;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;

public class BotGlobal {

  public static Map<String, Bot> bots = new ConcurrentHashMap<>();

  public static Bot get(String botId) {
    return bots.get(botId);
  }

  public static Bot get(String botId, Function<String, String> tryLogin) {
    Bot bot = get(botId);
    if (bot != null) {
      return bot;
    }
    String serverUrl = tryLogin.apply(botId);
    if (StringUtils.isBlank(serverUrl)) {
      return null;
    }
    SpringContextUtils.getBean(BotFactory.class).injectHttp(botId, Collections.singletonList(serverUrl));
    return get(botId);
  }
}
