package com.alinkeji.bot;

import com.alinkeji.bot.bot.Bot;
import com.alinkeji.bot.bot.BotFactory;
import com.alinkeji.bot.utils.SpringContextUtils;
import java.util.function.Consumer;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class BotGlobal {

  public static Map<String, Bot> bots = new ConcurrentHashMap<>();

  public static Bot get(String botId) {
    return bots.get(botId);
  }

  public static Bot add(String botId, Function<String, String> login) {
    String serverUrl = login.apply(botId);
    if (StringUtils.isBlank(serverUrl)) {
      return null;
    }
    return get(botId, factory -> {
      factory.injectHttp(botId, serverUrl);
    });
  }

  public static Bot get(String botId, Consumer<BotFactory> botFactory) {
    Bot bot = get(botId);
    if (bot != null) {
      return bot;
    }
    botFactory.accept(SpringContextUtils.getBean(BotFactory.class));
    return get(botId);
  }

  public static Bot get(String botId, Supplier<Bot> supplier) {
    Bot bot = get(botId);
    if (bot != null) {
      return bot;
    }
    return supplier.get();
  }
}
