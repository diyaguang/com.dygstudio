package com.dygstudio.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by diyaguang on 2017/5/31.
 * 用户表的 JavaBean
 */
@Entity
public class User {

    @Override
    public String toString(){
        return super.toString();
    }

    @Id
    @Column(name = "id")
    private String _id;
    private String username;
    private String userpwd;

    public User(){}
    public User(String username,String userpwd){
        this.username = username;
        this.userpwd = userpwd;
    }

    public String getUsername(){
        return username;
    }
    public String getUserpwd(){
        return userpwd;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setUserpwd(String userpwd){
        this.userpwd = userpwd;
    }
    public String getId(){
        return _id;
    }
    public void setId(String id){
        this._id = id;
    }
}
