package edu.sjsu.zen.ui;


import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import edu.sjsu.zen.models.Category;
import edu.sjsu.zen.models.MessageQuery;
import edu.sjsu.zen.R;
import edu.sjsu.zen.adapter.MessageAdapter;
import edu.sjsu.zen.models.MessageResponse;
import edu.sjsu.zen.models.Suggestion;
import edu.sjsu.zen.networking.VolleySingleton;
import edu.sjsu.zen.utils.DateTimeUtils;

import java.util.ArrayList;


public class ChatRoom extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = ChatRoom.class.getSimpleName();
    private final ArrayList<Object> messagesList = new ArrayList<>();
    private MessageAdapter adapter;
    private String courseContext;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_chat_room);

        Bundle extras = getIntent().getExtras();
        courseContext = extras.getString("COURSE_CONTEXT");
        MessageQuery query = new MessageQuery("course name "+courseContext);
        sendRequestAndprintResponse(query);

        recyclerView = (RecyclerView)findViewById(R.id.reyclerview_message_list);
        adapter = new MessageAdapter(messagesList,this);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(ChatRoom.this, LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(verticalLayoutManager);
        //messagesList.add(new User2("Select the course of interest"));

        recyclerView.setAdapter(adapter);

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
            addMessageObject(query);
            userMessageView.setText("");
            sendRequestAndprintResponse(query);

        }
    }

    public void onSuggestionClicked(Suggestion clickedSuggestion, MessageResponse messageResponse){
        if(clickedSuggestion == Suggestion.EMAIL_INSTRUCTOR) {
            MessageQuery query = new MessageQuery(clickedSuggestion.getName());
            addMessageObject(query);
            String toField = messageResponse.getString(MessageResponse.INSTRUCTOR_EMAIL);
            if (!TextUtils.isEmpty(toField)) {
                sendemail(toField);
            }
        } else if (clickedSuggestion == Suggestion.SET_REMINDER) {
            MessageQuery query = new MessageQuery(clickedSuggestion.getName());
            addMessageObject(query);
            String beginTime = "";
            String day = "";
            String dueDate = "";
            String title = "";
            if(messageResponse.getCategory() == Category.CLASS_TIMINGS){
                 beginTime = messageResponse.getString(MessageResponse.CLASS_START_TIME);
                 day = messageResponse.getString(MessageResponse.DAY_OF_CLASS);
                 title = messageResponse.getString("course_name") + " class";
                setReminderForCourseTimings(beginTime,day,title);
            }
            else {
              dueDate = messageResponse.getString(MessageResponse.DUE_DATE);
              title = messageResponse.getCategory().name();
              setReminderForCourseActivities(dueDate,title);
            }
        }
        else {
            MessageQuery query = new MessageQuery(clickedSuggestion.getName());
            addMessageObject(query);
            sendRequestAndprintResponse(query);
        }

    }

    private void addMessageObject(Object messageObject) {
        messagesList.add(messageObject);
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(messagesList.size() - 1);
    }

    private void sendemail(String toField) {
        String [] receiver = new String[]{toField};
        Intent mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.putExtra(Intent.EXTRA_EMAIL, receiver);
        mailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(mailIntent, "Choose an application to send your mail with"));
    }

    private void setReminderForCourseActivities(String dueDate, String title) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                DateTimeUtils.getNextEventDateTimeInMillis(dueDate));
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
        intent.putExtra(CalendarContract.Events.TITLE, title);
        startActivity(intent);


    }

    private void setReminderForCourseTimings(String beginTime, String dayName, String title) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                DateTimeUtils.getNextEventDateTimeInMillis(dayName, beginTime));
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
        intent.putExtra(CalendarContract.Events.TITLE, title);
        startActivity(intent);
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
                        addMessageObject(messageResponse);

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
