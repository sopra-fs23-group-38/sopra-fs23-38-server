package ch.uzh.ifi.hase.soprafs23.repository;

import ch.uzh.ifi.hase.soprafs23.entity.Answer;
import ch.uzh.ifi.hase.soprafs23.entity.Question;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query(value = "SELECT * FROM question ORDER BY change_time DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Question> findTopNByOrderByTimeDesc(@Param("offset") int offset, @Param("limit") int limit);



    @Query("SELECT q.who_asks FROM Question q WHERE q.id = :questionId")
    Integer findQuestionerIdById(@Param("questionId") Integer questionId);

    @Query("SELECT u FROM User u WHERE u.id = :questionerId")
    User findUserById(@Param("questionerId") Integer questionerId);

    @Query("SELECT q FROM Question q WHERE q.who_asks = :who_asks ORDER BY q.change_time DESC")
    List<Question> findAllByWhoAsksOrderByChangeTimeDesc(@Param("who_asks") Integer who_asks);

    @Query(value = "SELECT id, title, description, " +
            "((LENGTH(title) - LENGTH(REPLACE(title, :keyword, ''))) / LENGTH(:keyword) * 2) + " +
            "((LENGTH(description) - LENGTH(REPLACE(description, :keyword, ''))) / LENGTH(:keyword)) AS score " +
            "FROM question " +
            "WHERE title LIKE %:keyword% OR description LIKE %:keyword% " +
            "ORDER BY score DESC",
            nativeQuery = true)
    List<Object[]> QuestionFindByKeyword(@Param("keyword") String keyword);
    
    @Query(value = "SELECT * FROM question WHERE tag = :tag ORDER BY change_time DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Question> findTopNByTagOrderByTimeDesc(@Param("tag") String tag, @Param("offset") int offset, @Param("limit") int limit);

}
