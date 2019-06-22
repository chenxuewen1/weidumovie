package com.bw.movie.bean;

import java.io.Serializable;

/*Time:2019/4/12
 *Author:chenxuewen
 *Description:存储用户信息
 */
public class UserBean implements Serializable {
    private String sessionId;
    private int userId;
    private int birthday;
    private int id;
    private int lastLoginTime;
    private String nickName;
    private String phone;
    private int sex;
    private String headPic;

    public UserBean(String sessionId, int userId, int birthday, int id, int lastLoginTime, String nickName, String phone, int sex, String headPic) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.birthday = birthday;
        this.id = id;
        this.lastLoginTime = lastLoginTime;
        this.nickName = nickName;
        this.phone = phone;
        this.sex = sex;
        this.headPic = headPic;
    }

    public UserBean() {
        super();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(int lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }
}
