package com.example.websocketdemo.service.impl;

import com.example.websocketdemo.config.Constant;
import com.example.websocketdemo.dto.WiselyMessage;
import com.example.websocketdemo.service.WebSocketCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author gaochao
 * @create 2020-04-24 21:40
 */
@Service
public class WebSocketCustomServiceImpl implements WebSocketCustomService {

  /**
   * 客户端主动发送消息到服务端，服务端马上回应指定的客户端消息 类似http无状态请求，但是有质的区别
   * websocket可以从服务器指定发送哪个客户端，而不像http只能响应请求端
   */
  @Autowired
  private SimpMessagingTemplate template;

  @Override
  public void sendMsg(WiselyMessage msg) {
    //广播使用convertAndSend方法，第一个参数为目的地，和js中订阅的目的地要一致
    template.convertAndSend(Constant.PRODUCERPATH, msg);
  }

  @Override
  public void send2Users(WiselyMessage msg) {

    if(msg != null){
      /*广播使用convertAndSendToUser方法，第一个参数为用户id，此时js中的订阅地址为
            "/user/" + 用户Id + "/msg",其中"/user"是固定的.
            /user/msg.getName()/Constant.P2PPUSHPATH
      */
      template.convertAndSendToUser(msg.getName(), Constant.P2PPUSHPATH, msg.getContext());
    }

  }
}
