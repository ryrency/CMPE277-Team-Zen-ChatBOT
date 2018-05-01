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

import edu.sjsu.zen.ui.ChatRoom;
import edu.sjsu.zen.R;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.*;


public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    final ArrayList<Object> suggestions = new ArrayList<>();

    private List<Object> mMessages;
    private final int USER1 = 0, USER2 = 1;
    Context mContext;

    public MessageAdapter(List<Object> messages,Context context){
        this.mMessages = messages;
        this.mContext = context;

        Log.i("RENCY","INTO ADATPER");

    }


    @Override
    public int getItemCount(){
        Log.i("RENCY","SIZE "+String.valueOf(mMessages.size()));
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position){
        if(mMessages.get(position) instanceof ChatRoom.User1){
            return USER1;
        }
        else if (mMessages.get(position) instanceof ChatRoom.User2)
            return USER2;
        return -1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){

        Log.i("RENCY","Infalting user view");
        ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch(viewType) {
            case USER1:
                // Inflate the custom layout

                View userMessageView = inflater.inflate(R.layout.item_message_user, viewGroup, false);
                viewHolder = new ViewHolder1(userMessageView);
                break;
            case USER2:
                View chatbotMessageView = inflater.inflate(R.layout.item_message_chatbot,viewGroup, false);
                viewHolder = new ViewHolder2(chatbotMessageView);
                break;
            default:
//                View chatbotMessageView2 = inflater.inflate(R.layout.default_layout,viewGroup, false);
//                viewHolder = new ViewHolder3(chatbotMessageView2);
//                break;
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position){

        Log.i("RENCY", "VIEWTYPE "+String.valueOf(viewHolder.getItemViewType()));
        switch(viewHolder.getItemViewType()){
            case USER1:
                ViewHolder1 vh1 = (ViewHolder1)viewHolder;

                configureViewHolder1(vh1, position);
                break;
            case USER2:
                ViewHolder2 vh2 = (ViewHolder2)viewHolder;

                configureViewHolder2(vh2,position);
                break;
            default:
                ViewHolder3 vh3 = (ViewHolder3)viewHolder;

                configureViewHolder3(vh3,position);
                break;
        }
//        TextView tx, tx1;
        Log.i("RENCY","Binding");
//
//        DateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm");
//        Date date = new Date();
//
//        Message message = mMessages.get(position);
//
//        tx = viewHolder.messageText;
//        tx.setText(message.getMessage());
//        tx1 = viewHolder.messageTime;
//        tx1.setText((dateFormat.format(date)));
    }

//    private void configureDefaultViewHolder(RecyclerViewSimpleTextViewHolder vh, int position ){
//        vh.getLabel().setText((CharSequence) items.get(position));
//    }


    private void configureViewHolder1(ViewHolder1 viewHolder1, int position){
        ChatRoom.User1 user = (ChatRoom.User1)mMessages.get(position);

        TextView tv1, tv2;

        tv1 = viewHolder1.messageText;
        tv1.setText(user.getMessage());
    }

    private void configureViewHolder2(ViewHolder2 viewHolder2, int position){
        TextView chatbot_text_message_body_tv;
        ImageView chatbot_imageView_iv;
        RecyclerView suggestionRecyclerView_rv;
        String category = "instructor";
        SuggestionsAdapter suggestionsAdapter = new SuggestionsAdapter(category,mContext);

        LayoutInflater inflater = LayoutInflater.from(this.mContext);

        Log.i("RENCY POS", String.valueOf(position));

        ChatRoom.User2 user = (ChatRoom.User2)mMessages.get(position);

        String msg = user.getMessage();
        Log.i("RENCY",msg);

        chatbot_text_message_body_tv = viewHolder2.messageText;
        chatbot_imageView_iv = viewHolder2.chatbotImage;
        suggestionRecyclerView_rv = viewHolder2.suggestionRecyclerView;


        chatbot_text_message_body_tv.setText(user.getMessage());

        suggestionRecyclerView_rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        suggestionRecyclerView_rv.setAdapter(suggestionsAdapter);


        BottomOffsetDecoration bottomOffsetDecoration = new BottomOffsetDecoration(40);
        suggestionRecyclerView_rv.addItemDecoration(bottomOffsetDecoration);




    }

    static class BottomOffsetDecoration extends RecyclerView.ItemDecoration {
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
        public TextView messageText;

        public ViewHolder1(View itemView){
            super(itemView);

            Log.i("RENCY","creating view holder");
            messageText = (TextView) itemView.findViewById(R.id.user_text_message_body);
        }

    }

    public class ViewHolder2 extends ViewHolder{
        public ImageView chatbotImage;
        public TextView messageText;
        public RecyclerView suggestionRecyclerView ;


        public ViewHolder2(View itemView){
            super(itemView);
            Log.i("RENCY","creating view holder");
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

