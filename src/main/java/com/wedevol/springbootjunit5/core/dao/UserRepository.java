package com.wedevol.springbootjunit5.core.dao;

import com.wedevol.springbootjunit5.core.entity.UserEntity;

/**
 * Interface for the User Repository Implementation
 *
 * @author Charz++
 */

public interface UserRepository {

    UserEntity findById(String id);

    UserEntity create(UserEntity userInput);

    void update(String id, UserEntity userDb);

    void delete(String id);

    Boolean exists(String id);

    UserEntity findByEmail(String email);

    Integer countAll();

    void resetDb();

}
