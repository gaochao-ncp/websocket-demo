package com.example.websocketdemo.controller;

import com.example.websocketdemo.config.Constant;
import com.example.websocketdemo.dto.User;
import com.example.websocketdemo.dto.WiselyMessage;
import com.example.websocketdemo.service.WebSocketCustomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * github demo地址为: https://github.com/JMCuixy/SpringWebSocket.git
 * @author gaochao
 * @create 2020-04-24 21:45
 */
@Controller
@Slf4j
public class WsController {

  @Resource
  private WebSocketCustomService webSocketService;

  /**
   * @MessageMapping 和@RequestMapping功能类似，用于设置URL映射地址，浏览器向服务器发起请求，需要通过该地址。
   *
   * @SendToUser 表示要将消息发送给指定的用户，会自动在消息目的地前补上"/user"前缀。如下，最后消息会被发布在  /user/queue/notifications-username。
   * 但是问题来了，这个username是怎么来的呢？就是通过 principal 参数来获得的。
   * 那么，principal 参数又是怎么来的呢？需要在spring-websocket 的配置类中重写 configureClientInboundChannel 方法，添加上用户的认证。
   */
  @MessageMapping(Constant.PATH_P2P)
  //@SendToUser(Constant.PRODUCERPATH)//如果服务器接受到了消息(前端需要订阅)，就会对订阅了@SendTo括号中的地址传送消息。
  public void send2Users(/**StompHeaderAccessor stompHeaderAccessor,**/ WiselyMessage message) {
    /**
     * 把用户名的当作一个参数传递给控制器方法，从而绕过身份认证
     * User principal = (User) stompHeaderAccessor.getUser();
     *     List<User> users = new ArrayList<>();
     *     users.add(principal);
     */
    webSocketService.send2Users(message);
  }

  @MessageMapping(Constant.PATH_ALL)
  public void sendAll(StompHeaderAccessor stompHeaderAccessor,WiselyMessage message){
    User principal = (User) stompHeaderAccessor.getUser();
    log.info("进入广播方法");
    webSocketService.sendMsg(message);
  }


  @MessageExceptionHandler(Exception.class)
  @SendToUser("/queue/errors")
  public Exception handleExceptions(Exception t){
    t.printStackTrace();
    return t;
  }

}
