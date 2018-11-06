package com.example.user.projecthem;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 조수연 on 2018-06-01.
 */

public class User {
    String mUserId;
    String mUserPassword;
    String mUserEmail;
    String mUserHobby;
    String mUserCareer;

    public String getmUserId() {
        return mUserId;
    }

    public String getmUserPassword() {
        return mUserPassword;
    }

    public String getmUserEmail() {
        return mUserEmail;
    }

    public String getmUserHobby() {
        return mUserHobby;
    }

    public String getmUserCareer() {
        return mUserCareer;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("mUserId",mUserId);
        result.put("mUserPassword",mUserPassword);
        result.put("mUserEmail",mUserEmail);
        result.put("mUserHobby",mUserHobby);
        result.put("mUserCareer",mUserCareer);
        return result;
    }
}
