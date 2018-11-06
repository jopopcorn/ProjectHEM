package com.example.user.projecthem;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 조수연 on 2018-06-16.
 */

public class Comment {
    String mCommentId;
    String mCommentText;
    String mCommentPosttime;

    public String getmCommentId() {
        return mCommentId;
    }

    public String getmCommentText() {
        return mCommentText;
    }

    public String getmCommentPosttime() {
        return mCommentPosttime;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("mCommentId",mCommentId);
        result.put("mCommentPosttime",mCommentPosttime);
        result.put("mCommentText",mCommentText);
        return result;
    }
}
