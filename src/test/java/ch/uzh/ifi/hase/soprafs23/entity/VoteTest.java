package ch.uzh.ifi.hase.soprafs23.entity;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VoteTest {
    @Test
    public void testVote() {
        Vote vote = new Vote();

        // Set the properties
        Integer id = 1;
        Integer answer_id = 2;
        boolean is_upvote = true;

        vote.setId(id);
        vote.setAnswer_id(answer_id);
        vote.setIs_upvote(is_upvote);

        // Now use the getters to verify that the setters worked
        assertEquals(id, vote.getId());
        assertEquals(answer_id, vote.getAnswer_id());
        assertEquals(is_upvote, vote.isIs_upvote());
    }
}
