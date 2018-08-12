package com.sda.controllers;

import com.sda.model.StatusUser;
import com.sda.model.User;
import com.sda.repository.MessageRepository;
import com.sda.repository.UserDetailsRepository;
import com.sda.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    //http://localhost:12346/mongo/chat/user/add
    @GetMapping("/chat/user/add")
    public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String surename){
        User user = new User();
        user.setName(name);
        user.setSurename(surename);
        userRepository.save(user);
        return "saved";
    }

    //7.1
    //localhost:12346/mongo/chat/user/add
     /*{
        "name" : "testByJOSON12",
            "surename" : "testJJJJ12",
            "password" : "haslo"
    }*/
    @RequestMapping(value = "/chat/user/add", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody String addNewUserJOSON(@RequestBody User user){
        String shaPass = DigestUtils.sha1Hex(user.getPassword());
        user.setPassword(shaPass);
        User returnedUser = userRepository.save(user);
        return returnedUser.getId().toString();
    }

    //7.2
    @RequestMapping(value = "chat/user/find", method = RequestMethod.GET)
    public @ResponseBody List<User> getUsers(@RequestParam(value = "name", required = false) final String name,
                                             @RequestParam(value = "surname", required = false) final String surname,
                                             @RequestParam(value = "nick", required = false) final String nick,
                                             @RequestParam(value = "email", required = false) final String email){
        return userDetailsRepository.findBy(name,surname,nick,email);
    }

    //7.2*
    // localhost:12346/mongo/chat/user/findByAnyField?text=
    @RequestMapping(value = "chat/user/findByAnyField", method = RequestMethod.GET)
    public @ResponseBody List<User> getUsersByAnyField(@RequestParam String text){
        return userRepository.findUserByNameOrSurenameOrNickOrEmail(text, text, text, text);
    }

    //7.3
    //localhost:12346/mongo/chat/user/findActive
    @RequestMapping(value = "chat/user/findActive", method = RequestMethod.GET)
    public @ResponseBody List<User> getActiveUser(){
        return userRepository.findByStatusUser(StatusUser.ACTIVE);
    }

    //7.4
    //localhost:12346/mongo/chat/user/findActive  GET
    //localhost:12346/mongo/chat/user/set?statusUser=INACTIVE&id=5b6fe2eca7b5b10a6cc15884
    @RequestMapping(value = "chat/user/set", method = RequestMethod.GET)
    public @ResponseBody String setStatus(@RequestParam StatusUser statusUser, @RequestParam ObjectId id){
        User user = userRepository.findById(id).get();
        StatusUser oldStatus = user.getStatusUser();
        user.setStatusUser(statusUser);
        User returnedUser = userRepository.save(user);
        return returnedUser.getStatusUser().toString();
    }

    //7.5
    //localhost:12346/mongo/chat/user/setText?textStatus=status&id=5b6fe2eca7b5b10a6cc15884
    @RequestMapping(value = "chat/user/setText", method = RequestMethod.GET)
    public @ResponseBody Boolean setStatus(@RequestParam String textStatus, @RequestParam ObjectId id){
        User user = userRepository.findById(id).get();
        String oldText = user.getTextStatus();
        user.setTextStatus(textStatus);
        User returnedUser = userRepository.save(user);
        if (returnedUser.getStatusUser().toString() == oldText){
            return false;
        }else {
            return true;
        }
    }

    //7.6
    //localhost:12346/mongo/chat/user/del?id=5b6feb5fa7b5b11b306ed990
    @RequestMapping(value = "chat/user/del",method = RequestMethod.GET)
    public @ResponseBody Boolean deleteUser(@RequestParam ObjectId id){
        userRepository.deleteById(id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            return false;
        }else {
            return true;
        }
    }

    //7.7
    //localhost:12346/mongo/chat/user/count
    @RequestMapping(value = "chat/user/count",method = RequestMethod.GET)
    public @ResponseBody Integer countUser(){
        return  (int) userRepository.count();
    }

    //7.8
}
