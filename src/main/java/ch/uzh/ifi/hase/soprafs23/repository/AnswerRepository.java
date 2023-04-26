package com.example.accessingdatamysql;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.accessingdatamysql.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    @Query("SELECT COUNT(*) FROM Answer WHERE user.id =:userId AND question.id =:questionId")
    Integer findByUserAndQuestion(@Param("userId")Integer userId, @Param("questionId")Integer questionId);

//    @Query(value = "SELECT * FROM answer WHERE question_id = :questionId", nativeQuery = true)
//    List<Answer> findByQuestionId(@Param("questionId") Integer questionId);

    List<Answer> findByQuestionId(Integer questionId);

    List<Answer> findByUserId(Integer userId);

    Answer findAnswerById(Integer answerId);

    void deleteById(Integer id);

    @Query("SELECT a.question FROM Answer a WHERE a.id = :answerId")
    Question findQuestionByAnswerId(@Param("answerId") Integer answerId);
    
    @Query(value = "SELECT id, content, (LENGTH(:keyword) - LENGTH(REPLACE(content, :keyword, ''))) / LENGTH(:keyword) AS score " +
            "FROM Answer " +
            "WHERE content LIKE %:keyword% " +
            "ORDER BY score DESC")
    List<Object[]> findByKeyword(@Param("keyword") String keyword);
    
    
}
