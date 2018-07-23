package com.example.news.Repository;

import com.example.news.Entity.User;
import com.example.news.Entity.UserOfNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2018/5/30 23:00
 */
@Repository
public interface UserOfNewsRepository extends JpaRepository<UserOfNews,Integer> {

    @Query("select a from UserOfNews  a where  a.userKeyId=?1 and  a.newsKeyId=?2 ")
    UserOfNews findByUserKeyIdAndNewsKeyId(Integer userKeyId,Integer newsKeyId);

    @Query("select a from UserOfNews  a where  a.userKeyId=?1 order by a.keyId desc ")
    List<UserOfNews> findByUserKeyId(Integer userKeyId);
}
