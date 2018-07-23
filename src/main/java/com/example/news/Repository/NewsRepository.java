package com.example.news.Repository;

import com.example.news.Entity.News;
import com.example.news.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @Author: Colin
 * @Date: 2018/5/30 23:08
 */
@Repository
public interface NewsRepository extends JpaRepository<News,Integer> {
}
