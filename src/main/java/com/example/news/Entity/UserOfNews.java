package com.example.news.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author: Colin
 * @Date: 2018/5/30 0:24
 */
@Entity
public class UserOfNews implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer keyId;//id
    private Integer userKeyId;//用户id
    private Integer newsKeyId;//新闻id

    public Integer getKeyId() {
        return keyId;
    }

    public void setKeyId(Integer keyId) {
        this.keyId = keyId;
    }

    public Integer getUserKeyId() {
        return userKeyId;
    }

    public void setUserKeyId(Integer userKeyId) {
        this.userKeyId = userKeyId;
    }

    public Integer getNewsKeyId() {
        return newsKeyId;
    }

    public void setNewsKeyId(Integer newsKeyId) {
        this.newsKeyId = newsKeyId;
    }
}
