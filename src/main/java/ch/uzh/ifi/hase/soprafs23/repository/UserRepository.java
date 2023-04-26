package ch.uzh.ifi.hase.soprafs23.repository;

import ch.uzh.ifi.hase.soprafs23.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.repository.query.Param;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
  User findByName(String name);

  User findByUsername(String username);

  User findByUsernameAndPassword(String username, String password);

  User findById(long id);
    
    
  @Query("SELECT u FROM User u WHERE u.id = :userId")
    User findUserByUserId(@Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query("UPDATE User SET password=:newPassword WHERE id>:id")
    void updatePassword(@Param("newPassword") String newPassword, @Param("id") Integer id);

    void deleteById(Integer id);
    
    @Query(value = "SELECT id, username, (LENGTH(:keyword) / LENGTH(username)) AS score " +
               "FROM User " +
               "WHERE username LIKE %:keyword% " +
               "ORDER BY score DESC")
    List<User> UserfindByKeyword(@Param("keyword") String keyword);

}
