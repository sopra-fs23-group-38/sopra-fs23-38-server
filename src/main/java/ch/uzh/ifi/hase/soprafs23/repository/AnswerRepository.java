package ch.uzh.ifi.hase.soprafs23.repository;

import ch.uzh.ifi.hase.soprafs23.entity.Answer;
import ch.uzh.ifi.hase.soprafs23.entity.Question;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    @Query(value = "SELECT * FROM answer WHERE question_id = :questionId ORDER BY vote_count DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Answer> findByQuestionIdOrderByVoteCountDesc(@Param("questionId") Integer questionId, @Param("offset") int offset, @Param("limit") int limit);

    @Query("SELECT a.who_answers FROM Answer a WHERE a.id = :answerId")
    Integer findAnswererIdById(@Param("answerId") Integer answerId);

    @Query("SELECT u FROM User u WHERE u.id = :answererId")
    User findUserById(@Param("answererId") Integer answererId);

    @Query("SELECT a FROM Answer a WHERE a.who_answers = :who_answers ORDER BY a.change_time DESC")
    List<Answer> findAllByWhoAnswersOrderByChangeTimeDesc(@Param("who_answers") Integer whoAnswers);


    @Query("SELECT q.title FROM Question q WHERE q.id = " +
            "(SELECT a.question_id FROM Answer a WHERE a.id = :answerId)")
    String findAnswerToQuestionTitle(@Param("answerId") Integer answerId);

    @Modifying
    @Query("DELETE FROM Answer a WHERE a.id = :answerId")
    void deleteAnswerById(@Param("answerId") Integer answerId);

    @Query(value = "SELECT id, content, (LENGTH(?1) / LENGTH(content)) AS score " +
            "FROM answer " +
            "WHERE content LIKE CONCAT('%', ?1, '%') " +
            "ORDER BY score DESC", nativeQuery = true)
    List<Object[]> AnswerFindByKeyword(String keyword);

    @Query(value = "SELECT COUNT(*) FROM Answer WHERE question_id = ?1", nativeQuery = false)
    int countAnswersByQuestionId(Integer questionId);


}
