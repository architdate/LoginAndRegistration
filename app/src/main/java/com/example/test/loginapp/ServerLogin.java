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

/**
 * Created by test on 3/12/2016.
 */

public class ServerLogin extends AppCompatActivity{
    ArrayList<Server> Servers = new ArrayList<Server>();
    TableLayout display_table;
    boolean search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        display_table = (TableLayout) findViewById(R.id.display_table);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Server");
        myRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot){
                Servers = new ArrayList<Server>();
                for ( DataSnapshot child : snapshot.getChildren()){
                    Server s = child.getValue(Server.class);
                    Servers.add(s);
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
        Server s;
        String name_search = ((EditText) findViewById(R.id.editText_name)).getText().toString();
        String password_search = ((EditText) findViewById(R.id.editText_password)).getText().toString();
        for (int i = 0; i<Servers.size(); i++){
            s = Servers.get(i);
            if(name_search.equals(s.Servername) && password_search.equals(s.password)) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("User/"+getIntent().getExtras().getString("username")+"/Server/"+s.Servername);
                myRef.setValue(new Server(name_search,password_search));
                Intent intent = new Intent(this, AppActivity.class);
                startActivity(intent);
            }
        }
        Toast toast;
        toast = Toast.makeText(getApplicationContext(), "Servername or login mismatch", Toast.LENGTH_SHORT);
        toast.show();
    }
}

class Server {
    String Servername;
    String password;

    public Server(){
    }

    public Server(String email, String pass){
        this.Servername = email;
        this.password = pass;
    }

    public String getName() {
        return Servername;
    }

    public String getpassword() {
        return password;
    }

    public String toString(){
        return "Server(email=" + Servername +", password=" + password + ")" ;
    }

}



