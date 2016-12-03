package com.example.test.loginapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by test on 4/12/2016.
 */

public class RegisterActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }

    public void registerUser(View v){
        String name_search = ((EditText) findViewById(R.id.editText_name)).getText().toString();
        String password_search = ((EditText) findViewById(R.id.editText_password)).getText().toString();
        String repassword_search = ((EditText) findViewById(R.id.editText_repassword)).getText().toString();

        if(!name_search.equals("") && (password_search.equals(repassword_search))){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("User/"+name_search);
            myRef.setValue(new User(name_search, password_search));
        }else{
            Toast toast;
            toast = Toast.makeText(getApplicationContext(), "you fucked up", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
