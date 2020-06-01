package com.wedevol.springbootjunit5.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.wedevol.springbootjunit5.core.entity.UserEntity;
import com.wedevol.springbootjunit5.core.service.UserService;

/**
 * User REST Controller
 *
 * @author charz
 *
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserEntity findById(@PathVariable String userId) {
        logger.info("Find user by id: {}", userId);
        return userService.findByIdOrThrow(userId);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UserEntity create(@RequestBody UserEntity user) {
        logger.info("Create user");
        return userService.create(user);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserEntity update(@PathVariable String userId, @RequestBody UserEntity user) {
        logger.info("Update user: {}", userId);
        return userService.update(userId, user);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String userId) {
        logger.info("Delete user: {}", userId);
        userService.delete(userId);
    }

    @RequestMapping(value = "/{userId}/exists", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Boolean exists(@PathVariable String userId) {
        logger.info("Exists user {}?", userId);
        return userService.exists(userId);
    }

    @RequestMapping(value = "/find/email", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserEntity findByEmail(@RequestParam(value = "email") String email) {
        logger.info("Find user by email: {}", email);
        return userService.findByEmail(email);
    }

    @RequestMapping(value = "/count/all", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Integer countAll() {
        logger.info("Count all users");
        return userService.countAll();
    }

}
