package edu.sjsu.zen.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
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
    private DrawerLayout mDrawerLayout;
    private static final String GOOGLE_URL = " http://maps.google.co.in/maps?q= ";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_chat_room);

        /*Drawer Navigation set up*/
        setNavigationLayout();


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


    private void setNavigationLayout(){

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout.openDrawer(Gravity.START);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();

                        // set item as selected to persist highlight
                        menuItem.setChecked(true);

                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawer(Gravity.START);
                        if (menuItem.getItemId() == R.id.cmpe01)
                            courseContext = "273-01";
                        else
                            courseContext = "272-02";
                        MessageQuery query = new MessageQuery("course name "+courseContext);
                        sendRequestAndprintResponse(query);

                        return true;
                    }
                });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
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

        else if(clickedSuggestion == Suggestion.NAVIGATE_TO_LOCATION) {
            MessageQuery query = new MessageQuery(clickedSuggestion.getName());
            addMessageObject(query);
            String latitude = messageResponse.getString("latitude");
            String longitude = messageResponse.getString("longitude");

            navigateToLocation(latitude, longitude);
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

    private void navigateToLocation(String latitude, String longitude) {
        Uri mapUri = Uri.parse("geo:" + latitude + ',' + longitude +
                "?q=" + latitude + "," + longitude );
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
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
                    VolleySingleton.ZEN_AWS_ENDPOINT + "/classify?text="+ query.getQuery(),
                    null,
                    new Response.Listener<JSONObject>() {
                        public void onResponse(JSONObject response){
                            Log.d(TAG,"response is:" + response.toString());
                            MessageResponse messageResponse = MessageResponse.fromJSONObjectResponse(response);
                            Log.d(TAG, "received message response with category: " + messageResponse.getCategory().name());
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
