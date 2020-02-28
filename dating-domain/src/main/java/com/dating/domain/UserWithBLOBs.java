package com.dating.domain;

public class UserWithBLOBs extends User {
    private String allPhotoUrl;

    private String aboutMe;

    public String getAllPhotoUrl() {
        return allPhotoUrl;
    }

    public void setAllPhotoUrl(String allPhotoUrl) {
        this.allPhotoUrl = allPhotoUrl == null ? null : allPhotoUrl.trim();
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe == null ? null : aboutMe.trim();
    }
}