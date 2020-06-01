package com.wedevol.springbootjunit5.core.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wedevol.springbootjunit5.core.dao.UserRepository;
import com.wedevol.springbootjunit5.core.entity.UserEntity;
import com.wedevol.springbootjunit5.core.service.UserService;

/**
 * Service that manages the valid operations over the user.
 *
 * @author Charz++
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserEntity findByIdOrThrow(String id) {
        Optional<UserEntity> userObj = Optional.ofNullable(userRepo.findById(id));
        return userObj.orElseThrow(RuntimeException::new);
    }

    @Override
    public UserEntity create(UserEntity userInput) {
        // We first search by email, the user should not exist
        Optional<UserEntity> userObj = Optional.ofNullable(findByEmail(userInput.getEmail()));
        if (userObj.isPresent()) {
            throw new RuntimeException();
        }
        return userRepo.create(userInput);
    }

    @Override
    public UserEntity update(String id, UserEntity userInput) {
        logger.info("Id: {}", id);
        UserEntity userDb = findByIdOrThrow(id);
        if (userInput.getName() != null) {
            userDb.setName(userInput.getName());
        }
        if (userInput.getEmail() != null) {
            userDb.setEmail(userInput.getEmail());
        }
        if (userInput.getAge() != null) {
            userDb.setAge(userInput.getAge());
        }
        userRepo.update(id, userDb);
        return userDb;
    }

    @Override
    public void delete(String id) {
        logger.info("Id: {}", id);
        findByIdOrThrow(id);
        userRepo.delete(id);
    }

    @Override
    public Boolean exists(String id) {
        logger.info("Id: {}", id);
        return userRepo.exists(id);
    }

    @Override
    public UserEntity findByEmail(String email) {
        logger.info("Email: {}", email);
        return userRepo.findByEmail(email);
    }

    @Override
    public Integer countAll() {
        Integer counter = userRepo.countAll();
        logger.info("Counter: {}", counter);
        return counter;
    }

}
