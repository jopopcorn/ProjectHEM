package com.example.user.projecthem;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private TextView mTextIdOverlap, mTextPasswordCorrect;
    private EditText mEditId, mEditPassword, mEditPassword2, mEditEmail, mEditHobby, mEditCareer;
    private Button mBtnRegister;

    private ArrayAdapter adapter;
    private Spinner spinner;
    private FirebaseDatabase registerDatabase;
    private DatabaseReference registerRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mBtnRegister = findViewById(R.id.BtnRegister);
        mTextIdOverlap = findViewById(R.id.TextIdOverlap);
        mTextPasswordCorrect = findViewById(R.id.TextPasswordCorrect);
        mEditId = findViewById(R.id.EditId);
        mEditPassword = findViewById(R.id.EditPassword);
        mEditPassword2 = findViewById(R.id.EditPassword2);
        mEditEmail = findViewById(R.id.EditEmail);
        mEditHobby = findViewById(R.id.EditHobby);
        mEditCareer = findViewById(R.id.EditCareer);
        spinner = findViewById(R.id.spinnerGrade);
        adapter = ArrayAdapter.createFromResource(this,R.array.grade,android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        registerDatabase = FirebaseDatabase.getInstance();
        registerRef = registerDatabase.getReference("users");

        mEditId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();

                        while (child.hasNext()) {
                            if (child.next().getKey().equals(mEditId.getText().toString())) {
                                mTextIdOverlap.setText("동일한 아이디가 존재합니다.");
                                Log.i("동일한 아이디 존재",""+dataSnapshot.getChildren().iterator().next().getKey());
                                return;
                            }
                        }
                        mTextIdOverlap.setText("사용 가능한 아이디입니다.");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

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

        mEditId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> UserChildUpdate = new HashMap<>();
                Map<String, Object> UserValue = null;

                User user = new User();
                user.mUserId = mEditId.getText().toString().trim();
                user.mUserPassword = mEditPassword.getText().toString().trim();
                String strPW2 = mEditPassword2.getText().toString().trim();
                user.mUserEmail = mEditEmail.getText().toString().trim();
                user.mUserHobby = mEditHobby.getText().toString().trim();
                user.mUserCareer = mEditCareer.getText().toString().trim();

                Log.i("USER DATA","ID: "+user.mUserId+", PW: "+user.mUserPassword+", EMAIL: "+user.mUserEmail+", HOBBY: "+user.mUserHobby+", CAREER: "+user.mUserCareer);
                Log.i("USER ID, PW IDENTIFY",""+mTextIdOverlap.getText().toString()+", "+mTextPasswordCorrect.getText().toString());

                if (!(user.mUserId.equals("")) && !(user.mUserPassword.equals("")) && !(strPW2.equals("")) && !(user.mUserEmail.equals("")) && !(user.mUserHobby.equals("")) && !(user.mUserCareer.equals("")) && mTextIdOverlap.getText().toString().equals("사용 가능한 아이디입니다.") && mTextPasswordCorrect.getText().toString().equals("비밀번호가 일치합니다.")) {
                    UserValue = user.toMap();
                    UserChildUpdate.put(user.mUserId,UserValue);
                    registerRef.updateChildren(UserChildUpdate);
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(RegisterActivity.this, "가입 완료", Toast.LENGTH_SHORT).show();
                }else{
                    if(mEditId.getText().toString().length() == 0) {
                        Toast.makeText(RegisterActivity.this, "ID를 입력하세요!", Toast.LENGTH_SHORT).show();
                        mEditId.requestFocus();
                        return;
                    }else if(mTextIdOverlap.getText().toString().equals("동일한 아이디가 존재합니다.")){
                        Toast.makeText(RegisterActivity.this, "동일한 ID가 존재합니다. 새로운 ID를 설정해주세요.", Toast.LENGTH_SHORT).show();
                        mEditId.requestFocus();
                        return;
                    }else if(mEditPassword.getText().toString().length() == 0) {
                        Toast.makeText(RegisterActivity.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                        mEditPassword.requestFocus();
                        return;
                    }else if (mEditPassword2.getText().toString().length() == 0) {
                        Toast.makeText(RegisterActivity.this, "비밀번호 확인을 입력하세요!", Toast.LENGTH_SHORT).show();
                        mEditPassword2.requestFocus();
                        return;
                    }else if (mEditEmail.getText().toString().length() == 0) {
                        Toast.makeText(RegisterActivity.this, "이메일을 입력하세요!", Toast.LENGTH_SHORT).show();
                        mEditEmail.requestFocus();
                        return;
                    }else if (mEditHobby.getText().toString().length() == 0) {
                        Toast.makeText(RegisterActivity.this, "취미를 입력하세요!", Toast.LENGTH_SHORT).show();
                        mEditHobby.requestFocus();
                        return;
                    }else if(mEditCareer.getText().toString().length() == 0) {
                        Toast.makeText(RegisterActivity.this, "희망진로를 입력하세요!", Toast.LENGTH_SHORT).show();
                        mEditCareer.requestFocus();
                        return;
                    }else{
                        Toast.makeText(RegisterActivity.this,"알 수 없는 오류로 가입이 불가능합니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
