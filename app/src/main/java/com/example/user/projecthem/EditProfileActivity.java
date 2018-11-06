package com.example.user.projecthem;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FirebaseDatabase editDatabase;
    private DatabaseReference editRef;
    private TextView mTextNowPasswordCorrect, mTextPasswordCorrect;
    private EditText mNowPassword, mEditPassword, mEditPassword2, mEditEmail, mEditHobby, mEditCareer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mTextNowPasswordCorrect = findViewById(R.id.TextNowPasswordCorrect);
        mTextPasswordCorrect = findViewById(R.id.TextPasswordCorrect);
        mNowPassword = findViewById(R.id.NowPassword);
        mEditPassword = findViewById(R.id.EditPassword);
        mEditPassword2 = findViewById(R.id.EditPassword2);
        mEditEmail = findViewById(R.id.EditEmail);
        mEditHobby = findViewById(R.id.EditHobby);
        mEditCareer = findViewById(R.id.EditCareer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("프로필 수정");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String userId = intent.getStringExtra("name");

        editDatabase = FirebaseDatabase.getInstance();
        editRef = editDatabase.getReference("users");

        mEditPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = mEditPassword.getText().toString();
                String password2 = mEditPassword2.getText().toString();

                if(password.equals(password2)) {
                    mEditPassword.setBackgroundColor(Color.GREEN);
                    mEditPassword2.setBackgroundColor(Color.GREEN);
                    mTextPasswordCorrect.setText("비밀번호가 일치합니다.");
                }else{
                    mEditPassword.setBackgroundColor(Color.RED);
                    mEditPassword2.setBackgroundColor(Color.RED);
                    mTextPasswordCorrect.setText("비밀번호가 불일치합니다. 다시 확인해주세요.");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                Intent intent = getIntent();
                final String userId = intent.getStringExtra("name");
                Intent idintent = new Intent(this,MainActivity.class);
                idintent.putExtra("name", userId);
                finish();
                return true;
            }
            case R.id.editUserInformation:{

                Intent intent = getIntent();
                final String userId = intent.getStringExtra("name");
                Map<String, Object> UserChildUpdate = new HashMap<>();
                Map<String, Object> UserValue = null;

                User user = new User();
                user.mUserId = userId;
                String strCURRENTPW = mNowPassword.getText().toString().trim();
                user.mUserPassword = mEditPassword.getText().toString().trim();
                String strPW2 = mEditPassword2.getText().toString().trim();
                user.mUserEmail = mEditEmail.getText().toString().trim();
                user.mUserHobby = mEditHobby.getText().toString().trim();
                user.mUserCareer = mEditCareer.getText().toString().trim();

                if(!strCURRENTPW.equals("") && mTextPasswordCorrect.getText().toString().equals("비밀번호가 일치합니다.") && !user.mUserPassword.equals("") && !strPW2.equals("") && !user.mUserEmail.equals("") && !user.mUserHobby.equals("") && !user.mUserCareer.equals("")) {
                    UserValue = user.toMap();
                    UserChildUpdate.put(userId, UserValue);
                    editRef.updateChildren(UserChildUpdate);
                    Intent editintent = new Intent(this, MainActivity.class);
                    editintent.putExtra("name",userId);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(editintent);
                    Toast.makeText(this, "수정 완료", Toast.LENGTH_SHORT).show();
                    return true;
                }
                else{
                    if(!mTextPasswordCorrect.getText().toString().equals("비밀번호가 일치합니다.")){
                        Toast.makeText(this, "새 비밀번호를 정확히 입력하세요!", Toast.LENGTH_SHORT).show();
                        mEditPassword.requestFocus();
                    }else if(strCURRENTPW.equals("")){
                        Toast.makeText(this, "현재 비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                        mNowPassword.requestFocus();
                    }else if(user.mUserPassword.equals("")){
                        Toast.makeText(this, "새 비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                        mEditPassword.requestFocus();
                    }else if(strPW2.equals("")){
                        Toast.makeText(this, "새 비밀번호 확인을 입력하세요!", Toast.LENGTH_SHORT).show();
                        mEditPassword2.requestFocus();
                    }else if(user.mUserEmail.equals("")){
                        Toast.makeText(this, "이메일을 입력하세요!", Toast.LENGTH_SHORT).show();
                        mEditEmail.requestFocus();
                    }else if(user.mUserHobby.equals("")){
                        Toast.makeText(this, "취미를 입력하세요!", Toast.LENGTH_SHORT).show();
                        mEditHobby.requestFocus();
                    }else if(user.mUserCareer.equals("")){
                        Toast.makeText(this, "희망진로를 입력하세요!", Toast.LENGTH_SHORT).show();
                        mEditCareer.requestFocus();
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
