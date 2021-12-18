package com.alinkeji.bot.boot;

import com.alinkeji.bot.bot.BotPlugin;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bot")
public class Properties {

  @Getter
  @Setter
  private String url = "/ws/*/";
  @Getter
  @Setter
  private Integer maxTextMessageBufferSize = 512000;
  @Getter
  @Setter
  private Integer maxBinaryMessageBufferSize = 512000;
  @Getter
  @Setter
  private Long maxSessionIdleTimeout = 15 * 60000L;
  @Getter
  @Setter
  private Long apiTimeout = 120000L;
  @Getter
  @Setter
  List<Class<? extends BotPlugin>> pluginList = new ArrayList<>();

}