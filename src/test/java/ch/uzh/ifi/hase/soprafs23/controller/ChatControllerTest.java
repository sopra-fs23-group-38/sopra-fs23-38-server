package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.service.AnswerService;
import ch.uzh.ifi.hase.soprafs23.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
//import org.springframework.messaging.converter.MappingJackson2MessageConverter;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnabledOnOs({OS.WINDOWS, OS.MAC})  //cloud runs on linux therefore this is only runs locally
public class ChatControllerTest {
    final static String serverURL = "http://localhost:8080";
    // final static String serverURL = "https://sopra-fs23-group-08-server.oa.r.appspot.com";
    static final String serverWsURL = "ws://localhost:8080/my-websocket";
    // static final String serverWsURL = "ws://sopra-fs23-group-08-server.oa.r.appspot.com/sopra-websocket";
    @LocalServerPort
    private Integer port;
    private StompSession session;

    private AnswerService answerService;
    private QuestionService questionService;
    private String gameId;
    private StompSession sessionString;

    static public StompSession createStompSession(MessageConverter converter)
            throws InterruptedException, ExecutionException, TimeoutException {

        Exception error = null;
        for (int x = 0; x < 3; x++) {
            try {
                var webSocketStompClient = new WebSocketStompClient(new SockJsClient(
                        List.of(new WebSocketTransport(new StandardWebSocketClient()))));

                webSocketStompClient.setMessageConverter(converter);

                var stompSession = webSocketStompClient
                        .connect(serverWsURL, new StompSessionHandlerAdapter() {
                        })
                        .get(1, TimeUnit.SECONDS);
                return stompSession;
            }
            catch (Exception e) {
                error = e;
            }
        }
        throw new IllegalStateException("Connection failed", error);
    }



    @BeforeEach
    public void setup() throws IOException, InterruptedException, ExecutionException, TimeoutException {

        session = createStompSession(new MappingJackson2MessageConverter());
        sessionString = createStompSession(new StringMessageConverter());

    }

    @Test
    void connectionPossible() throws InterruptedException, ExecutionException, TimeoutException {
        var webSocketStompClient = new WebSocketStompClient(new SockJsClient(
                List.of(new WebSocketTransport(new StandardWebSocketClient()))));

        StompSession session = webSocketStompClient
                .connect(serverWsURL, new StompSessionHandlerAdapter() {
                })
                .get(1, TimeUnit.SECONDS);
        ;
//        assertDoesNotThrow(() -> session.send("/app/getHowManyQuestions/", ""), "");
    }
    @Test
    public void testHandleGetHowManyQuestions() throws InterruptedException, ExecutionException, TimeoutException {
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(
                List.of(new WebSocketTransport(new StandardWebSocketClient()))
        ));
        BlockingQueue<String> messageQueue = new ArrayBlockingQueue<>(1);

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession session = stompClient.connect(serverWsURL, new StompSessionHandlerAdapter() {}).get(1, TimeUnit.SECONDS);

        session.subscribe("/topic/howManyQuestions/", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                String message = (String) payload;
                System.out.println("----------"+message);
                messageQueue.add(message);
            }
        });

        session.send("/app/getHowManyQuestions/", "");
        Thread.sleep(2000);
        String receivedMessage = messageQueue.poll(5, TimeUnit.SECONDS);
        System.out.println("Received message: " + receivedMessage);

        assertNull(receivedMessage);
    }


}
