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
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByUserId(Integer userId);

    @Modifying
    @Transactional
    @Query("UPDATE Question SET title=:newTitle, description=:newDescription, updated_at=CURRENT_TIMESTAMP WHERE id=:id")
    void updateQuestion(@Param("newTitle") String newTitle, @Param("newDescription") String newDescription, @Param("id") Integer id);

    Question findQuestionById(Integer ID);

    void deleteById(Integer id);
    
    @Query(value = "SELECT id, title, description, " +
                    "((LENGTH(title) - LENGTH(REPLACE(title, :keyword, ''))) / LENGTH(:keyword) * 2) + " +
                    "((LENGTH(description) - LENGTH(REPLACE(description, :keyword, ''))) / LENGTH(:keyword)) AS score " +
                    "FROM Question " +
                    "WHERE title LIKE %:keyword% OR description LIKE %:keyword% " +
                    "ORDER BY score DESC")
    List<Question> QuestionfindByKeyword(@Param("keyword") String keyword);

}

