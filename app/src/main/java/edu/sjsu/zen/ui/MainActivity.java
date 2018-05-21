package edu.sjsu.zen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import edu.sjsu.zen.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button context27301 = (Button)findViewById(R.id.main_lets_chat_button);

        context27301.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.main_lets_chat_button:
                goToChatRoom("273-01");
                break;
        }
    }

    private void goToChatRoom(String courseContext){
        Intent chatRoom = new Intent(this,ChatRoom.class);
        chatRoom.setClass(this,ChatRoom.class);
        startActivity(chatRoom);
    }
}
