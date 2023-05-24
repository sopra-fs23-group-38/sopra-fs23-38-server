package ch.uzh.ifi.hase.soprafs23.repository;

import ch.uzh.ifi.hase.soprafs23.entity.Answer;
import ch.uzh.ifi.hase.soprafs23.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
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

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM comment WHERE answer_ID = ?1",nativeQuery = true)
    void deleteCommentByAnswer_ID(Integer Id);

    @Query(value = "SELECT id FROM comment WHERE answer_ID = ?1",nativeQuery = true)
    List<Integer> getAllIdByAnswer_id(Integer id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM comment WHERE parent_comment_id= ?1",nativeQuery = true)
    void deleteCommentByParent_ID(Integer Id);

    @Query(value = "SELECT id, content, answer_id,(LENGTH(?1) / LENGTH(content)) AS score " +
            "FROM comment " +
            "WHERE content LIKE CONCAT('%', ?1, '%') " +
            "ORDER BY score DESC", nativeQuery = true)
    List<Object[]> CommentFindByKeyword(String keyword);

    @Query(value = "SELECT COUNT(*) FROM comment WHERE parent_comment_id= ?1", nativeQuery = true)
    int countByParentId(Integer parentId);

}
