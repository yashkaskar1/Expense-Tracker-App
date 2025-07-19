package org.example.repository;

import org.example.Entities.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserInfo,Long> {
    UserInfo findByUsername(String username);
}
