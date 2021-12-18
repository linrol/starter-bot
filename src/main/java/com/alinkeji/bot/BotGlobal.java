package com.alinkeji.bot;

import com.alinkeji.bot.bot.Bot;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BotGlobal {

  public static Map<String, Bot> bots = new ConcurrentHashMap<>();
}
