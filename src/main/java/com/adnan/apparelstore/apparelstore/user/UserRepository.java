package com.adnan.apparelstore.apparelstore.user;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    
    public User findByEmail(String email);
    public void deleteByEmail(String email);

}
