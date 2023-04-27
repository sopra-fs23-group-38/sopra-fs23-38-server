package ch.uzh.ifi.hase.soprafs23.repository;

import ch.uzh.ifi.hase.soprafs23.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.password = :password")
    List<User> loginWithUsername(@Param("username") String username, @Param("password") String password);


    User findByUsername(String username);

    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    Integer findIdByUsername(String username);

    @Query(value = "SELECT id, username, (LENGTH(?1) / LENGTH(username)) AS score " +
            "FROM user " +
            "WHERE username LIKE CONCAT('%', ?1, '%') " +
            "ORDER BY score DESC", nativeQuery = true)
    List<Object[]> UserFindByKeyword(String keyword);

}
