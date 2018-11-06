package com.example.user.projecthem;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 조수연 on 2018-05-25.
 */

public class Board {
    /*게시판 글 UI*/
    String mNickname;
    String mPosttime;
    String mContent;
    int mComment;

    public String getmNickname() {
        return mNickname;
    }

    public String getmPosttime() {
        return mPosttime;
    }

    public String getmContent() {
        return mContent;
    }

    public int getmComment() {
        return mComment;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("mNickname",mNickname);
        result.put("mPosttime",mPosttime);
        result.put("mContent",mContent);
        result.put("mComment",mComment);
        return result;
    }
}
