package edu.sjsu.zen.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.sjsu.zen.models.Category;
import edu.sjsu.zen.models.MessageQuery;
import edu.sjsu.zen.models.MessageResponse;
import edu.sjsu.zen.ui.ChatRoom;
import edu.sjsu.zen.R;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.*;


public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = MessageAdapter.class.getSimpleName();

    final ArrayList<Object> suggestions = new ArrayList<>();
    private final ArrayList<Object> messagesList;
    //private final int USER1 = 0, USER2 = 1;
    private final Context context;
    private final LayoutInflater layoutInflater;
    public MessageAdapter(ArrayList<Object> messagesList,Context context){
        this.messagesList = messagesList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        Log.i(TAG,"INTO ADATPER");

    }

    @Override
    public int getItemCount(){
        Log.i(TAG,"SIZE "+String.valueOf(messagesList.size()));
        return messagesList.size();
    }

    @Override
    public int getItemViewType(int position){
        Log.d(TAG, "");
        if(messagesList.get(position) instanceof MessageQuery){
            return 0;
        } else if (messagesList.get(position) instanceof MessageResponse) {
            Category category = ((MessageResponse) messagesList.get(position)).getCategory();
            if(category == Category.INSTRUCTOR_PHONE_NO ||
                    category == Category.INSTRUCTOR_NAME ||
                    category == Category.INSTRUCTOR_EMAIL ||
                    category == Category.INSTRUCTOR_OFFICE_HOURS ||
                    category == Category.INSTRUCTOR_OFFICE_LOCATION ||
                    category == Category.UNKNOWN)
                {
                return 1;
            }
        }

        return -1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        switch (viewType) {
        case 0:
            // Inflate the custom layout
            View userMessageView = layoutInflater.inflate(R.layout.item_message_user, viewGroup, false);
            //ViewHolder viewHolder1 = new ViewHolder1(userMessageView);
            return new ViewHolder1(userMessageView);

        case 1:
            View chatbotMessageView = layoutInflater.inflate(R.layout.item_message_chatbot, viewGroup, false);
            //ViewHolder viewHolder2 = new ViewHolder2(chatbotMessageView);
            return new ViewHolder2(chatbotMessageView);

    }
    return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position){
        switch(viewHolder.getItemViewType()){
            case 0:
                ViewHolder1 vh1 = (ViewHolder1)viewHolder;

                configureViewHolder1(vh1, position);
                break;
            case 1:
                ViewHolder2 vh2 = (ViewHolder2)viewHolder;

                configureViewHolder2(vh2,position);
                break;
            default:
                ViewHolder3 vh3 = (ViewHolder3)viewHolder;

                configureViewHolder3(vh3,position);
                break;
        }
    }


    private void configureViewHolder1(ViewHolder1 viewHolder1, int position){
        MessageQuery queryFromUser = (MessageQuery) messagesList.get(position);
        TextView queryFromUserTextView;
        queryFromUserTextView = viewHolder1.messageText;
        queryFromUserTextView.setText(queryFromUser.getQuery());
    }

    private void configureViewHolder2(ViewHolder2 viewHolder2, int position){
        TextView chatbotTextView;
        ImageView chatbotImageView;
        RecyclerView suggestionRecyclerView;
        SuggestionsAdapter suggestionsAdapter;
        String key = "";

        try {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            Log.i(TAG, String.valueOf(position));
            MessageResponse responseFromChatBot = (MessageResponse) messagesList.get(position);
            chatbotTextView = viewHolder2.messageText;

            suggestionRecyclerView = viewHolder2.suggestionRecyclerView;
            key = getKey(responseFromChatBot.getCategory().toString());


            if (key.equals("UNKNOWN")){
                chatbotTextView.setText("Sorry did not understand the question");
            }
            else {
                chatbotTextView.setText(responseFromChatBot.getString(key));
                suggestionsAdapter = new SuggestionsAdapter(responseFromChatBot.getCategory().getSuggestions(), context);
                suggestionRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                suggestionRecyclerView.setAdapter(suggestionsAdapter);
                BottomOffsetDecoration bottomOffsetDecoration = new BottomOffsetDecoration(40);
                suggestionRecyclerView.addItemDecoration(bottomOffsetDecoration);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private String getKey(String category){
        if (category.equals("INSTRUCTOR_OFFICE_LOCATION"))
            return "office_location";
        if (category.equals("INSTRUCTOR_EMAIL"))
            return "instructor_email";
        if (category.equals("INSTRUCTOR_NAME"))
            return "instructor_name";
        if (category.equals("INSTRUCTOR_CONTACT"))
            return "office_location";
        if (category.equals("INSTRUCTOR_PHONE_NO"))
            return "phn_no";
        if (category.equals("INSTRUCTOR_OFFICE_HOURS"))
            return "office_hours_start_time";
        if (category.equals("COURSE_NAME"))
            return "course_name";
        if (category.equals("UNKNOWN"))
            return "UNKNOWN";
        return "";

    }

    public static class BottomOffsetDecoration extends RecyclerView.ItemDecoration {
        private int mBottomOffset;

        public BottomOffsetDecoration(int bottomOffset) {
            mBottomOffset = bottomOffset;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int dataSize = state.getItemCount();
            int position = parent.getChildAdapterPosition(view);
            if (dataSize > 0 && position == dataSize - 1) {
                outRect.set(0, 0, mBottomOffset,0 );
            } else {
                outRect.set(0, 0, mBottomOffset, 0);
            }

        }
    }

    private void configureViewHolder3(ViewHolder3 viewHolder2, int position){

    }

    public class ViewHolder1 extends ViewHolder{
        private TextView messageText;

        private ViewHolder1(View itemView){
            super(itemView);

            Log.i(TAG,"creating view holder");
            messageText = (TextView) itemView.findViewById(R.id.user_text_message_body);
        }

    }

    public class ViewHolder2 extends ViewHolder{
        private ImageView chatbotImage;
        private TextView messageText;
        private RecyclerView suggestionRecyclerView ;


        public ViewHolder2(View itemView){
            super(itemView);
            Log.i(TAG,"creating view holder");
            chatbotImage = (ImageView) itemView.findViewById(R.id.chatbot_imageView);
            messageText = (TextView) itemView.findViewById(R.id.chatbot_text_message_body);
            suggestionRecyclerView = (RecyclerView)itemView.findViewById(R.id.reyclerview_suggestion_list);
        }

    }

    public class ViewHolder3 extends ViewHolder{

        public ViewHolder3(View itemView){
            super(itemView);
        }

    }



}

