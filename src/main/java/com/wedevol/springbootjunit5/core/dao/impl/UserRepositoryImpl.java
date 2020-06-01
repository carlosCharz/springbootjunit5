package com.wedevol.springbootjunit5.core.dao.impl;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import com.wedevol.springbootjunit5.core.dao.UserRepository;
import com.wedevol.springbootjunit5.core.entity.UserEntity;

/**
 * Repository that manages the valid operations over the user.
 *
 * @author Charz++
 */

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final AtomicLong COUNTER = new AtomicLong(0);
    private static final Map<String, UserEntity> USERS = new ConcurrentHashMap<>();

    @Override
    public UserEntity findById(String id) {
        return USERS.get(id);
    }

    @Override
    public UserEntity create(UserEntity userInput) {
        Long nextKey = COUNTER.incrementAndGet();
        userInput.setId(nextKey);
        return USERS.put(String.valueOf(nextKey), userInput);
    }

    @Override
    public void update(String id, UserEntity userDb) {
        USERS.put(id, userDb);
    }

    @Override
    public void delete(String id) {
        USERS.remove(id);
    }

    @Override
    public Boolean exists(String id) {
        return USERS.containsKey(id);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return USERS.entrySet().stream().filter(e -> email.equals(e.getValue().getEmail())).map(Entry::getValue)
                .findFirst().orElse(null);
    }

    @Override
    public Integer countAll() {
        return USERS.size();
    }

    @Override
    public void resetDb() {
        COUNTER.set(0);
        USERS.clear();
    }

}
