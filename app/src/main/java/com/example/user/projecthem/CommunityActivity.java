package com.example.user.projecthem;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommunityActivity extends AppCompatActivity {
    private ListView listView;
    private BoardListAdapter adapter;
    private List<Board> boardList;
    private FirebaseDatabase communityDatabase;
    private DatabaseReference communityRef;
    private FloatingActionButton mNewTextFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        communityDatabase = FirebaseDatabase.getInstance();
        communityRef = communityDatabase.getReference("posts");
        Intent intent = getIntent();
        final String userId = intent.getStringExtra("name");

        mNewTextFAB = findViewById(R.id.newTextFAB);
        listView = findViewById(R.id.listView);

        boardList = new ArrayList<Board>();
        adapter = new BoardListAdapter(getApplicationContext(),boardList);
        listView.setAdapter(adapter);

        communityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boardList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Board board = data.getValue(Board.class);
                    boardList.add(board);
                }
                adapter.notifyDataSetChanged();
                listView.setSelection(adapter.getCount() - 1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String boardId = ((Board) adapter.getItem(position)).getmNickname();
                String boardContent = ((Board)adapter.getItem(position)).getmContent();
                String boardPosttime = ((Board) adapter.getItem(position)).getmPosttime();
                int boardListNumber = position+1;
                Log.i("BOARD NUMBER",""+boardListNumber);
                Intent commentintent = new Intent(CommunityActivity.this,CommentActivity.class);
                commentintent.putExtra("name", userId);
                commentintent.putExtra("boardId",boardId);
                commentintent.putExtra("boardContent",boardContent);
                commentintent.putExtra("boardPosttime",boardPosttime);
                commentintent.putExtra("boardlistnumber",boardListNumber);
                startActivity(commentintent);
            }
        });

        mNewTextFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writingintent = new Intent(CommunityActivity.this,WritingActivity.class);
                writingintent.putExtra("name", userId);
                writingintent.putExtra("boardlistsize",boardList.size());
                startActivity(writingintent);
            }
        });
    }

}
