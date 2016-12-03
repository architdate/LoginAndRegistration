package com.example.test.loginapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ArrayList<User> Users = new ArrayList<User>();
    TableLayout display_table;
    boolean search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display_table = (TableLayout) findViewById(R.id.display_table);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");
        myRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot){
                Users = new ArrayList<User>();
                for ( DataSnapshot child : snapshot.getChildren()){
                    User s = child.getValue(User.class);
                    Users.add(s);
                }
                //display_table();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError);
            }
        } );
    }




    public void login(View v){
        User s;
        String name_search = ((EditText) findViewById(R.id.editText_name)).getText().toString().trim();
        String password_search = ((EditText) findViewById(R.id.editText_password)).getText().toString().trim();
        System.out.println(name_search + " " + password_search);
        for (int i = 0; i<Users.size(); i++){
            s = Users.get(i);
            if(s.password.trim().equals(password_search) &&  s.username.trim().equals(name_search)) {
                System.out.println("in here");
                Intent intent = new Intent(this, ServerLogin.class);
                intent.putExtra("username", s.username);
                startActivity(intent);
            }
        }
        Toast toast;
        toast = Toast.makeText(getApplicationContext(), "username or login mismatch", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void register(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}

class User {
    String username;
    String password;
    String servername;

    public User(){
    }

    public User(String email, String pass){
        this.username = email;
        this.password = pass;
    }

    public User(String email, String pass, String serverName){
        this.username = email;
        this.password = pass;
        this.servername = serverName;
    }


    public String getName() {
        return username;
    }

    public String getpassword() {
        return password;
    }

    public String toString(){
        return "User(email=" + username +", password=" + password + ")" ;
    }

}
