package edu.sjsu.zen.ui;


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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import edu.sjsu.zen.models.MessageQuery;
import edu.sjsu.zen.R;
import edu.sjsu.zen.adapter.MessageAdapter;
import edu.sjsu.zen.models.MessageResponse;
import edu.sjsu.zen.networking.VolleySingleton;

import java.util.ArrayList;


public class ChatRoom extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = ChatRoom.class.getSimpleName();
    private final ArrayList<Object> messagesList = new ArrayList<>();
    private MessageAdapter adapter;
    //ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_chat_room);

        RecyclerView recycler = (RecyclerView)findViewById(R.id.reyclerview_message_list);
        adapter = new MessageAdapter(messagesList,this);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(ChatRoom.this, LinearLayoutManager.VERTICAL,
                false);
        recycler.setLayoutManager(verticalLayoutManager);
        //messagesList.add(new User2("Select the course of interest"));

        recycler.setAdapter(adapter);

        Button send = (Button)findViewById(R.id.button_chatroom_send);
        send.setOnClickListener(this);
    }

    public void clear(View view){

        EditText text = (EditText) findViewById(R.id.edittext_chatbox);
        text.setText("");

    }


    public void messageFromUser(){
        EditText userMessageView = (EditText) findViewById(R.id.edittext_chatbox);
        String textFromUser = userMessageView.getText().toString().trim();
        //DateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm");
        //Date date = new Date();
        //Messages newMessage =  new Messages();
        if (!textFromUser.equals("")){
            MessageQuery query = new MessageQuery(textFromUser);
            messagesList.add(query);
            adapter.notifyDataSetChanged();
            userMessageView.setText("");
            sendRequestAndprintResponse(query);

        }
    }


    @Override
    public void onClick(View v) {
        messageFromUser();
    }

//    /*User*/
//    public static class User1 {
//        String  message;
//        public User1(String msg) {
//            message = msg;
//        }
//        public String getMessage(){
//            return this.message;
//        }
//
  //  }

    /*ChatBOT*/
//    public static class User2 {
//        String  message;
//        public User2(String msg) {
//            message = msg;
//        }
//        public String getMessage(){
//            return this.message;
//        }
//
//
//    }

    public void sendRequestAndprintResponse(MessageQuery query) {
        Log.d(TAG,"inside sendRequestAndprintResponse()");
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://10.0.2.2:5000/classify?text="+ query.getQuery(),
                null,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response){
                        Log.d(TAG,"response is:" +response.toString());
                        MessageResponse messageResponse = MessageResponse.fromJSONObjectResponse(response);
                        messagesList.add(messageResponse);
                        adapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "error making server request", error);
                    }
                }
        );

        VolleySingleton
                .getInstance(getApplicationContext())
                .getRequestQueue(this.getApplicationContext())
                .add(request);
    }


}
