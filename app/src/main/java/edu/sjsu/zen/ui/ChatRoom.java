package edu.sjsu.zen.ui;


import android.content.Intent;
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
    private String emailAddressInResponse ;
    private static boolean RUN_ONCE = true;
    private String courseContext;
    //ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_chat_room);

        if (RUN_ONCE){
            RUN_ONCE = false;
            Bundle extras = getIntent().getExtras();
            courseContext = extras.getString("COURSE_CONTEXT");
            MessageQuery query = new MessageQuery("course name "+courseContext);
            sendRequestAndprintResponse(query);
        }
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
        if (!textFromUser.equals("")){
            Log.d(TAG+"Text from User ",textFromUser);
            MessageQuery query = new MessageQuery(textFromUser);
            messagesList.add(query);
            adapter.notifyDataSetChanged();
            userMessageView.setText("");
            sendRequestAndprintResponse(query);

        }
    }

    public void messageFromUser(String suggestionSelected){
        if(suggestionSelected.equals("Email Instructor")) {
            MessageQuery query = new MessageQuery(suggestionSelected);
            messagesList.add(query);
            adapter.notifyDataSetChanged();
            String toField = emailAddressInResponse;
            sendemail(toField);
        }
        else if (!suggestionSelected.equals("")){
            MessageQuery query = new MessageQuery(suggestionSelected);
            messagesList.add(query);
            adapter.notifyDataSetChanged();
            sendRequestAndprintResponse(query);
        }

    }

    public void EmailAddressFromChatBot(String response) {
        emailAddressInResponse = response;
    }


    private void sendemail(String toField) {
        String [] receiver = new String[]{toField};
        Intent mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.putExtra(Intent.EXTRA_EMAIL, receiver);
        mailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(mailIntent, "Choose an application to send your mail with"));
    }

    @Override
    public void onClick(View v) {
        messageFromUser();
    }


    public void sendRequestAndprintResponse(MessageQuery query) {
        Log.d(TAG,"inside sendRequestAndprintResponse()");
        try{
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
    catch (Exception e){
            e.printStackTrace();
    }
    }


}
