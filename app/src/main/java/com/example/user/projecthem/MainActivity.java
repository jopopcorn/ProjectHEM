package com.example.user.projecthem;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private Button mBtnEdit;
    private TextView mtextMainNickname;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    static final String [] menuData = {"커뮤니티", "행사일정", "로그아웃"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        final String userId = intent.getStringExtra("name");

        mtextMainNickname = findViewById(R.id.textMainNickname);
        database = FirebaseDatabase.getInstance();
        mtextMainNickname.setText("ID: "+userId);
        myRef = database.getReference("users");

        mBtnEdit = findViewById(R.id.btnEdit);

        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileintent = new Intent(MainActivity.this,EditProfileActivity.class);
                profileintent.putExtra("name", userId);
                MainActivity.this.startActivity(profileintent);
            }
        });

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,menuData){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = view.findViewById(android.R.id.text1);
                text.setTextColor(Color.WHITE);
                return view;
            }
        };

        ListView listMenu = findViewById(R.id.ListMenu);
        listMenu.setAdapter(adapter);

        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent commuintent = new Intent(MainActivity.this,CommunityActivity.class);
                    commuintent.putExtra("name", userId);
                    MainActivity.this.startActivity(commuintent);
                }else if(position == 1){
                    Intent calendarintent = new Intent(MainActivity.this,CalendarActivity.class);
                    MainActivity.this.startActivity(calendarintent);
                }else{
                    Intent logoutintent = new Intent(MainActivity.this,LoginActivity.class);
                    logoutintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    MainActivity.this.startActivity(logoutintent);
                    Toast.makeText(MainActivity.this,"로그아웃",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
