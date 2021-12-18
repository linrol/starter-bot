package com.alinkeji.bot.boot;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "bot.event")
public class EventProperties {

  private Integer corePoolSize = 5;
  private Integer maxPoolSize = 20;
  private Integer keepAliveTime = 2000;
  private Integer workQueueSize = 512;
}
