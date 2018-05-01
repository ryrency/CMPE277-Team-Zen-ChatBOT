package edu.sjsu.zen.adapter;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.sjsu.zen.models.Messages;
import edu.sjsu.zen.R;
import edu.sjsu.zen.ui.MessageAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ChatRoom extends AppCompatActivity implements View.OnClickListener{

    final ArrayList<Object> items = new ArrayList<>();

    MessageAdapter adapter = new MessageAdapter(items,this);
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_chat_room);

        RecyclerView recycler = (RecyclerView)findViewById(R.id.reyclerview_message_list);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(ChatRoom.this, LinearLayoutManager.VERTICAL,
                false);
        recycler.setLayoutManager(verticalLayoutManager);
        items.add(new User2("Select the course of interest"));

        recycler.setAdapter(adapter);

        Button send = (Button)findViewById(R.id.button_chatroom_send);
        send.setOnClickListener(this);
    }

    public void clear(View view){

        EditText text = (EditText) findViewById(R.id.edittext_chatbox);
        text.setText("");

    }


    public void getUserData(){
        EditText text = (EditText) findViewById(R.id.edittext_chatbox);
        String input_from_user = "";
        input_from_user = text.getText().toString().trim();
        Log.i("RENCY INPUT FRM USER",input_from_user);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm");
        Date date = new Date();
        Messages newMessage =  new Messages();
        if (input_from_user != " "){
            items.add(new ChatRoom.User1(input_from_user));
            adapter.notifyDataSetChanged();
            text.setText("");
        }
    }


    @Override
    public void onClick(View v) {
        getUserData();
    }

    /*User*/
    public static class User1 {
        String  message;
        public User1(String msg) {
            message = msg;
        }
        public String getMessage(){
            return this.message;
        }

    }

    /*ChatBOT*/
    public static class User2 {
        String  message;
        public User2(String msg) {
            message = msg;
        }
        public String getMessage(){
            return this.message;
        }


    }
}
