package ch.uzh.ifi.hase.soprafs23;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.util.ArrayList;

class CorsConfigureTest {

    private CorsConfigure corsConfigureUnderTest;

    @BeforeEach
    void setUp() {
        corsConfigureUnderTest = new CorsConfigure();
    }

//    @Test
//    void testRegisterStompEndpoints() {
//        // Setup
//        final StompEndpointRegistry registry = null;
//
//        // Run the test
//        corsConfigureUnderTest.registerStompEndpoints(registry);
//
//        // Verify the results
//    }
//
//    @Test
//    void testConfigureMessageBroker() {
//        // Setup
//        final MessageBrokerRegistry registry = new MessageBrokerRegistry(Mockito.any(), Mockito.any());
//
//        // Run the test
//        corsConfigureUnderTest.configureMessageBroker(registry);
//
//        // Verify the results
//    }

    @Test
    void testCorsFilter() {
        // Setup
        // Run the test
        final CorsFilter result = corsConfigureUnderTest.corsFilter();

        // Verify the results
    }
}
