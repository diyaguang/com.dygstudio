package com.dygstudio.web.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by diyaguang on 2017/5/31.
 * 用户表的 JavaBean
 */
@Entity
@Table( name = "user")
public class User {

    @Override
    public String toString(){
        return super.toString();
    }

    @Id
    @Column(name = "id")
    private String _id;
    @Column(name = "username")
    private String username;
    @Column(name = "userpwd")
    private String userpwd;

    public User(){}
    public User(String username,String userpwd){
        this.username = username;
        this.userpwd = userpwd;
    }
    public User(String id,String username,String userpwd){
        this._id = id;
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

    @Override
    public boolean equals(Object that){
        return EqualsBuilder.reflectionEquals(this,that,"id");
    }
    @Override
    public int hashCode(){
        return HashCodeBuilder.reflectionHashCode(this,"id");
    }
}
