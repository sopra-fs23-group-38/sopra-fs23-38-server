package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.Auxiliary.auxiliary;

import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.Instant;
import java.util.*;


@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public String registerNewUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> infobody = new HashMap<>();

        if (request.getParameter("username") == null
                || userRepository.findByUsername(request.getParameter("username")) != null) {
            infobody.put("success", "false");
            infobody.put("reason", "This username is already created.");
        } else {
            User newUser = new User();
            newUser.setUsername(request.getParameter("username"));
            newUser.setPassword(request.getParameter("password"));
            newUser.setEmail(request.getParameter("email"));
            newUser.setAvatarUrl(request.getParameter("avatar"));
            newUser.setRegister_time(Date.from(Instant.now()));
            newUser.setHasNew(0);
            userRepository.save(newUser);
            infobody.put("success", "true");

            response.addCookie(auxiliary.Identification(userRepository.findIdByUsername(request.getParameter("username"))));
        }
        return auxiliary.mapObjectToJson(infobody);
    }


    @Transactional
    public String loginOneUser(String username,
                               String password,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        Map<String, Object> infobody = new HashMap<>();

        infobody.put("success","false");


        List<User> user_list;
        boolean UserIndicatior = (user_list = userRepository.loginWithUsername(username, password)).size() == 1;

        if(UserIndicatior) {
            infobody.put("success", "true");
            infobody.put("user", user_list.get(0));
            Cookie cookie = auxiliary.Identification(user_list.get(0).getId());
            response.addCookie(cookie);
            infobody.put("token", cookie.getValue());
        }
        else{
            infobody.put("reason","The username and password does not match.");
        }
        return auxiliary.mapObjectToJson(infobody);
    }




}
