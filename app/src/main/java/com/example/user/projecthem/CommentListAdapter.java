package com.example.user.projecthem;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 조수연 on 2018-06-16.
 */

public class CommentListAdapter extends BaseAdapter{
    private Context context;
    private List<Comment> commentList;

    public CommentListAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.comment,null);
        TextView textCommentId = (TextView) v.findViewById(R.id.textCommentId);
        TextView textCommentContent = (TextView) v.findViewById(R.id.textCommentContent);
        TextView textCommentPosttime = (TextView) v.findViewById(R.id.textCommentPosttime);

        textCommentId.setText(commentList.get(position).getmCommentId());
        textCommentId.setTextColor(Color.GRAY);
        textCommentContent.setText(commentList.get(position).getmCommentText());
        textCommentContent.setTextColor(Color.GRAY);
        textCommentPosttime.setText(commentList.get(position).getmCommentPosttime());
        textCommentPosttime.setTextColor(Color.GRAY);

        v.setTag(commentList.get(position).getmCommentId());
        return v;
    }
}
