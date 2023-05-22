package ch.uzh.ifi.hase.soprafs23.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentTest {
    @Test
    public void testComment() {
        Comment comment = new Comment();

        // Set the properties
        Integer id = 1;
        String content = "Test comment";
        Integer who_comments = 2;
        Integer answer_ID = 3;
        Date change_time = new Date();
        Integer parentCommentId = 4;

        comment.setId(id);
        comment.setContent(content);
        comment.setWho_comments(who_comments);
        comment.setAnswer_ID(answer_ID);
        comment.setChange_time(change_time);
        comment.setParentCommentId(parentCommentId);

        // Now use the getters to verify that the setters worked
        assertEquals(id, comment.getId());
        assertEquals(content, comment.getContent());
        assertEquals(who_comments, comment.getWho_comments());
        assertEquals(answer_ID, comment.getAnswer_ID());
        assertEquals(change_time, comment.getChange_time());
        assertEquals(parentCommentId, comment.getParentCommentId());
    }
}
