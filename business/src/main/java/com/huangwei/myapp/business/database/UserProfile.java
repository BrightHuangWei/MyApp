package com.huangwei.myapp.business.database;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * description ：
 * author : HuangWei
 * creation date: 2018/3/30
 */

@Entity(nameInDb = "user_profile")
public class UserProfile {

    @JSONField(name = "userId")
    @Id
    private long userId = 0;

    @JSONField(name = "name")
    private String name = null;

    @JSONField(name = "avatar")
    private String avatar = null;

    @JSONField(name = "gender")
    private String gender = null;

    @JSONField(name = "address")
    private String address = null;


    @Generated(hash = 1202698052)
    public UserProfile(long userId, String name, String avatar, String gender,
            String address) {
        this.userId = userId;
        this.name = name;
        this.avatar = avatar;
        this.gender = gender;
        this.address = address;
    }
    @Generated(hash = 968487393)
    public UserProfile() {
    }
    public long getUserId() {
        return this.userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
