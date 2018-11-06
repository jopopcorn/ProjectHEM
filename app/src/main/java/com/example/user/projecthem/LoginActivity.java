package com.example.user.projecthem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference loginRef;
    private FirebaseDatabase loginDatabase;
    private EditText mEditId, mEditPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginDatabase = FirebaseDatabase.getInstance();
        loginRef = loginDatabase.getReference("users");
        mEditId = findViewById(R.id.EditId);
        mEditPassword = findViewById(R.id.EditPassword);
        Button btnLogin = findViewById(R.id.BtnLogin);
        TextView joinText = findViewById(R.id.textJoin);
        joinText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent joinIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(joinIntent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEditId.getText().toString().equals("") || mEditPassword.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"빈칸없이 입력하세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    loginRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();

                            while (child.hasNext()) {
                                if (child.next().getKey().equals(mEditId.getText().toString())) {
                                    Toast.makeText(LoginActivity.this, "로그인", Toast.LENGTH_SHORT).show();
                                    Log.i("아이디 일치",""+dataSnapshot.getChildren().iterator().next().getKey());
                                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    mainIntent.putExtra("name", mEditId.getText().toString());
                                    LoginActivity.this.startActivity(mainIntent);
                                    return;
                                }
                            }
                            Toast.makeText(LoginActivity.this, "로그인 실패: 아이디 또는 비밀번호를 다시 확인하세요.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
}
