package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to
 * the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */
@Service
@Transactional
public class UserService {

  private final Logger log = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;

  @Autowired
  public UserService(@Qualifier("userRepository") UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getUsers() {
    return this.userRepository.findAll();
  }

  public User createUser(User newUser) {
    newUser.setToken(UUID.randomUUID().toString());
    newUser.setStatus(UserStatus.ONLINE);
    newUser.setCreation_date(new Date());
    newUser.setBirthday(null);
    checkIfUserExists(newUser);
    // saves the given entity but data is only persisted in the database once
    // flush() is called
    newUser = userRepository.save(newUser);
    userRepository.flush();
//    log.debug("Created Information for User: {}", newUser);
    return newUser;
  }

  /**
   * This is a helper method that will check the uniqueness criteria of the
   * username and the name
   * defined in the User entity. The method will do nothing if the input is unique
   * and throw an error otherwise.
   *
   * @param userToBeCreated
   * @throws org.springframework.web.server.ResponseStatusException
   * @see User
   */
  private void checkIfUserExists(User userToBeCreated) {
    User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());
    String baseErrorMessage = "The %s provided %s not unique. Therefore, the user could not be created!";
    if (userByUsername != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(baseErrorMessage, "username", "is"));
    }
  }

    public User getUserByUserId(Long userId) {
        String baseErrorMessage = "user with %s was not found!";
        Optional<User> userOptional = this.userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format(baseErrorMessage, userId));
        }
    }

    public User updateUser2(long id, User user) {
        // check user

        User userByUserId = getUserByUserId(id);
        if (userByUserId == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("user Id :%s not found!",id));
        }
        if (user.getUsername() != null && !user.getUsername().equals("")) {

            userByUserId.setUsername(user.getUsername());
        }
        userByUserId.setBirthday(user.getBirthday());

        this.userRepository.save(userByUserId);
        userRepository.flush();
        return userByUserId;
    }

//    public void updateUser(User user) {
//      // check user
//       User userByUserId = getUserByUserId(user.getId());
//       userByUserId.setBirthday(user.getBirthday());
//       userByUserId.setUsername(user.getUsername());
//       this.userRepository.save(userByUserId);
//    }

    public User logout(long id)
    {
        User user = userRepository.findById(id);
        if (user == null) {
            String errorMessage = "The user does not exist.";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(errorMessage));
        }

        user.setStatus(UserStatus.OFFLINE);
        this.userRepository.save(user);
        userRepository.flush();

        return user;
    }
    public User login(User userInput) {
        String baseErrorMessage = "Username or Password error!";
        User byUsernameAndPassword = userRepository.findByUsernameAndPassword(userInput.getUsername(), userInput.getPassword());
        if (byUsernameAndPassword == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format(baseErrorMessage));
        }
        byUsernameAndPassword.setStatus(UserStatus.ONLINE);
        this.userRepository.save(byUsernameAndPassword);
        userRepository.flush();
        return byUsernameAndPassword;
    }
}
