package com.example.news.Controller;

import com.example.news.Entity.News;
import com.example.news.Entity.UserOfNews;
import com.example.news.Repository.NewsRepository;
import com.example.news.Repository.UserOfNewsRepository;
import com.example.news.Repository.UserRepository;
import com.example.news.Util.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Colin
 * @Date: 2018/5/30 22:58
 */
@RestController
@CrossOrigin
public class UserOfNewsController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserOfNewsRepository userOfNewsRepository;
    @Autowired
    private NewsRepository newsRepository;


    @RequestMapping(value = "/userOfNews/add")
    public ApiResult addUserOfNews(Integer userKeyId,String date,String title,String category,String author_name,String url,
            String thumbnail_pic_s,String thumbnail_pic_s02,String thumbnail_pic_s03){
        News news = new News();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            news.setDate(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        news.setTitle(title);
        news.setAuthor_name(author_name);
        news.setCategory(category);
        news.setUrl(url);
        news.setThumbnail_pic_s(thumbnail_pic_s);
        news.setThumbnail_pic_s02(thumbnail_pic_s02);
        news.setThumbnail_pic_s03(thumbnail_pic_s03);
        news = newsRepository.save(news);
        UserOfNews userOfNews = new UserOfNews();
        userOfNews.setNewsKeyId(news.getKeyId());
        userOfNews.setUserKeyId(userKeyId);
        userOfNewsRepository.save(userOfNews);
        return ApiResult.ok();
    }

    @RequestMapping(value = "/userOfNews/delete")
    public ApiResult delete(Integer userKeyId,Integer newsKeyId){
        UserOfNews userOfNews = userOfNewsRepository.findByUserKeyIdAndNewsKeyId(userKeyId,newsKeyId);
        if(null != userOfNews){
            userOfNewsRepository.delete(userOfNews);
        }
        return ApiResult.ok();
    }

    @RequestMapping(value = "/userOfNews/list")
    public ApiResult list(Integer userKeyId){
        List<UserOfNews> userOfNewsList = userOfNewsRepository.findByUserKeyId(userKeyId);
        List<News> newsList = new ArrayList<>();
        for(UserOfNews userOfNews : userOfNewsList){
            News news = newsRepository.getOne(userOfNews.getNewsKeyId());
            newsList.add(news);
        }
        return ApiResult.ok(newsList);
    }
}
