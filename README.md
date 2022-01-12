# starter-bot
[![maven](https://img.shields.io/maven-central/v/net.lz1998/spring-cq)](https://search.maven.org/artifact/com.alinkeji/starter-bot)
[![QQ群](https://img.shields.io/static/v1?label=QQ%E7%BE%A4&message=606160828&color=blue)](https://jq.qq.com/?_wv=1027&k=5BKAROL)

- 基于 go-cqhttp、oicq、wechat-bot的QQ和WeChat机器人框架（支持正向，反向websocket以及http 多种通信方式）

- 这是一个自定义spring-boot-starter，开箱即用

## 开发环境
- IntelliJ IDEA
- IntelliJ IDEA中的lombok插件，File->Settings->Plugins->搜索Lombok->Install->重启IDEA
- JDK IDEA自动安装，不需要自己装
- MAVEN IDEA自动安装，不需要自己装

## 导入maven依赖
```xml
    <dependency>
        <groupId>com.alinkeji</groupId>
        <artifactId>starter-bot</artifactId>
        <version>1.2.2</version>
    </dependency>
```

推荐SpringBoot 最新版即可

## 配置说明
- 配置resources/application.yml
    ```yml
    server:
      port: 8081 # 下面的cqhttp都是8081端口，可以自己改

    bot:
      # 在这里配置具体API的通信方式，可选配置:WsReverse、Ws、Http 默认为WsReverse，
      api-method:
        send_group_msg:
          http

      # 在这里配置机器人通信地址
      http-server:
        botId:
          http://oicq-%s:5700/%s,http://go-cqhttp-%s:5700/%s
      ws-server:
        botId:
          ws://ws-server:5555
      ws-reverse-url:
        /ws/*/

      # 在这里配置各个插件执行顺序
      # 如果前一个功能返回MESSAGE_BLOCK，下一个功能不会被执行
      # 如果前一个功能返回MESSAGE_IGNORE，会继续执行下一个功能
      plugin-list:
        - com.example.demo.plugin.DemoPlugin
        - com.example.demo.plugin.TestPlugin
        - com.example.demo.plugin.HelloPlugin
    ```

## 编写插件
- 编写XXXPlugin插件，继承BotPlugin
    ```java
   /**
    * 示例插件
    * 插件必须继承BotPlugin，上面要 @Component
    *
    * 添加事件：光标移动到类中，按 Ctrl+O 添加事件(讨论组消息、加群请求、加好友请求等)
    * 查看API参数类型：光标移动到方法括号中按Ctrl+P
    * 查看API说明：光标移动到方法括号中按Ctrl+Q
    */
   @Component
   public class DemoPlugin extends BotPlugin {
       /**
        * 收到私聊消息时会调用这个方法
        *
        * @param bot    机器人对象，用于调用API，例如发送私聊消息 sendPrivateMsg
        * @param event  事件对象，用于获取消息内容、群号、发送者QQ等
        * @return 是否继续调用下一个插件，IGNORE表示继续，BLOCK表示不继续
        */
       @Override
       public int onPrivateMessage(Bot bot, BotPrivateMessageEvent event) {
           // 获取 发送者QQ 和 消息内容
           long userId = event.getUserId();
           String msg = event.getMessage();
   
           if (msg.equals("hi")) {
               // 调用API发送hello
               bot.sendPrivateMsg(userId, "hello", false);
   
               // 不执行下一个插件
               return MESSAGE_BLOCK;
           }
           // 继续执行下一个插件
           return MESSAGE_IGNORE;
       }
   
   
       /**
        * 收到群消息时会调用这个方法
        *
        * @param bot    机器人对象，用于调用API，例如发送群消息 sendGroupMsg
        * @param event  事件对象，用于获取消息内容、群号、发送者QQ等
        * @return 是否继续调用下一个插件，IGNORE表示继续，BLOCK表示不继续
        */
       @Override
       public int onGroupMessage(Bot bot, BotGroupMessageEvent event) {
           // 获取 消息内容 群号 发送者QQ
           String msg = event.getMessage();
           long groupId = event.getGroupId();
           long userId = event.getUserId();
   
           if (msg.equals("hello")) {
               // 回复内容为 at发送者 + hi
               String result = BotCode.at(userId) + "hi";
   
               // 调用API发送消息
               bot.sendGroupMsg(groupId, result, false);
   
               // 不执行下一个插件
               return MESSAGE_BLOCK;
           }
   
           // 继续执行下一个插件
           return MESSAGE_IGNORE;
       }
   }
    ```