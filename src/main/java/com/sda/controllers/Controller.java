package com.sda.controllers;

import com.sda.model.User;
import com.sda.repository.MessageRepository;
import com.sda.repository.UserDetailsRepository;
import com.sda.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping(path = "/mongo")
public class Controller {

//    @GetMapping(path = "")
//    public @ResponseBody
//    String sayHello() {
//        return "Hello";
//    }

    private UserRepository userRepository;
    private MessageRepository messageRepository;
    private UserDetailsRepository userDetailsRepository;


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Autowired
    public void setUserDetailsRepository(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @GetMapping("/chat/user/add")
    public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String surename){
        User user = new User();
        user.setName(name);
        user.setSurename(surename);
        userRepository.save(user);
        return "saved";
    }


    //localhost:12346/mongo/chat/user/add
    @RequestMapping(value = "/chat/user/add", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody String addNewUserJOSON(@RequestBody User user){
        String shaPass = DigestUtils.sha1Hex(user.getPassword());
        user.setPassword(shaPass);
        User returnedUser = userRepository.save(user);
        return returnedUser.getId().toString();
    }

    @RequestMapping(value = "chat/user/find", method = RequestMethod.GET)
    public @ResponseBody List<User> getUsers(@RequestParam(value = "name", required = false) final String name,
                                             @RequestParam(value = "surname", required = false) final String surname,
                                             @RequestParam(value = "nick", required = false) final String nick,
                                             @RequestParam(value = "email", required = false) final String email){
        return userDetailsRepository.findBy(name,surname,nick,email);
    }

}
