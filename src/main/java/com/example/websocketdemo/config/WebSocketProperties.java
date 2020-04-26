package com.example.websocketdemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * webSocket配置文件,自动注入
 * @author gaochao
 * @create 2020-04-24 21:28
 */
@Data
@Component
@ConfigurationProperties(prefix = "websocket")
public class WebSocketProperties {

  private String pathPrefix;
  private String path;
  private String broadCastPath;
  private String foreToServerPath;
  private String producerPath;
  private String p2pushBasePrefixPath;
  private String p2pushBaseSuffixPath;

}
