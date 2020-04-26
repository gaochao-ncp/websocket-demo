package com.example.websocketdemo.config;

import com.example.websocketdemo.dto.User;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

/**
 * 3 定义用户拦截器。
 * 这里preSend里当连接方式为CONNECT的时候获取session里的用户信息，注入stompHeaderAccessor。
 * 注意一点的是用户类需要实现java.security.Principal。preSend有很多连接方式，包括DISCONNECT,SUBSCRIBE，DISSUBSCRIBE，可以用这么连接方式监控用户的上线下线，统计每个订阅的在线人数等等，大家可以自行想象。
 * 通过认证的用户，使用 accessor.setUser(user); 方法，将登陆信息绑定在该 StompHeaderAccessor 上，
 * 在Controller方法上可以获取 StompHeaderAccessor 的相关信息
 *
 * 这里虽然我还是用了认证的信息得到用户名。但是，其实大可不必这样，因为 convertAndSendToUser 方法可以指定要发送给哪个用户。也就是说，完全可以把用户名的当作一个参数传递给控制器方法，从而绕过身份认证！convertAndSendToUser 方法最终会把消息发送到 /user/sername/queue/shouts 目的地上。
 * 在实际生产环境中,token的验证交给uaa工程进行验证了,因此需要改动业务逻辑,这个拦截器可以忽略
 *
 * @create 2020-04-26 10:14
 */
@Component
public class UserInterceptor implements ChannelInterceptor {

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
    //1、判断是否首次连接
    if(StompCommand.CONNECT.equals(accessor.getCommand())){
      //2、进行验证授权 从nativeHeaders中获取参数信息
      String token = accessor.getNativeHeader(Constant.TOKEN_KEY).get(0);
      //if (raw instanceof Map) {
      if (token != null) {
        //TODO 这里就是token,集成到电商项目中的时候需要从token中解析参数信息
        User user = new User();
          user.setUserName(token);
          accessor.setUser(user);
          return message;
//        if (name instanceof LinkedList) {
          // 设置当前访问器的认证用户
//          String token = ((LinkedList) name).get(0).toString();
//          String username = null;
//          try {
//            Map<String, Claim> claimMap = JWTUtils.verifyToken(token);
//            username = claimMap.get("username").asString();
//            if(username == null){
//              throw new RuntimeException("websocket认证失败");
//            }
//          } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            throw new RuntimeException("websocket认证失败", e);
//          } catch (ValidTokenException e) {
//            e.printStackTrace();
//            throw new RuntimeException("websocket认证失败", e);
//          }
//          User user = new User();
//          user.setUserName("username");
//          accessor.setUser(user);
//        }
      }else {
        //认证失败,返回null
        return null;
      }
    }
    //不是首次连接，已经登陆成功
    return message;
  }

  @Override
  public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

  }

  @Override
  public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {

  }

  @Override
  public boolean preReceive(MessageChannel channel) {
    return false;
  }

  @Override
  public Message<?> postReceive(Message<?> message, MessageChannel channel) {
    return null;
  }

  @Override
  public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {

  }
}
