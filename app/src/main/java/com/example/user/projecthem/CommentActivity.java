package com.example.user.projecthem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FirebaseDatabase commentDatabase;
    private DatabaseReference commentRef;
    private ListView listView;
    private List<Comment> commentList;
    private TextView mTextNickname, mTextContent, mTextPosttime, mTextNumber; //board 정보
    private Button mBtnWriteComment; //댓글 작성 버튼
    private EditText mEditComment; //댓글 작성하는 edittext
    private CommentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.listView);
        mTextNickname = findViewById(R.id.textNickname);
        mTextContent = findViewById(R.id.textContent);
        mTextPosttime = findViewById(R.id.textPosttime);
        mTextNumber = findViewById(R.id.textNumber);
        mEditComment = findViewById(R.id.EditComment);
        mBtnWriteComment = findViewById(R.id.BtnWriteComment);

        Intent intent = getIntent();
        final String userId = intent.getStringExtra("name");
        final String boardId = intent.getStringExtra("boardId");
        final String boardContent = intent.getStringExtra("boardContent");
        final String boardPosttime = intent.getStringExtra("boardPosttime");
        final int boardListNumber = intent.getIntExtra("boardlistnumber",0);

        commentDatabase = FirebaseDatabase.getInstance();
        commentRef = commentDatabase.getReference("posts/"+boardListNumber+"/comments");

        mTextNickname.setText(boardId);
        mTextContent.setText(boardContent);
        mTextPosttime.setText(boardPosttime);

        commentList = new ArrayList<Comment>();
        adapter = new CommentListAdapter(getApplicationContext(),commentList);
        listView.setAdapter(adapter);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Comment comment = data.getValue(Comment.class);
                    commentList.add(comment);
                }
                adapter.notifyDataSetChanged();
                listView.setSelection(adapter.getCount() - 1);
                mTextNumber.setText("댓글 "+(commentList.size()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mBtnWriteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEditComment.getText().toString().trim().equals("")){
                    Map<String, Object> CommentChildUpdate = new HashMap<>();
                    Map<String, Object> CommentValue = null;
                    Comment comment = new Comment();

                    comment.mCommentId = userId;
                    comment.mCommentText = mEditComment.getText().toString();

                    long currentTime = System.currentTimeMillis();
                    Date date = new Date(currentTime);
                    SimpleDateFormat sdfCurrentTime = new SimpleDateFormat(" yyyy/MM/dd HH:mm:ss");
                    comment.mCommentPosttime = sdfCurrentTime.format(date);

                    CommentValue = comment.toMap();
                    CommentChildUpdate.put((commentList.size()+1)+"_"+userId,CommentValue);
                    commentRef.updateChildren(CommentChildUpdate);
                    mEditComment.setText("");
                    Toast.makeText(CommentActivity.this,"댓글이 등록되었습니다.",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(CommentActivity.this,"빈칸없이 댓글을 작성해주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Intent intent = getIntent();
                final String userId = intent.getStringExtra("name");
                Intent idintent = new Intent(this,CommunityActivity.class);
                idintent.putExtra("name", userId);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
