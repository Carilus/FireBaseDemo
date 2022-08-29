package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    EditText edtLang;
    ListView listView;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lstLanguages);
        edtLang = findViewById(R.id.edtValue);

        //saveData();
        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this,R.layout.list_items,list);
        listView.setAdapter(listAdapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("languages");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot datasnapshot:snapshot.getChildren())
                {
                    list.add(datasnapshot.getValue().toString());
                }
                listAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getApplicationContext(),"Logging Out",Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(),StartActivity.class));
    }
    public void saveData()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("ProgrammingKnowledge/users/carilus");

        HashMap<String, Object> users = new HashMap<>();
        users.put("Name","Carolus");
        //users.put("Email", "maryjane@gmail.com");

        ref.updateChildren(users);

    }
    public void Add(View view)
    {
        String lang_val = edtLang.getText().toString();
        if(lang_val.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"No value entered",Toast.LENGTH_LONG).show();
        }
        else
        {
            FirebaseDatabase.getInstance().getReference().child("languages").child("Name").setValue(lang_val);
            edtLang.setText("");
        }
    }
}
