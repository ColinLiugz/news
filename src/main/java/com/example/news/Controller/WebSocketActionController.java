package com.example.news.Controller;

import com.example.news.WebSocket.ClientMessage;
import com.example.news.WebSocket.ServerMessage;
import io.swagger.annotations.Api;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Colin
 * @Date: 2018/6/9 0:01
 */
@Api(value = "WebSocketAction", description = "webSocket测试")
@RestController
@CrossOrigin
public class WebSocketActionController {
    private static final org.apache.commons.logging.Log log = LogFactory.getLog(WebSocketActionController.class);

    @MessageMapping("/sendTest")
    @SendTo("/topic/subscribeTest")
    public ServerMessage sendDemo(ClientMessage message) {
        log.info("接收到了信息" + message.getName());
        return new ServerMessage("你发送的消息为:" + message.getName());
    }


    @SubscribeMapping("/subscribeTest")
    public ServerMessage sub() {
        log.info("XXX用户订阅了我。。。");
        return new ServerMessage("感谢你订阅了我。。。");
    }
}
