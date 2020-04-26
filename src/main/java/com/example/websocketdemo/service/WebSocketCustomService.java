package com.example.websocketdemo.service;

import com.example.websocketdemo.dto.WiselyMessage;

/**
 * @author gaochao
 * @create 2020-04-24 21:39
 */
public interface WebSocketCustomService {

  /**
   * 广播 发送给所有在线用户
   * @param msg
   */
   void sendMsg(WiselyMessage msg);

  /**
   * 发送给指定用户
   */
   void send2Users( WiselyMessage msg);
}
