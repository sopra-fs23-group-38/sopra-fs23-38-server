package ch.uzh.ifi.hase.soprafs23.controller;


import ch.uzh.ifi.hase.soprafs23.Auxiliary.auxiliary;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

  private final UserService userService;

  UserController(UserService userService) {
    this.userService = userService;
  }

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String registerNewUser(HttpServletRequest request, HttpServletResponse response) {
        return userService.registerNewUser(request, response);
    }

    @PostMapping("/login")
    public String loginOneUser(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        return userService.loginOneUser(username, password, request, response);
    }

    @GetMapping("/has_new")
    public String getHasNew(HttpServletRequest request) {
        Integer userId = auxiliary.extractUserID(request);
        Optional<User> byId = userRepository.findById(userId);

        Map<String, Object> eacNotification = new HashMap<>();
        eacNotification.put("has_new", false);
        byId.ifPresent(user -> eacNotification.put("has_new", user.getHasNew()));
        return auxiliary.mapObjectToJson(eacNotification);
    }

    @PostMapping("/has_new")
    public String cleanHasNew(HttpServletRequest request) {
        Integer userId = auxiliary.extractUserID(request);
        Optional<User> byId = userRepository.findById(userId);
        if (byId.isPresent()) {
            User user = byId.get();
            user.setHasNew(false);
            userRepository.save(user);
        }

        Map<String, Object> eachNotification = new HashMap<>();
        eachNotification.put("success", true);

        return auxiliary.mapObjectToJson(eachNotification);
    }


}
