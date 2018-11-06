package com.example.user.projecthem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WritingActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FirebaseDatabase writingDatabase;
    private DatabaseReference writingRef;
    private EditText editPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        writingDatabase = FirebaseDatabase.getInstance();
        writingRef = writingDatabase.getReference();
        toolbar = findViewById(R.id.toolbar);
        editPost = findViewById(R.id.editPost);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.writemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                Intent intent = getIntent();
                final String userId = intent.getStringExtra("name");
                Intent idintent = new Intent(this,CommunityActivity.class);
                idintent.putExtra("name", userId);
                finish();
                return true;
            }
            case R.id.newPost:{
                if(!editPost.getText().toString().trim().equals("")){
                    Intent intent = getIntent();
                    final String userId = intent.getStringExtra("name");
                    final int boardlistsize = intent.getIntExtra("boardlistsize",0);
                    Map<String, Object> BoardChildUpdate = new HashMap<>();
                    Map<String, Object> BoardValue = null;

                    Board board = new Board();

                    board.mNickname = userId;
                    board.mContent = editPost.getText().toString();
                    board.mComment = 0;

                    long currentTime = System.currentTimeMillis();
                    Date date = new Date(currentTime);
                    SimpleDateFormat sdfCurrentTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    board.mPosttime = sdfCurrentTime.format(date);

                    BoardValue = board.toMap();
                    BoardChildUpdate.put("/posts/"+(boardlistsize+1),BoardValue);
                    writingRef.updateChildren(BoardChildUpdate);
                    Intent writeintent = new Intent(this, CommunityActivity.class);
                    writeintent.putExtra("name", userId);
                    writeintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(writeintent);
                    Toast.makeText(WritingActivity.this, "새 글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                    return true;
                }else{
                    Toast.makeText(WritingActivity.this,"빈칸없이 글을 작성해주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
