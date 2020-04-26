package com.example.websocketdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * 配置websocket
 * @author gaochao
 * @create 2020-04-24 21:25
 */
// @EnableWebSocketMessageBroker注解用于开启使用STOMP协议来传输基于代理（MessageBroker）的消息，这时候控制器（controller）
// 开始支持@MessageMapping,就像是使用@requestMapping一样。
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig /*extends AbstractWebSocketMessageBrokerConfigurer*/ implements WebSocketMessageBrokerConfigurer {

  @Autowired
  private UserInterceptor interceptor;

  /**
   * 注册stomp的端点
   */
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    //注册一个Stomp的节点（endpoint,前端连接的时候要指定对应的地址）,并指定使用SockJS协议,允许跨域。
    registry.addEndpoint(Constant.ENDPOINT_ALL)
            .setAllowedOrigins("*")
            .withSockJS();

    registry.addEndpoint(Constant.ENDPOINT_PTOP)
            .setAllowedOrigins("*")
            .withSockJS();

  }

  /**
   * 配置信息代理
   * 如果不重载它的话，将会自动配置一个简单的内存消息代理，用它来处理以"/topic"为前缀的消息
   */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    //服务端发送消息给客户端的域,多个用逗号隔开;订阅Broker名称; 基于内存的STOMP消息代理 //topic用来广播，user用来实现p2p
    registry.enableSimpleBroker(Constant.WEBSOCKETBROADCASTPATH_TOPIC, Constant.P2PPUSHBASEPATH);

    //基于RabbitMQ 的STOMP消息代理
/*        registry.enableStompBrokerRelay(Constant.WEBSOCKETBROADCASTPATH_TOPIC, Constant.WEBSOCKETBROADCASTPATH_QUEUE)
                .setRelayHost(host)
                .setRelayPort(port)
                .setClientLogin(userName)
                .setClientPasscode(password);*/

    //定义一对一推送的时候前缀点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user
    registry.setUserDestinationPrefix(Constant.P2PPUSHBASEPATH);
    //定义websoket前缀,请求@MessageMapping中的地址的时候需要加上该前缀 全局使用的消息前缀（客户端订阅路径上会体现出来）
    registry.setApplicationDestinationPrefixes(Constant.WEBSOCKETPATHPERFIX);
    //registry.setApplicationDestinationPrefixes("/app", "/foo");
  }

  @Override
  public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
    registry.setMessageSizeLimit(500 * 1024 * 1024);
    registry.setSendBufferSizeLimit(1024 * 1024 * 1024);
    registry.setSendTimeLimit(200000);
  }

  /**
   * 配置客户端入站通道拦截器
   * @param registration
   */
  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    //registration.interceptors(interceptor);
  }
}
