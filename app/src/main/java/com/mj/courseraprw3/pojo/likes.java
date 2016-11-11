package com.mj.courseraprw3.pojo;

/**
 * Created by leyenda on 10/30/16.
 */

public class likes {
    private String username;
    private String full_name;
    private String profile_picture;
    private String id;

    public likes(String username, String full_name, String profile_picture, String id) {
        this.username = username;
        this.full_name = full_name;
        this.profile_picture = profile_picture;
        this.id = id;
    }

    public likes() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
