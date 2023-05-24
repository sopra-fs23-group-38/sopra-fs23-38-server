package ch.uzh.ifi.hase.soprafs23.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionTest {

    private Question questionUnderTest;

    @BeforeEach
    void setUp() {
        questionUnderTest = new Question();
    }

    @Test
    void testGetAnswerCount() {
        Assertions.assertNull(questionUnderTest.getAnswerCount());
    }

    @Test
    void testSetAnswerCount() {
        // Setup
        // Run the test
        questionUnderTest.setAnswerCount(0);
        questionUnderTest.getTag();
        // Verify the results
    }
}
