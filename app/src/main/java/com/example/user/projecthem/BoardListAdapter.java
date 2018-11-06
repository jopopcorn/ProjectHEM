package com.example.user.projecthem;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 조수연 on 2018-05-28.
 */

public class BoardListAdapter extends BaseAdapter{
    private Context context;
    private List<Board> boardList;

    public BoardListAdapter(Context context, List<Board> boardList){
        this.context = context;
        this.boardList = boardList;
    }

    @Override
    public int getCount() {
        return boardList.size();
    }

    @Override
    public Object getItem(int position) {
        return boardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.board,null);
        TextView textNickname = (TextView) v.findViewById(R.id.textNickname);
        TextView textContent = (TextView) v.findViewById(R.id.textContent);
        TextView textPosttime = (TextView) v.findViewById(R.id.textPosttime);
//        TextView textComment = (TextView) v.findViewById(R.id.textComment);

        textNickname.setText(boardList.get(position).getmNickname());
        textNickname.setTextColor(Color.GRAY);
        textContent.setText(boardList.get(position).getmContent());
        textContent.setTextColor(Color.GRAY);
        textPosttime.setText(boardList.get(position).getmPosttime());
        textPosttime.setTextColor(Color.GRAY);
//        textComment.setText(""+boardList.get(position).getmComment());
//        textComment.setTextColor(Color.GRAY);

        v.setTag(boardList.get(position).getmNickname());
        return v;
    }
}
