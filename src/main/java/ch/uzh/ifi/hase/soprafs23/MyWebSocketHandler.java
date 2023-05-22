package ch.uzh.ifi.hase.soprafs23;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 处理接收到的文本消息
        String payload = message.getPayload();
        // ...
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 在建立WebSocket连接后执行的逻辑
        // ...
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 在关闭WebSocket连接后执行的逻辑
        // ...
    }
}

