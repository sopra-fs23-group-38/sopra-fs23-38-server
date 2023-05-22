package ch.uzh.ifi.hase.soprafs23.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

class GlobalExceptionAdviceTest {

    private GlobalExceptionAdvice globalExceptionAdviceUnderTest;

    @BeforeEach
    void setUp() {
        globalExceptionAdviceUnderTest = new GlobalExceptionAdvice();
    }

    @Test
    void testHandleConflict() {
        // Setup
        final WebRequest request = null;

        // Run the test
        final ResponseEntity<Object> result = globalExceptionAdviceUnderTest.handleConflict(
                new RuntimeException("message"), request);

        // Verify the results
    }

    @Test
    void testHandleTransactionSystemException() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();

        // Run the test
        final ResponseStatusException result = globalExceptionAdviceUnderTest.handleTransactionSystemException(
                new Exception("message"), request);

        // Verify the results
    }

    @Test
    void testHandleException() {
        // Setup
        // Run the test
        final ResponseStatusException result = globalExceptionAdviceUnderTest.handleException(new Exception("message"));

        // Verify the results
    }
}
