package ch.uzh.ifi.hase.soprafs23.repository;

import ch.uzh.ifi.hase.soprafs23.entity.Answer;
import ch.uzh.ifi.hase.soprafs23.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CommentRepository  extends JpaRepository<Comment,Integer> {

    @Query(value = "SELECT * FROM comment WHERE answer_ID = :answerId ORDER BY change_time DESC", nativeQuery = true)
    List<Comment> findByAnswerIdOrderByTimeDesc(@Param("answerId") Integer answerId);

    @Query(value = "SELECT * FROM comment WHERE who_comments = :commentID ORDER BY change_time DESC", nativeQuery = true)
    List<Comment> findByWhoCommentsOrderByTimeDesc(@Param("commentID") Integer commentID);

    @Query(value = "SELECT * FROM comment WHERE answer_ID = :answerId ORDER BY change_time ASC", nativeQuery = true)
    List<Comment> findByAnswerIdOrderByTimeAsc(@Param("answerId") Integer answerId);

    @Query(value = "SELECT * FROM comment WHERE parent_comment_id = :parentId ORDER BY change_time ASC", nativeQuery = true)
    List<Comment> findByParentID(@Param("parentId") Integer parentId);


}
