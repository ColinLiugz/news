package com.example.news.Repository;

import com.example.news.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("select a from User a where a.phoneNumber=?1")
    User getByPhoneNumber(String phoneNumber);

    @Query("select a from User a where a.keyId=?1")
    User getOneByKeyId(Integer keyId);
}
