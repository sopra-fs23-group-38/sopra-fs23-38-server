package ch.uzh.ifi.hase.soprafs23.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VoteTest {

    private Vote voteUnderTest;

    @BeforeEach
    void setUp() {
        voteUnderTest = new Vote();
    }
    @Test
    public void testSetAndGetId() {
        Vote vote = new Vote();
        vote.setId(1);
        assertEquals(1, vote.getId());
    }

    @Test
    public void testSetAndGetAnswerId() {
        Vote vote = new Vote();
        vote.setAnswer_id(2);
        assertEquals(2, vote.getAnswer_id());
    }

    @Test
    public void testSetAndGetIsUpvote() {
        Vote vote = new Vote();
        vote.setIs_upvote(true);
        assertEquals(true, vote.isIs_upvote());
    }
}
