package com.example.websocketdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接受前端消息实体
 * @author gaochao
 * @create 2020-04-24 21:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WiselyMessage {

  /**
   * 需要发送到目标用户的用户名
   */
  private String name;

  /**
   * 发送内容
   */
  private String context;

}
