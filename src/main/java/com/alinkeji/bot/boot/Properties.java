package com.alinkeji.bot.boot;

import com.alinkeji.bot.bot.BotPlugin;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bot")
public class Properties {

  @Getter
  @Setter
  private List<String> httpUrl = Arrays.asList("http://oicq-%s:5700/%s", "http://go-cqhttp-%s:5700/%s");
  @Getter
  @Setter
  private String wsReverseUrl = "/ws/*/";
  @Getter
  @Setter
  private Map<String, Object> wxUrl = Collections.emptyMap();
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
  private Map<String, String> apiMethod = Collections.emptyMap();
  @Getter
  @Setter
  List<Class<? extends BotPlugin>> pluginList = new ArrayList<>();

}
