package com.example.websocketdemo.config;

/**
 * @author gaochao
 * @create 2020-04-24 21:50
 */
public class Constant {

  //webSocket相关配置
  /**
   * 链接地址
   */
  public static String WEBSOCKETPATHPERFIX = "/ws-push";

  /**
   * endpoint地址:广播地址
   */
  public static String ENDPOINT_ALL = "/webServer";
  /**
   * endpoint地址:点对点地址
   */
  public static String ENDPOINT_PTOP = "/queueServer";
  /**
   * 消息代理路径
   */
  public static String WEBSOCKETBROADCASTPATH_TOPIC = "/topic";
  public static String WEBSOCKETBROADCASTPATH_QUEUE = "/queue";
  /**
   * 前端发送给服务端请求地址:点对点
   */
  public static final String PATH_P2P = "/queue";
  /**
   * 前端发送给服务端请求地址:广播
   */
  public static final String PATH_ALL = "/subscribe";
  /**
   * 服务端生产地址,客户端订阅此地址以接收服务端生产的消息
   */
  public static final String PRODUCERPATH = "/topic/getResponse";
  /**
   * 点对点消息推送地址前缀
   */
  public static final String P2PPUSHBASEPATH = "/user";
  /**
   * 点对点消息推送地址后缀,最后的地址为/user/用户识别码/msg
   */
  public static final String P2PPUSHPATH = "/message";

  /**
   * 用户连接的key
   */
  public static final String SESSION_USER = "SESSION_USER";

  public static final String WEBSOCKET_USER_KEY = "WEBSOCKET_USER_KEY";

  /**
   * header 中的参数信息
   */
  public static final String TOKEN_KEY = "token";
}
