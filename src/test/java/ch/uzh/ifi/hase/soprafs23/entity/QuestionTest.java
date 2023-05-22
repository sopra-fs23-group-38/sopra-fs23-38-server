package ch.uzh.ifi.hase.soprafs23.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionTest {

    private Question questionUnderTest;

    @BeforeEach
    void setUp() {
        questionUnderTest = new Question();
    }

//    @Test
//    void testGetAnswerCount() {
//        assertThat(questionUnderTest.getAnswerCount()).isEqualTo(0);
//    }

    @Test
    void testSetAnswerCount() {
        // Setup
        // Run the test
        questionUnderTest.setAnswerCount(0);

        // Verify the results
    }
}
