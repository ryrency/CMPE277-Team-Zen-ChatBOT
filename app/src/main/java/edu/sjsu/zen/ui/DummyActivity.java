package edu.sjsu.zen.ui;

//Don't use this class..This is for reference purpose.

//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.shefalimunjal.chatbot.R;
//import com.shefalimunjal.chatbot.messageAdapter.MessageListAdapter;

//public class DummyActivity extends AppCompatActivity {

//    private final List<Object> messageList = new ArrayList<Object>();
//    private RecyclerView messageRecyclerView;
    //private MessageListAdapter messageListAdapter;
//    private static final String TAG = DummyActivity.class.getSimpleName();

//    private final String url = "http://www.mocky.io/v2/5aa8169d2f00005c1e8ea8c0";

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

//        messageRecyclerView = findViewById(R.id.recylerView);
//        View.OnClickListener sendEventListner = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               switch (v.getId()){
//                   case R.id.send_button:
//                       messageFromUser();
//                       break;
//
//               }
//            }
//        };
//        messageListAdapter = new MessageListAdapter(messageList, getApplicationContext());
//
//        LinearLayoutManager verticalLayoutManager =
//                new LinearLayoutManager(
//                        MainActivity.this,
//                        LinearLayoutManager.VERTICAL,
//                        false
//                );
//        messageRecyclerView.setLayoutManager(verticalLayoutManager);
//        messageFromChatbot();
//        //sendRequestAndprintResponse();
//        messageListAdapter.notifyDataSetChanged();
//        Button sendButton = (Button) findViewById(R.id.send_button);
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               switch (v.getId()){
//                   case R.id.send_button:
//                        Log.d(TAG,"Button is clicked");
//                        messageFromUser();
//                        break;
//               }
//            }
//        });
//        Log.d(TAG,"setting click listner");
//        messageRecyclerView.setAdapter(messageListAdapter);
//
//    }
//
//    public void sendRequestAndprintResponse() {
//        Log.d(TAG,"inside sendRequestAndprintResponse()");
//
//        JsonObjectRequest request = new JsonObjectRequest(
//                Request.Method.GET,
//                "https://api.androidhive.info/contacts/",
//                null,
//                new Response.Listener<JSONObject>() {
//                    public void onResponse(JSONObject response){
//                        Log.d(TAG,"response is:" +response.toString());
//                        try {
//                            Log.d(TAG, "getting the response object");
//                            JSONArray contactArray = response.getJSONArray("contacts");
//                            String data = "";
//                            for (int i = 0; i < contactArray.length(); i++) {
//                                JSONObject jsonObject = contactArray.getJSONObject(i);
//                                String id = jsonObject.getString("id");
//                                String type = jsonObject.getString("name");
//                                String email = jsonObject.getString("email");
//                                data += "ID: " + id + "NAME : " + type + "Email:" +email;
//                                Log.i(TAG,"data is:" +data);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e(TAG, "error making server request", error);
//                    }
//                }
//        );
//
//        VolleySingleton
//                .getInstance(getApplicationContext())
//                .getRequestQueue(this.getApplicationContext())
//                .add(request);
//    }
//
//    private void messageFromChatbot(){
//        Main2Activity.Message message1 = new Main2Activity.Message("Hi,I am your Course Assistant! How can I help you today?",
//                true);
//        Main2Activity.Message message2 = new Main2Activity.Message("You can ask any info about courses 277,294,295",
//                true);
//        messageList.add(message1);
//        messageList.add(message2);
//    }
//    private void messageFromUser(){
//        EditText userMessageView = (EditText) findViewById(R.id.enter_message);
//        String textFromUser = userMessageView.getText().toString();
//        if (!textFromUser.equals("")) {
//            Main2Activity.Message messageFromUser = new Main2Activity.Message(textFromUser, false);
//            messageList.add(messageFromUser);
//            Log.d(TAG, "add user message to the message list" + textFromUser);
//            messageListAdapter.notifyDataSetChanged();
//            userMessageView.setText("");
//
//        }
//    }
//}

