package com.wedevol.springbootjunit5.core.service;

import com.wedevol.springbootjunit5.core.entity.UserEntity;

/**
 * Interface for the User Service Implementation
 *
 * @author Charz++
 */

public interface UserService {

    UserEntity findByIdOrThrow(String id);

    UserEntity create(UserEntity userInput);

    UserEntity update(String id, UserEntity userInput);

    void delete(String id);

    Boolean exists(String id);

    UserEntity findByEmail(String email);

    Integer countAll();

}
